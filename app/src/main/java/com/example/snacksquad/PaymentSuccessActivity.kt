package com.example.snacksquad

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PaymentSuccessActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var feedbackEditText: EditText
    private lateinit var submitFeedbackBtn: Button
    private lateinit var homeBtn: Button
    private lateinit var trackOrderBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        // Initialize views
        ratingBar = findViewById(R.id.ratingBar)
        feedbackEditText = findViewById(R.id.feedbackEditText)
        submitFeedbackBtn = findViewById(R.id.submitFeedbackBtn)
        homeBtn = findViewById(R.id.backToHomeBtn)
        trackOrderBtn = findViewById(R.id.trackOrderBtn)

        // Submit Feedback
        submitFeedbackBtn.setOnClickListener {
            val rating = ratingBar.rating
            val feedback = feedbackEditText.text.toString().trim()

            if (feedback.isEmpty()) {
                Toast.makeText(this, "Please enter feedback", Toast.LENGTH_SHORT).show()
            } else {
                // Feedback submission logic (local storage or Firebase can be added here)
                Toast.makeText(this, "Thanks for your feedback!", Toast.LENGTH_LONG).show()
                ratingBar.rating = 0f
                feedbackEditText.setText("")
            }
        }

        // Back to Home
        homeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        // Track Order
        trackOrderBtn.setOnClickListener {
            val intent = Intent(this, OrderTrackingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
