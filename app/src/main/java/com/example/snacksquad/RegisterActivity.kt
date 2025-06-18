package com.example.snacksquad

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var registerBtn: Button
    private lateinit var loginRedirect: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.registerName)
        emailEditText = findViewById(R.id.registerEmail)
        addressEditText = findViewById(R.id.registerAddress)
        registerBtn = findViewById(R.id.registerBtn)
        loginRedirect = findViewById(R.id.loginRedirect)

        registerBtn.setOnClickListener {
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
                    val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                    prefs.edit().apply {
                        putString("name", name)
                        putString("email", email)
                        putString("address", address)
                        putBoolean("isLoggedIn", true)
                        apply()
                    }
                    Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        loginRedirect.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
