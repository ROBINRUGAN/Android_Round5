package com.example.android_round5.entity

data class Order(
    val buyer_id: String,
    val buyer_status: Int,
    val generate_time: String,
    val good_id: String,
    val good_title: String,
    val id: String,
    val price: Int,
    val seller_id: String,
    val seller_status: Int,
    val status: Int
)
