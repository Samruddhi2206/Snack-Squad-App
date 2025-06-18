package com.example.snacksquad

data class CartItem(
    val name: String,
    val price: Double,
    var quantity: Int = 1
)
