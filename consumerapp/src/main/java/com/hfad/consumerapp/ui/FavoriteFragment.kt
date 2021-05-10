package com.hfad.consumerapp.ui

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.consumerapp.R
import com.hfad.consumerapp.adapter.ConsumerAdapter
import com.hfad.consumerapp.data.Favorite
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {


    private lateinit var consumerAdapter: ConsumerAdapter

    companion object {
        private const val AUTHORITY = "com.hfad.githubapp"
        private const val TABLE_NAME = "favorite.db"
        private const val _ID = "id"
        private const val LOGIN = "login"
        private const val LOCATION = "location"
        private const val AVATAR = "avatar_url"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getFavoriteList()
    }

    private fun getFavoriteList() {
        val uriBuilder = Uri.Builder().apply {
            scheme("content")
            authority(AUTHORITY)
            appendPath(TABLE_NAME)
        }
        val uri = uriBuilder.build()

        val contentResolver = requireContext().contentResolver

        val cursor = contentResolver.query(uri, null, null, null, null)


        if (cursor != null) {
            setupRecyclerReview(cursor)
        }


    }



    private fun getFavList(cursor: Cursor): List<Favorite> {
        val favoriteList: MutableList<Favorite> = mutableListOf()

        cursor.apply {
            while (moveToNext()) {
                favoriteList.add(
                    Favorite(
                        getInt(getColumnIndexOrThrow(_ID)),
                        getString(getColumnIndexOrThrow(LOGIN)),
                        getString(getColumnIndexOrThrow(AVATAR)),
                        getString(getColumnIndexOrThrow(LOCATION))

                    )
                )
            }
            close()
        }

        return favoriteList.toList()
    }
    private fun setupRecyclerReview(cursor: Cursor) {
        rv_consumer.apply {
            consumerAdapter = ConsumerAdapter(getFavList(cursor))
            adapter = consumerAdapter
            layoutManager =LinearLayoutManager(activity)
        }
    }
}
