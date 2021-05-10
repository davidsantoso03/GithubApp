package com.hfad.consumerapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Favorite(
    var id :Int,
    var login: String,
    var avatar_url: String,
    var location : String,
)
