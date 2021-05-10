package com.hfad.githubapp.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavDao {
    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    fun readAllData() :  List<Favorite>

    @Query("SELECT * FROM Favorite WHERE id = :id")
    fun getFavById(id:Int): LiveData<Favorite>

    @Delete
    suspend fun deleteData(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
   fun favListConsumer() : Cursor

}