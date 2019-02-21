import 'package:flutter/services.dart';

typedef Future<dynamic> MessageHandler(Map<String, dynamic> message);

class Stripe {
  static const platform = const MethodChannel('com.rajyadavnp.flutter/stripe');

  MessageHandler _onTokenSuccess;
  MessageHandler _onTokenError;

  static init(String apiKey) {
    try {
      var data = platform.invokeMethod("init", {"apiKey": apiKey});
      print(data);
    } on PlatformException catch (e) {
      throw e;
    }
  }

  Future<dynamic> _handleMethod(MethodCall call) async {
    switch (call.method) {
      case "onTokenSuccess":
        return _onTokenSuccess(call.arguments.cast<String, dynamic>());
      case "onTokenError":
        return _onTokenError(call.arguments.cast<String, dynamic>());
      default:
        throw UnsupportedError("Unrecognized message");
    }
  }

  Future<void> createToken(StripeCard card, MessageHandler onTokenSuccess,
      MessageHandler onTokenError) async {
    _onTokenSuccess = onTokenSuccess;
    _onTokenError = onTokenError;
    platform.setMethodCallHandler(_handleMethod);
    platform.invokeMethod("createToken", {
      "number": card.cardNumber,
      "month": card.expiryMonth,
      "year": card.expiryYear,
      "cvc": card.cvc
    });
  }

  static Future<bool> validateCard(StripeCard card) async {
    try {
      final bool result = await platform.invokeMethod("validateCard", {
        "number": card.cardNumber,
        "expiryMonth": card.expiryMonth,
        "expiryYear": card.expiryYear,
        "cvc": card.cvc
      });
      return result;
    } on PlatformException catch (e) {
      return false;
    }
  }
}

class StripeCard {
  final String id;
  final String customerId;
  final String addressCity;
  final String addressCountry;
  final String addressLine1;
  final String addressLine1Check;
  final String addressLine2;
  final String addressState;
  final String addressZip;
  final String addressZipCheck;
  final String brand;
  final String country;
  final String currency;
  final String cvcCheck;
  final String fingerprint;
  final String funding;
  final String last4;
  final String name;

  final String cardNumber;
  final int expiryMonth;
  final int expiryYear;
  final String cvc;

  StripeCard({
    this.id,
    this.customerId,
    this.cardNumber,
    this.expiryMonth,
    this.expiryYear,
    this.cvc,
    this.addressCity,
    this.addressCountry,
    this.addressLine1,
    this.addressLine1Check,
    this.addressLine2,
    this.addressState,
    this.addressZip,
    this.addressZipCheck,
    this.brand,
    this.country,
    this.currency,
    this.cvcCheck,
    this.fingerprint,
    this.funding,
    this.last4,
    this.name,
  });
}

class BankAccount {
  final String accountHolderName;
  final String accountHolderType;
  final String accountNumber;
  final String bankName;
  final String country;
  final String currency;
  final String fingerprint;
  final String last4;
  final String routingNumber;

  BankAccount(
      {this.accountHolderName,
      this.accountHolderType,
      this.accountNumber,
      this.bankName,
      this.country,
      this.currency,
      this.fingerprint,
      this.last4,
      this.routingNumber});
}

class Token {
  final String id;
  final BankAccount bankAccount;
  final StripeCard card;
  final int created;
  final bool liveMode;
  final String type;
  final bool used;

  Token(
      {this.id,
      this.bankAccount,
      this.card,
      this.created,
      this.liveMode,
      this.type,
      this.used});
}
