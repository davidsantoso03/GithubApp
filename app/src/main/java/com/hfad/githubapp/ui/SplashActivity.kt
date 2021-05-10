package com.hfad.githubapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.hfad.githubapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val icon = findViewById<ImageView>(R.id.ic_splash)

        icon.alpha = 0f

        icon.animate().setDuration(1500).alpha(1f).withEndAction {

            val i = Intent(this, MainActivity::class.java)

            startActivity(i)

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            finish()
        }
    }
}