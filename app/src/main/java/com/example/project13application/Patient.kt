package com.example.project13application

data class Patient(

    // automatic generate by Firebase
    var id: String ?= null,
    var username: String ?= null,
    var firstName: String ?= null,
    var lastName: String ?= null,

    // one patient can have multiple diaries
    val diary: List<Diary>? = null,

    // one patient can have multiple subscribers
    // and we have two types of subscriber
    val familyMembers: List<Subscriber>? = null,
    val caregivers: List<Subscriber>? = null

)