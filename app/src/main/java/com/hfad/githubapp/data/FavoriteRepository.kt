package com.hfad.githubapp.data


import androidx.lifecycle.LiveData

class FavoriteRepository(private val favDao: FavDao) {
    fun getAllFavorite(): List<Favorite> {
        return  favDao.readAllData()
    }

    fun getFav(id:Int) :LiveData<Favorite>{
        return favDao.getFavById(id)
    }


}