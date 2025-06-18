package com.example.snacksquad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var totalPriceTextView: TextView
    private lateinit var clearCartBtn: Button
    private lateinit var checkoutBtn: Button
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Initialize views
        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        clearCartBtn = findViewById(R.id.clearCartBtn)
        checkoutBtn = findViewById(R.id.checkoutBtn)

        // Set up RecyclerView
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(CartManager.getItems().toMutableList()) {
            updateTotal()  // Update total when quantity changes
        }
        cartRecyclerView.adapter = adapter

        // Set initial total price
        updateTotal()

        // Clear Cart Button
        clearCartBtn.setOnClickListener {
            CartManager.clearCart()
            adapter = CartAdapter(mutableListOf()) {
                updateTotal()
            }
            cartRecyclerView.adapter = adapter
            updateTotal()
        }

        // ✅ Checkout Button now opens Delivery Options screen
        checkoutBtn.setOnClickListener {
            val intent = Intent(this, DeliveryOptionsActivity::class.java)
            startActivity(intent)
        }
    }

    // Public function to update total price
    fun updateTotal() {
        totalPriceTextView.text = "Total: ₹${CartManager.getTotalPrice()}"
    }
}
