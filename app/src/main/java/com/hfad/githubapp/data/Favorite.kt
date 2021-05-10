package com.hfad.githubapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite(
        @PrimaryKey(autoGenerate = true)
        var id:Int,
        var login:String,
        var avatar_url: String,
        var location: String,
        var company: String,
        var following: String ,
        var followers: String,
        var public_repos: String ,
        var checkFav: Boolean = false
) : Parcelable {

}
