package com.example.snacksquad

import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderTrackingActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var etaText: TextView
    private lateinit var progressBar: ProgressBar

    private val statuses = listOf(
        "Order Placed",
        "Preparing Your Snacks",
        "Out for Delivery",
        "Delivered"
    )

    private var currentIndex = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_tracking)

        statusText = findViewById(R.id.statusText)
        etaText = findViewById(R.id.etaText)
        progressBar = findViewById(R.id.progressBar)

        updateStatus()

        // Simulate real-time tracking every 3 seconds
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (currentIndex < statuses.size - 1) {
                    currentIndex++
                    updateStatus()
                    handler.postDelayed(this, 3000)
                }
            }
        }, 3000)
    }

    private fun updateStatus() {
        statusText.text = statuses[currentIndex]
        etaText.text = "Estimated Delivery in ${15 - (currentIndex * 5)} mins"
        progressBar.progress = ((currentIndex + 1) * 25)
    }
}
