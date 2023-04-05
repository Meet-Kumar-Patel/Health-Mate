package com.example.project13application

data class Subscriber(

    val id: String = "",
    val username: String = "",

    val type: SubscriberType = SubscriberType.FAMILY_MEMBER,
    val canEdit: Boolean = false
)

// subscriber types enumeration
enum class SubscriberType {
    FAMILY_MEMBER,
    CAREGIVER
}