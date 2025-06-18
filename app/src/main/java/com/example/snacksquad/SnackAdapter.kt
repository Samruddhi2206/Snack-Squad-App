package com.example.snacksquad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class SnackAdapter(private val snackList: List<Snack>) :
    RecyclerView.Adapter<SnackAdapter.SnackViewHolder>() {

    class SnackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val snackImage: ImageView = itemView.findViewById(R.id.snackImage)
        val snackName: TextView = itemView.findViewById(R.id.snackName)
        val snackPrice: TextView = itemView.findViewById(R.id.snackPrice)
        val addBtn: Button = itemView.findViewById(R.id.addToCartBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_snack, parent, false)
        return SnackViewHolder(view)
    }

    override fun onBindViewHolder(holder: SnackViewHolder, position: Int) {
        val snack = snackList[position]
        holder.snackName.text = snack.name
        holder.snackPrice.text = snack.price
        holder.snackImage.setImageResource(snack.imageResId)  // Set image here

        holder.addBtn.setOnClickListener {
            val cleanedPrice = snack.price.replace("₹", "").trim().toDoubleOrNull() ?: 0.0

            val cartItem = CartItem(
                name = snack.name,
                price = cleanedPrice,
                quantity = 1
            )
            CartManager.addItem(cartItem)
            Toast.makeText(
                holder.itemView.context,
                "${snack.name} added to cart!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int = snackList.size
}
