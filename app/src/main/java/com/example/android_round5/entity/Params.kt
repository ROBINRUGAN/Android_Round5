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
    val id: String,
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

data class OrderList(
    val code: Int,
    val `data`: List<OrderData>,
    val msg: String
)

data class OrderData(
    val buyer_id: String,
    val buyer_status: Int,
    val generate_time: String,
    val good_id: String,
    val good_title: String,
    val id: String,
    val price: Double,
    val seller_id: String,
    val seller_status: Int,
    val status: Int
)

data class ChatList(
    val code: Int,
    val `data`: List<ChatListData>,
    val message: String
)

data class ChatListData(
    val last_message: String,
    val last_message_id: String,
    val last_message_time: String,
    val person_id: String,
    val person_nickname: String,
    val person_profile_photo: String
)

data class MessageList(
    val code: Int,
    val `data`: List<MessageListData>,
    val message: String
)

data class MessageListData(
    val message: String,
    val message_id: String,
    val receive_id: String,
    val send_id: String,
    val send_time: String,
    val type: Int
)

data class MessageReceipt(
    val code: Int,
    val message: String,
    val message_id: String,
    val receive_id: String,
    val send_id: String,
    val send_time: String,
    val user_id: String,
    val type: Int
)

data class SendData(
    val message: String,
    val receive_id: String,
    val type: Int
)