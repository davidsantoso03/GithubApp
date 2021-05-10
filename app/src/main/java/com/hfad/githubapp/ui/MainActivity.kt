package com.hfad.githubapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.githubapp.R
import com.hfad.githubapp.viewModel.MainViewModel
import com.hfad.githubapp.adapter.UserAdapter
import com.hfad.githubapp.data.Favorite
import com.hfad.githubapp.databinding.ActivityMainBinding
import com.hfad.githubapp.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var model: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = menu.findItem(R.id.search_user).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String): Boolean {


                if (query.isNotEmpty()) {

                    model.setSearchData(query)
                    loadingProgress(true)

                } else{
                   return true


                }


                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       if(item.itemId ==  R.id.favorite){
           val mv = Intent(this@MainActivity,FavoriteActivity::class.java)
           startActivity(mv)
       }else if (item.itemId == R.id.notification){
           val mv = Intent(this@MainActivity, ReminderActivity::class.java)

           startActivity(mv)
       }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        configLayoutManager()

        showRecyclerItem()


    }


    private fun configLayoutManager() {
        adapter = UserAdapter()
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)

        adapter.notifyDataSetChanged()
        binding.rvUser.adapter = adapter
    }

    private fun showRecyclerItem() {
        model = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        model.setListData()

        model.getUser().observe(this, { user ->
            if (user != null) {
                adapter.setUserData(user)
                loadingProgress(false)
            }
        })

        adapter.setOnItemCLickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                selectedUser(user)
            }

        })
    }

    private fun loadingProgress(b: Boolean) {
        if (b) {
            binding.progressBar.visibility = View.VISIBLE

        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun selectedUser(user: User) {
        val moveIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(moveIntent)
    }


}

