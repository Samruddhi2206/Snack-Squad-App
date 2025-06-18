package com.example.snacksquad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var logoutBtn: Button  // 🔴 Added logout button reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        nameEditText = findViewById(R.id.editProfileName)
        emailEditText = findViewById(R.id.editProfileEmail)
        addressEditText = findViewById(R.id.editProfileAddress)
        saveButton = findViewById(R.id.btnSaveProfile)
        logoutBtn = findViewById(R.id.logoutBtn) // 🔴 Bind logout button

        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Load existing values
        nameEditText.setText(prefs.getString("name", ""))
        emailEditText.setText(prefs.getString("email", ""))
        addressEditText.setText(prefs.getString("address", ""))

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                prefs.edit().apply {
                    putString("name", name)
                    putString("email", email)
                    putString("address", address)
                    apply()
                }

                Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        // 🔴 Log out button action
        logoutBtn.setOnClickListener {
            prefs.edit().clear().apply()  // Clear login data
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
