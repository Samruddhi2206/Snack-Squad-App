package com.example.snacksquad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SharedPreferences
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Auto-login if already logged in
        if (prefs.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_login)

        // Views
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        addressEditText = findViewById(R.id.addressEditText)
        loginBtn = findViewById(R.id.loginBtn)
        registerText = findViewById(R.id.registerText)

        // 🔐 Login Validation
        loginBtn.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()

            val nameRegex = "^[a-zA-Z\\s]+$".toRegex()
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

            when {
                name.isEmpty() || email.isEmpty() || address.isEmpty() ->
                    Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()

                !name.matches(nameRegex) ->
                    Toast.makeText(this, "Name must contain only letters", Toast.LENGTH_SHORT).show()

                !email.matches(emailRegex) ->
                    Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()

                else -> {
                    prefs.edit().apply {
                        putString("name", name)
                        putString("email", email)
                        putString("address", address)
                        putBoolean("isLoggedIn", true)
                        apply()
                    }

                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        // 🔁 Navigate to Register Page
        registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
