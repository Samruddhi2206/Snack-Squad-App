package com.example.snacksquad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var snackRecycler: RecyclerView
    private lateinit var snackAdapter: SnackAdapter
    private lateinit var snackList: ArrayList<Snack>
    private lateinit var viewCartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Sample snack list
        snackList = arrayListOf(
            Snack("Chips", "₹40", R.drawable.chips),
            Snack("Cookies", "₹60", R.drawable.cookies),
            Snack("Popcorn", "₹50", R.drawable.popcorn)
        )

        // Setup RecyclerView
        snackRecycler = findViewById(R.id.snackRecyclerView)
        snackRecycler.layoutManager = LinearLayoutManager(this)
        snackAdapter = SnackAdapter(snackList)
        snackRecycler.adapter = snackAdapter

        // View Cart Button
        viewCartButton = findViewById(R.id.viewCartBtn)
        viewCartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Profile Icon Click
        val profileIcon = findViewById<ImageView>(R.id.profileIcon)
        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
