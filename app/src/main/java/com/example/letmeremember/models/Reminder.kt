package com.example.letmeremember.models

class Reminder {
    private var title: String? = null
    private var body: String? = null
    private var userId: String? = null
    private var isActive: Boolean? = null
    private var deletedAt: String? = null
    private var key: String? = null
    private var groupId: String? = null

    var not: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(key: String?, idgroup: String?, nombre: String?, body: String?, userId: String?, isActive: Boolean?, deletedAt: String?) {
        this.key = key
        this.groupId = idgroup
        this.title = nombre
        this.body = body
        this.userId = userId
        this.isActive = isActive
        this.deletedAt = deletedAt
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "key" to key,
            "groupId" to groupId,
            "title" to title,
            "body" to body,
            "userId" to userId,
            "isActive" to isActive,
            "deletedAt" to deletedAt,
            "not" to not,
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

    fun getGroupId(): String? {
        return groupId
    }

    fun setGroupId(groupId: String?) {
        this.groupId = groupId
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

    fun getKey(): String? {
        return key
    }

    fun setKey(key: String?) {
        this.key = key
    }
}