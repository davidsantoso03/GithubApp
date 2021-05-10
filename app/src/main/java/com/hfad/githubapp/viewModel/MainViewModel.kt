package com.hfad.githubapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.githubapp.data.Favorite
import com.hfad.githubapp.data.FavoriteRepository
import com.hfad.githubapp.databinding.ActivityMainBinding
import com.hfad.githubapp.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val listUser = MutableLiveData<ArrayList<User>>()
    private var listDataUser: ArrayList<User> = ArrayList()
    private lateinit var binding: ActivityMainBinding
    private lateinit var repo : FavoriteRepository
    val searchData = MutableLiveData<String>()

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    fun setListData() {

        val url = "https://api.github.com/users"
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
                        val responseObject = responseArray.getJSONObject(i)
                        val userName = responseObject.getString("login")
                        val photo = responseObject.getString("avatar_url")
                        val user = User()
                        user.login = userName
                        user.avatar_url = photo
                        setDetail(userName)
                    }



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
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("OnFailure",errorMessage)
            }
        })
    }

    fun setSearchData(query: String) {
        if (query.isNotEmpty()){
            listDataUser.clear()
        }
        val url = "https://api.github.com/search/users?q=$query"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 89ac05b6f335334d6896d2f3d9c53355da4c6b67")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
            ) {
                listDataUser.clear()
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val userName = item.getString("login")
                        val photo = item.getString("avatar_url")
                        val user = User()
                        user.login = userName
                        user.avatar_url = photo
                       setDetail(userName)
                    }
                    searchData.postValue(query)




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

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("OnFailure",errorMessage)

            }
        })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUser
    }
    fun setDetail(username: String) {
        val url = "https://api.github.com/users/$username"
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
                    val responseObject = JSONObject(result)


                    val nameUser = responseObject.getString("name")
                    val userName = responseObject.getString("login")
                    val photo = responseObject.getString("avatar_url")
                    val company = responseObject.getString("company")
                    val location = responseObject.getString("location")
                    val following = responseObject.getInt("following")
                    val followers = responseObject.getInt("followers")
                    val publicRepo = responseObject.getInt("public_repos")


                    val user = User()
                    user.login = userName
                    user.avatar_url = photo
                    user.company = company
                    user.location = location
                    user.name = nameUser
                    user.followers = followers
                    user.following = following
                    user.public_repos = publicRepo
                    listDataUser.add(user)
                    listUser.postValue(listDataUser)


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("OnFailure",errorMessage)
            }
        })
    }
    fun getDetail(): LiveData<ArrayList<User>> {
        return listUser
    }

    fun getFav(favorite : Favorite):LiveData<Favorite>{
        return repo.getFav(favorite.id)
    }



}