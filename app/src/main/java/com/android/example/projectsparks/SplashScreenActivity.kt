package com.android.example.projectsparks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val tv: TextView = findViewById(R.id.textView)
        tv.animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text)

        val iv: ImageView = findViewById(R.id.imageIcon)
        iv.animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim_image)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, UserListActivity::class.java)
            intent.putExtra("isTransfer", false)
            startActivity(intent)
            finish()
        }, 3600)
    }
}