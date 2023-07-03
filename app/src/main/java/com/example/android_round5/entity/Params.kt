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
    val `data`: GetCodeData,
    val message: String
)

data class GetCodeData(
    val code: String
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