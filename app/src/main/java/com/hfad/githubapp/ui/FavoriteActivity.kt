package com.hfad.githubapp.ui
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.githubapp.adapter.FavoriteAdapter
import com.hfad.githubapp.data.Favorite
import com.hfad.githubapp.data.FavoriteDatabase
import com.hfad.githubapp.databinding.ActivityFavoriteBinding
import com.hfad.githubapp.ui.DetailActivity.Companion.EXTRA_FAVORITE
import kotlinx.coroutines.*

class FavoriteActivity : AppCompatActivity() {

     private lateinit var binding :ActivityFavoriteBinding
     private lateinit var favAdapter: FavoriteAdapter



    val db by lazy { FavoriteDatabase(this) }

    companion object{
        val TAG: String = FavoriteActivity::class.java.simpleName
        private const val EXTRA_STATE = "EXTRA_STATE"
    }


    override fun onResume() {
        super.onResume()
        loadData()
    }
//
    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            favAdapter.setFavList(db.favDao().readAllData() as ArrayList<Favorite>)
            withContext(Dispatchers.Main){
                favAdapter.notifyDataSetChanged()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite"

        favAdapter = FavoriteAdapter(arrayListOf(), object : FavoriteAdapter.OnItemClickCallback {


            override fun onDeleteFav(favorite: Favorite) {
                CoroutineScope(Dispatchers.IO).launch {
                   db.favDao().deleteData(favorite)
                    loadData()
                }
            }

            override fun setOnItemClickCallback(favorite: Favorite) {
               selectedFav(favorite)
            }
        })

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = favAdapter

        }
    }


    private fun selectedFav(favorite: Favorite){
        val intent = Intent(this@FavoriteActivity,DetailFavActivity::class.java)
        intent.putExtra(EXTRA_FAVORITE,favorite)
        startActivity(intent)
    }
}



