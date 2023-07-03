package com.example.demoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val setGoToAnalisa = findViewById<ImageView>(R.id.camera_circle)
        val setGoToUser = findViewById<ImageView>(R.id.account_circle)

        setGoToAnalisa.setOnClickListener{
            val intent = Intent(this@HomePage,AnalyzePage::class.java)
            startActivity(intent)
        }
        setGoToUser.setOnClickListener{
            val intent2 = Intent(this@HomePage,UserPage::class.java)
            startActivity(intent2)
        }

    }
}