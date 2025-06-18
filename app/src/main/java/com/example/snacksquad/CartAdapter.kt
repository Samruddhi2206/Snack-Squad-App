package com.example.snacksquad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.cartItemName)
        val itemPrice: TextView = itemView.findViewById(R.id.cartItemPrice)
        val itemQuantity: TextView = itemView.findViewById(R.id.cartItemQuantity)
        val btnIncrease: Button = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease: Button = itemView.findViewById(R.id.btnDecrease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]

        holder.itemName.text = item.name
        holder.itemPrice.text = "₹${item.price}"
        holder.itemQuantity.text = item.quantity.toString()

        holder.btnIncrease.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            CartManager.updateItem(item)
            onCartUpdated()
        }

        holder.btnDecrease.setOnClickListener {
            item.quantity--
            if (item.quantity <= 0) {
                CartManager.removeItem(item)
                cartItems.removeAt(position)
                notifyItemRemoved(position)
            } else {
                notifyItemChanged(position)
                CartManager.updateItem(item)
            }
            onCartUpdated()
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
