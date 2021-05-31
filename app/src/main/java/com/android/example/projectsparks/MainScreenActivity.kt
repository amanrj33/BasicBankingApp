package com.android.example.projectsparks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Thread.sleep(3600)
        Handler(Looper.getMainLooper()).postDelayed({
                                                    //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UserListActivity::class.java)
            intent.putExtra("isTransfer", false)
            startActivity(intent)
            finish()
        }, 3600)
    }
}