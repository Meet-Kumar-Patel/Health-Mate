package com.example.project13application.ui.models

data class Subscriber(

    val username: String = "",

    val type: SubscriberType = SubscriberType.FAMILY_MEMBER,
    val canEdit: Boolean = false
)

// subscriber types enumeration
enum class SubscriberType {
    FAMILY_MEMBER,
    CAREGIVER
}