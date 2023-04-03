package com.example.letmeremember.models

class User {
    fun key(key: String?) {
    }
    var user: String? = null
    var name: String? = null
    var email: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()
    constructor() {}
    constructor(user: String?, name: String?, email: String?) {
        this.user = user
        this.name = name
        this.email = email
    }
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "user" to user,
            "name" to name,
            "email" to email,
            "key" to key,
            "per" to per
        )
    }
}