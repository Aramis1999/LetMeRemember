package com.example.letmeremember.models

class Group {
    fun key(key: String?) {
    }
    var nombre: String? = null
    var key: String? = null

    var not: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(nombre: String?) {
        this.nombre = nombre
    }
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "key" to key,
            "not" to not
        )
    }
}