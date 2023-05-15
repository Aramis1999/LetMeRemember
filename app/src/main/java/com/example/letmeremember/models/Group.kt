package com.example.letmeremember.models

class Group {
    fun key(key: String?) {
    }
    private var nombre: String? = null
    private var idGroup: String? = null
    private var key: String? = null
    private var userId: String? = null
    private var isActive: Boolean? = null
    private var percent: String? = null

    var not: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(nombre: String?,userId: String?,isActive: Boolean?,idGroup: String?,percent: String?) {
        this.nombre = nombre
        this.userId = userId
        this.isActive = isActive
        this.idGroup = idGroup
        this.percent = percent
    }
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "percent" to percent,
            "key" to key,
            "not" to not
        )
    }

    fun getPercent(): String? {
        return percent
    }

    fun setPercent(percent: String?) {
        this.percent = percent
    }
    fun getNombre(): String? {
        return nombre
    }

    fun setNombre(nombre: String?) {
        this.nombre = nombre
    }

    fun setId(id: String?) {
        this.idGroup = id
    }

    fun getId(): String? {
        return idGroup
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    fun getIsActive(): Boolean? {
        return isActive
    }


    fun setIsActive(isActive: Boolean?) {
        this.isActive = isActive
    }
}