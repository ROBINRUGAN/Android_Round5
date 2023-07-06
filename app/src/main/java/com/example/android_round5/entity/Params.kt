package com.example.android_round5.entity

data class Login(
    val code: Int,
    val message: String,
    val status: Int,
    val token: String
)
data class Register(
    val code: Int,
    val message: String
)
data class GetCode(
    val code: Int,
    val message: String
)

data class UserInfo(
    val code: Int,
    val message: String,
    val user: UserInfoUser
)

data class UserInfoUser(
    val id: Long,
    val id_card: String,
    val money: String,
    val name: String,
    val nickname: String,
    val phone_number: String,
    val profile_photo: String,
    val status: Int,
    val username: String
)
data class HomeList(
    val code: Int,
    val `data`: ArrayList<HomeData>,
    val message: String
)

data class HomeData(
    val add_time: String,
    val content: String,
    val game: String,
    val id: String,
    val picture_url: String,
    val price: String,
    val seller_id: String,
    val seller_nickname: String,
    val seller_profile_photo: String,
    val status: Int,
    val title: String,
    val view: Int
)

data class DetailAddData(
    val code: Int,
    val message: String
)

data class BidOrderData(
    val code: Int,
    val msg: String
)
data class UrlData(
    val code: Int,
    val `data`: String,
    val message: String
)
