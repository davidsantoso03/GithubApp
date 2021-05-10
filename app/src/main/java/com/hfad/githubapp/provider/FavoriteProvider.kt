package com.hfad.githubapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.hfad.githubapp.data.FavDao
import com.hfad.githubapp.data.FavoriteDatabase

class FavoriteProvider : ContentProvider() {
    private lateinit var favDao : FavDao

    companion object{
        private const val AUTHORITY = "com.hfad.githubapp"
        private const val TABLE_NAME = "favorite.db"
        private const val FAVORITE = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply { addURI(AUTHORITY, TABLE_NAME, FAVORITE) }

    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {
       favDao = FavoriteDatabase.invoke(context as Context).favDao()
        return  true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return  when (sUriMatcher.match(uri)){
            FAVORITE -> favDao.favListConsumer()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }
}