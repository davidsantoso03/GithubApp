package com.hfad.githubapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.githubapp.model.Following
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<Following>>()

    val listDataUser: ArrayList<Following> = ArrayList()



    companion object {
        private val TAG = FollowingViewModel::class.java.simpleName
    }


    fun setListFollowing(username: String) {


        val url = "https://api.github.com/users/$username/following"

        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token 89ac05b6f335334d6896d2f3d9c53355da4c6b67")

        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {

            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
            )
            {

                val result = String(responseBody)

                Log.d(TAG,result)

                try {

                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val responseObject = responseArray.getJSONObject(i)
                        val userName = responseObject.getString("login")
                        val photo = responseObject.getString("avatar_url")
                        val numberId = responseObject.getInt("id")
                        val types = responseObject.getString("type")
                        val following = Following()
                        following.login = userName
                        following.avatar_url = photo
                        following.id = numberId
                        following.type = types
                        listDataUser.add(following)
//                        setDetail(userName)
                    }

                    listFollowing.postValue(listDataUser)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable
            ) {
                val errorMessage = when(statusCode){
                    401 -> "$statusCode : Bad Request"
                    402 -> "$statusCode : Forbidden "
                    403 -> "$statusCode : Not Found "
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("OnFailure", errorMessage)
            }

        })

    }


    fun getFollowing(): LiveData<ArrayList<Following>> {

        return listFollowing

    }
}