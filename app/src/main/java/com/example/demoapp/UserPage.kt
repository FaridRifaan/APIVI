package com.example.demoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demoapp.databinding.ActivityForgotPasswordPageBinding
import com.example.demoapp.databinding.ActivityRegisterPageBinding
import com.example.demoapp.databinding.ActivityUserPageBinding
import com.google.firebase.auth.FirebaseAuth

class UserPage : AppCompatActivity() {
    lateinit var binding: ActivityUserPageBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null){
            binding.emailUser.setText(user.email)
        }

        binding.btnLogout.setOnClickListener{
            btnLogout()  
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun btnLogout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
    }
}