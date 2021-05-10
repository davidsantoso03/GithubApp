

package com.hfad.consumerapp.ui
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hfad.consumerapp.R
import com.hfad.consumerapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.title = "Consumer App"

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setFavFragment()



    }

    private fun setFavFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,FavoriteFragment()).commit()
    }


}

