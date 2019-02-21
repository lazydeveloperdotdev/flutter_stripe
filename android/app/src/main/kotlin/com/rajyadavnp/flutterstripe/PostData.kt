package com.rajyadavnp.flutterstripe

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*

/**
 * @author Raj Yadav
 * @date Jan 12, 2018 11:03
 * @link www.rajyadav-np.blogspot.com
 * @since 1.0
 */

class PostData(private val keyValue: KeyValue) {

    //httpConnection.connect();
    val postData: String?
        get() {
            var httpConnection: HttpURLConnection? = null
            try {
                val mUrl = URL(keyValue.url)
                httpConnection = mUrl.openConnection() as HttpURLConnection
                httpConnection.requestMethod = keyValue.method
                httpConnection.connectTimeout = 30 * 1000
                httpConnection.readTimeout = 30 * 1000
                httpConnection.doInput = true

                if (keyValue.method.equals("POST")) {
                    httpConnection.doOutput = true
                    val stream = httpConnection.outputStream
                    val writer = BufferedWriter(OutputStreamWriter(stream, "UTF-8"))
                    writer.write(encodedPairs(keyValue.pairs!!))
                    writer.flush()
                    writer.close()
                    stream.close()
                }

                val responseCode = httpConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val br = BufferedReader(InputStreamReader(httpConnection.inputStream))
                    val sb = StringBuilder()
                    var line = br.readLine()

                    while (line != null) {
                        sb.append(line).append("\n")
                        line = br.readLine()
                    }
                    br.close()
                    return sb.toString()
                }
            } catch (e: IOException) {
                return "IOEx: " + e.message
            } catch (ex: Exception) {
                return "Ex: " + ex.message
            } finally {
                httpConnection?.disconnect()
            }
            return null
        }

    @Throws(UnsupportedEncodingException::class)
    private fun encodedPairs(pairs: HashMap<String, Any>): String {
        val res = StringBuilder()
        var first = true
        for ((key, value) in pairs) {
            if (first) {
                first = false
            } else {
                res.append("&")
            }
            res.append(URLEncoder.encode(key, "UTF-8"))
                    .append("=")
                    .append(URLEncoder.encode(value.toString(), "UTF-8"))

        }
        return res.toString()
    }
}