package com.hfad.githubapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hfad.githubapp.R
import com.hfad.githubapp.data.Favorite
import com.hfad.githubapp.databinding.ActivityDetailFavBinding
import com.hfad.githubapp.ui.DetailActivity.Companion.EXTRA_FAVORITE

class DetailFavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFavBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setStatusFavorite(true)
        val favUser = intent.getParcelableExtra<Favorite>(EXTRA_FAVORITE)


        binding.apply {
            if (favUser != null) {
                supportActionBar?.title = favUser.login
                Glide.with(this@DetailFavActivity)
                        .load(favUser.avatar_url)
                        .apply(RequestOptions().override(100, 100))
                        .into(imgPhoto)
                tvName.text = favUser.login
                tvLocation.text = favUser.location
                tvCompany.text = favUser.company
                tvFollowing.text = favUser.following
                tvFollowers.text = favUser.followers
                tvRepositories.text = favUser.public_repos

            }

        }

    }


    private fun setStatusFavorite(b: Boolean) {
        if (b){
            binding.fabButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        }else {
            binding.fabButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
}
