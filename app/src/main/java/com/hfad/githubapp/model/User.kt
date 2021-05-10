package com.hfad.githubapp.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
        var login: String? = null,
        var avatar_url: String? = null,
        var name : String? = null,
        var company : String? = null,
        var location : String? = null,
        var followers: Int? = null,
        var following: Int? = null,
        var public_repos :Int? = null

        ) : Parcelable
