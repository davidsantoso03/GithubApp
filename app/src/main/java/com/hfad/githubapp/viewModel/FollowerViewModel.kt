package com.hfad.githubapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.githubapp.model.Follower
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowerViewModel : ViewModel() {
   private val listFollower = MutableLiveData<ArrayList<Follower>>()



    companion object {
        private val TAG = FollowerViewModel::class.java.simpleName
    }

    fun setListFollower(username: String) {
        val listDataUser =  ArrayList<Follower>()
        val url = "https://api.github.com/users/$username/followers"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 89ac05b6f335334d6896d2f3d9c53355da4c6b67")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val follow = responseArray.getJSONObject(i)
                        val userName = follow.getString("login")
                        val photo = follow.getString("avatar_url")
                        val numberId = follow.getInt("id")
                        val types = follow.getString("type")
                        val follower = Follower()
                        follower.login = userName
                        follower.avatar_url = photo
                        follower.id = numberId
                        follower.type = types
                        listDataUser.add(follower)
                    }
                    listFollower.postValue(listDataUser)
                    Log.d(TAG,"onSuccess:Finished")

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    e.printStackTrace()
                }

            }

            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable
            ) {
                val errorMessage = when(statusCode){
                    401 -> "$statusCode : Bad request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode :${error.message}"
                }
                Log.d("OnFailure", errorMessage)
            }
        })
    }

    fun getFollower(): LiveData<ArrayList<Follower>> {
        return listFollower
    }

}