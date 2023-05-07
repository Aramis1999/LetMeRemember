package com.example.letmeremember.models

class Trash {
        fun key(key: String?) {
        }
        private var title: String? = null
        private var body: String? = null
        private var url: String? = null
        private var userId: String? = null
        private var isActive: Boolean? = null
        private var deletedAt: String? = null
        private var id: String? = null
        private var key: String? = null

        var not: MutableMap<String, Boolean> = HashMap()

        constructor() {}
        constructor(nombre: String?, body: String?, url: String?, userId: String?, isActive: Boolean?, deletedAt: String?, id: String?) {
            this.title = nombre
            this.body = body
            this.url = url
            this.userId = userId
            this.isActive = isActive
            this.deletedAt = deletedAt
            this.id = id
        }
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "title" to title,
                "body" to body,
                "url" to url,
                "userId" to userId,
                "isActive" to isActive,
                "deletedAt" to deletedAt,
                "key" to key,
                "not" to not
            )
        }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getBody(): String? {
        return body
    }

    fun setBody(body: String?) {
        this.body = body
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String?) {
        this.url = url
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

    fun getDeletedAt(): String? {
        return deletedAt
    }

    fun setDeletedAt(deletedAt: String?) {
        this.deletedAt = deletedAt
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }
}