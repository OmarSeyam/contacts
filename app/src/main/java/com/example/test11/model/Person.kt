package com.example.test11.model

import com.google.firebase.firestore.DocumentId

data class Person(@DocumentId var id:String, var name:String, var number:String, var address:String)