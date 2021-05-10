    package com.hfad.githubapp.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hfad.githubapp.R
import com.hfad.githubapp.viewModel.MainViewModel
import com.hfad.githubapp.adapter.SectionPagerAdapter
import com.hfad.githubapp.data.Favorite
import com.hfad.githubapp.data.FavoriteDatabase
import com.hfad.githubapp.model.User
import com.hfad.githubapp.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailModel: MainViewModel

    val db by lazy { FavoriteDatabase(this) }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val EXTRA_POSITION = "extra_position"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupListener()

        val data = intent.getParcelableExtra<User>(EXTRA_USER) as User

        //tab Layout
        val username = data.login
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = username
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        //set Detail
        detailModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        supportActionBar?.title = data.login
        data.login?.let { detailModel.setDetail(it) }
        detailModel.getDetail().observe(this, {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(data.avatar_url)
                    .apply(RequestOptions().override(100, 100))
                    .into(imgPhoto)
                tvName.text = data.name
                tvCompany.text = data.company
                tvLocation.text = data.location
                tvFollowers.text = data.followers.toString()
                tvFollowing.text = data.following.toString()
                tvRepositories.text = data.public_repos.toString()
            }
        })
    }
        private fun setupListener() {
            val data = intent.getParcelableExtra<User>(EXTRA_USER) as User
            val img =data.avatar_url
            var statusFavDefault = false
            val fab = binding.fabButton
            setStatusFavorite(false)
            fab.setOnClickListener {
                setStatusFavorite(true)
                CoroutineScope(Dispatchers.IO).launch {
                    setStatusFavorite(true)
                    db.favDao().addFavorite(
                        Favorite(
                            0,
                            binding.tvName.text.toString(),
                            img.toString(),
                            binding.tvLocation.text.toString(),
                                binding.tvCompany.text.toString(),
                                binding.tvFollowers.text.toString(),
                                binding.tvFollowing.text.toString(),
                                binding.tvRepositories.text.toString()
                        )
                    )
                    finish()
                    statusFavDefault
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
//    private fun favouriteCheck(){
//        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + detailGithubUser.id)
//        val cursor = contentResolver.query(uriWithId, null, null, null, null)
//        val favourite = MappingHelper.mapCursorToArrayList(cursor)
//        for (data in favourite) {
//            if (detailGithubUser.id == data.id) {
        //                isFavourite = true
//                Log.d(TAG, "this User Favorite")
//            }
//        }
//    }
//

    }


