package com.example.letmeremember.alarms

class AlarmClass {
    var groupId: String? = null
    var id1: Long? = null
    var id2: Long? = null
    var id3: Long? = null
    var key: String? = null

    var not: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(groupId: String?, id1: Long?, id2: Long?, id3: Long?) {
        this.groupId = groupId
        this.id1 = id1
        this.id2 = id2
        this.id3 = id3
    }
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "groupId" to groupId,
            "id1" to id1,
            "id2" to id2,
            "id3" to id3,
            "key" to key,
            "not" to not
        )
    }
}