package com.example.project13application

data class Subscriber(

    val id: String,
    val username: String,

    val type: SubscriberType,
    val canEdit: Boolean
)

// subscriber types enumeration
enum class SubscriberType {
    FAMILY_MEMBER,
    CAREGIVER
}