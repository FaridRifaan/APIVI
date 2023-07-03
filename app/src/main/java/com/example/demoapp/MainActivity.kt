package com.example.demoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, LoginPage::class.java)
            startActivity(intent)
            finish()
        }, 3000)

//        val setHello = findViewById<TextView>(R.id.hello)
//        setHello.setOnClickListener{
//            val intent = Intent(this@MainActivity,LoginPage::class.java)
//            startActivity(intent)
//        }
    }
}