package com.example.demoapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demoapp.databinding.ActivityRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterPage : AppCompatActivity() {
    lateinit var binding: ActivityRegisterPageBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()



            //validasi email kosong
            if (email.isEmpty()){
                binding.emailRegister.error = "email harus di isi"
                binding.emailRegister.requestFocus()
                return@setOnClickListener
            }
            //validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailRegister.error = "Bukan seperti itu email nya :("
                binding.emailRegister.requestFocus()
                return@setOnClickListener
            }
                //Validasi password
            if (password.isEmpty()){
                binding.passwordRegister.error = "Password harus di isi"
                binding.passwordRegister.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 5){
                binding.passwordRegister.error = "Password minimal 5 karakter"
                binding.passwordRegister.requestFocus()
                return@setOnClickListener

            }
            registerFirebase(email,password)
        }
    }

    private fun registerFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,LoginPage::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}