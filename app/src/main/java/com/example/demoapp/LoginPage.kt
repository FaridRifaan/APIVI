package com.example.demoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.demoapp.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
lateinit var binding: ActivityLoginPageBinding
lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener{
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
        binding.forgotYour.setOnClickListener(){
            val forGotIntent = Intent(this,ForgotPasswordPage::class.java)
            startActivity(forGotIntent)
        }
        binding.btnLogin.setOnClickListener{
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()
            //validasi email kosong
            if (email.isEmpty()){
                binding.emailLogin.error = "email harus di isi"
                binding.emailLogin.requestFocus()
                return@setOnClickListener
            }
            //validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailLogin.error = "email tidak sama :("
                binding.emailLogin.requestFocus()
                return@setOnClickListener
            }
            //Validasi password
            if (password.isEmpty()){
                binding.passwordLogin.error = "password harus Diisi!"
                binding.passwordLogin.requestFocus()
                return@setOnClickListener
            }

            loginPageFirebase(email,password)
        }
//        val setForgotPassword = findViewById<TextView>(R.id.register)
//        val setLogin = findViewById<Button>(R.id.btn_login)
//
//        setForgotPassword.setOnClickListener{
//            val intent = Intent(this@LoginPage,RegisterPage::class.java)
//            startActivity(intent)
//        }
//        setLogin.setOnClickListener{
//            val intent2 = Intent(this@LoginPage,HomePage::class.java)
//            startActivity(intent2)
//        }

    }

    private fun loginPageFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Selamat Datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}