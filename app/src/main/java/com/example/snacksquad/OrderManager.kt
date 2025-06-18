package com.example.snacksquad

object OrderManager {
    private val orderHistory = mutableListOf<Pair<List<CartItem>, Double>>()

    fun addOrder(items: List<CartItem>, total: Double) {
        orderHistory.add(Pair(items.map { it.copy() }, total))
    }

    fun getOrders(): List<Pair<List<CartItem>, Double>> {
        return orderHistory
    }
}
