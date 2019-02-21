package com.rajyadavnp.flutterstripe

import android.os.Bundle
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.rajyadavnp.flutter/stripe"

    lateinit var methodChannel: MethodChannel
    lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        methodChannel = MethodChannel(flutterView, CHANNEL)

        methodChannel.setMethodCallHandler { call, result ->
            if (call.method == "init") {
                val api = call.argument<String>("apiKey");
                initStripe(api!!)
                try {
                    result.success("initialized::Stripe")
                } catch (e: Exception) {
                    result.error("Failed!", "Oops, something is not right!", "failed::Stripe")
                }
            } else if (call.method == "createToken") {
                val number = call.argument<String>("number")
                val expiryMonth = call.argument<Int>("month")
                val expiryYear = call.argument<Int>("year")
                val cvc = call.argument<String>("cvc")

                createToken(Card(number, expiryMonth, expiryYear, cvc));
                try {
                    result.success("tokenCreated")
                } catch (e: Exception) {
                    result.error("Failed!", "Oops, something is not right!", e.message)
                }
            } else if (call.method == "validateCard") {
                val number = call.argument<String>("number")
                val expiryMonth = call.argument<Int>("expiryMonth")
                val expiryYear = call.argument<Int>("expiryYear")
                val cvc = call.argument<String>("cvc")

                val valid = validateCard(number!!, expiryMonth!!, expiryYear!!, cvc!!)
                try {
                    result.success(valid)
                } catch (e: Exception) {
                    result.error("Failed!", "Oops, something is not right!", false)
                }
            } else {
                result.notImplemented()
            }
        }
    }


    private fun initStripe(apiKey: String) {
        stripe = Stripe(this, apiKey)
    }


    private fun createPayment(token: String) {

    }

    private fun createToken(cardOut: Card) {
        if (stripe == null) {
            throw Exception("Stripe is not initialized")
        } else {
            stripe.createToken(cardOut, object : TokenCallback {
                override fun onSuccess(token: Token?) {

                    val bankMap = HashMap<String, Any?>()
                    val cardMap = HashMap<String, Any?>()
                    val tokenMap = HashMap<String, Any?>()

                    val bank = token!!.bankAccount
                    val card = token.card

                    if (bank != null) {
                        bankMap["accountHolderName"] = bank.accountHolderName
                        bankMap["accountHolderType"] = bank.accountHolderType
                        bankMap["accountNumber"] = bank.accountNumber
                        bankMap["bankName"] = bank.bankName
                        bankMap["countryCode"] = bank.countryCode
                        bankMap["currency"] = bank.currency
                        bankMap["fingerprint"] = bank.fingerprint
                        bankMap["last4"] = bank.last4
                        bankMap["routingNumber"] = bank.routingNumber
                    }
                    cardMap["id"] = card.id
                    cardMap["customerId"] = card.customerId
                    cardMap["number"] = card.number
                    cardMap["expiryMonth"] = card.expMonth
                    cardMap["expiryYear"] = card.expYear
                    cardMap["cvc"] = card.cvc
                    cardMap["addressCity"] = card.addressCity
                    cardMap["addressCountry"] = card.addressCountry
                    cardMap["addressLine1"] = card.addressLine1
                    cardMap["addressLine1Check"] = card.addressLine1Check
                    cardMap["addressLine2"] = card.addressLine2
                    cardMap["addressState"] = card.addressState
                    cardMap["addressZip"] = card.addressZip
                    cardMap["addressZipCheck"] = card.addressZipCheck
                    cardMap["brand"] = card.brand
                    cardMap["country"] = card.country
                    cardMap["currency"] = card.currency
                    cardMap["cvcCheck"] = card.cvcCheck
                    cardMap["fingerprint"] = card.fingerprint
                    cardMap["funding"] = card.funding
                    cardMap["last4"] = card.last4
                    cardMap["name"] = card.name

                    tokenMap["id"] = token.id
                    tokenMap["created"] = token.created.time
                    tokenMap["liveMode"] = token.livemode
                    tokenMap["type"] = token.type
                    tokenMap["used"] = token.used
                    tokenMap["bankAccount"] = bankMap
                    tokenMap["card"] = cardMap

                    methodChannel.invokeMethod("onTokenSuccess", tokenMap)
                }

                override fun onError(error: Exception) {
                    methodChannel.invokeMethod("onTokenError", error)
                }

            })
        }
    }

    private fun validateCard(number: String, expiryMonth: Int, expiryYear: Int, cvc: String): Boolean {
        val card = Card(number, expiryMonth, expiryYear, cvc)
        return card.validateCard()
    }

}
