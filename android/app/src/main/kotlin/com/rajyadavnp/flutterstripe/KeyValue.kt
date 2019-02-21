package com.rajyadavnp.flutterstripe


import java.util.*

/**
 * @author Raj Yadav
 * @date Jan 10, 2018 00:33
 * @link www.rajyadav-np.blogspot.com
 * @since 1.0
 */

class KeyValue {
    var url: String? = null
        private set
    var method: String? = null
        private set
    var pairs: HashMap<String, Any>? = null

    constructor(url: String, method: String) {
        this.url = url
        this.method = method
    }

    constructor(url: String, method: String, pairs: HashMap<String, Any>) {
        this.url = url
        this.method = method
        this.pairs = pairs
    }
}
