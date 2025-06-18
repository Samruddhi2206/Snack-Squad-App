package com.example.snacksquad

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)

        val confirmationText: TextView = findViewById(R.id.confirmationMessage)
        confirmationText.text = "Thank you for your purchase!\nYour order has been placed successfully."
    }
}
