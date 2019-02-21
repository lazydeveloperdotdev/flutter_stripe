package com.rajyadavnp.oppia

import android.app.Application
import android.graphics.Typeface
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class Oppia : Application() {
    companion object {
        lateinit var instance: Oppia
            private set

        val TAG = Oppia::class.java.simpleName
        var mRequestQueue: RequestQueue? = null

        val SERVER_URL = "http://192.168.43.111:8000/"

        val LOGIN_PREF = "LOGIN_PREF"
        val SESSION = "SESSION"
        val USER_TOKEN = "USER_TOKEN"

        lateinit var BOLD_ITALIC: Typeface
        lateinit var BOLD: Typeface
        lateinit var ITALIC: Typeface
        lateinit var REGULAR: Typeface
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        BOLD_ITALIC = Typeface.createFromAsset(assets, "fonts/open_sans/OpenSans-BoldItalic.ttf")
        BOLD = Typeface.createFromAsset(assets, "fonts/open_sans/OpenSans-Bold.ttf")
        ITALIC = Typeface.createFromAsset(assets, "fonts/open_sans/OpenSans-Italic.ttf")
        REGULAR = Typeface.createFromAsset(assets, "fonts/open_sans/OpenSans-Regular.ttf")

    }

    @Synchronized
    fun getInstance(): Oppia {
        return instance
    }

    fun getRequestQueue(): RequestQueue {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(applicationContext)
        }
        return mRequestQueue!!
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String) {
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        getRequestQueue().add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue().add(req)
    }

    fun cancelPendingRequests(tag: Any) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }
}