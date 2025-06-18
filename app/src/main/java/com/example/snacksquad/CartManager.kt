package com.example.snacksquad

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        val existingItem = cartItems.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            cartItems.add(item)
        }
    }

    // Updated removeItem: removes all entries of the item by name
    fun removeItem(item: CartItem) {
        cartItems.removeAll { it.name == item.name }
    }

    // Optional: decrement quantity or remove completely
    fun decrementItem(item: CartItem) {
        val existingItem = cartItems.find { it.name == item.name }
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                existingItem.quantity -= 1
            } else {
                cartItems.remove(existingItem)
            }
        }
    }

    fun updateItem(updatedItem: CartItem) {
        val index = cartItems.indexOfFirst { it.name == updatedItem.name }
        if (index != -1) {
            cartItems[index] = updatedItem
        }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getItems(): List<CartItem> {
        return cartItems
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf {
            it.price * it.quantity
        }
    }
}
