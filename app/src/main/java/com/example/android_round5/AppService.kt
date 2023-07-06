package com.example.android_round5

import com.example.android_round5.entity.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface AppService {

    /**
     * 用户名登录
     */
    @POST("/users/login/username")
    @Headers("Content-Type: application/json")
    fun LoginByUsername(@Body body: RequestBody): retrofit2.Call<Login>

    /**
     * 手机号登录
     */
    @POST("/users/login/phone")
    @Headers("Content-Type: application/json")
    fun LoginByPhone(@Body body: RequestBody): retrofit2.Call<Login>

    /**
     * 获取验证码
     */
    @POST("/users/sms")
    @Headers("Content-Type: application/json")
    fun GetCode(@Body body: RequestBody): retrofit2.Call<GetCode>

    /**
     * 注册
     */
    @POST("/users")
    @Headers("Content-Type: application/json")
    fun Register(@Body body: RequestBody): retrofit2.Call<Register>

    /**
     * 获取首页商品列表
     */
    @GET("/home-page?page=1&size=114514")
    fun GetHomePage(): retrofit2.Call<HomeList>

    /**
     * 获取猜你喜欢
     */
    @GET("/guess")
    fun GetGuessLike(): retrofit2.Call<HomeList>

    /**
     * 获取用户信息
     */
    @GET("/users")
    fun GetUserInfo(): retrofit2.Call<UserInfo>

    /**
     * 获取首页搜索
     */
    @GET("/search")
    fun GetSearch(
        @Query("keywords") keyword: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): retrofit2.Call<HomeList>

    /**
     * 添加心愿单
     */
    @POST("/users/favorites")
    @Headers("Content-Type: application/json")
    fun AddFavorite(@Body body: RequestBody): retrofit2.Call<DetailAddData>

    /**
     * 立即出价
     */
    @POST("/order/bid")
    @FormUrlEncoded
    fun BidOrder(
        @Field("good_id") good_id: String,
        @Field("money") money: Double
    ): retrofit2.Call<BidOrderData>

    /**
     * 上传图片，获取图片url
     */
    @Multipart
    @POST("/picture")
    fun GetUrl(
        @Part picture: MultipartBody.Part
    ): retrofit2.Call<UrlData>

    /**
     * 上传商品
     */
    @POST("/goods")
    @FormUrlEncoded
    fun UploadGoods(
        @Field("game") game: String,
        @Field("title") title: String,
        @Field("content") content: String,
        @Field("picture") picture: String,
        @Field("account") account: String,
        @Field("password") password: String,
        @Field("price") price: String,
    ): retrofit2.Call<DetailAddData>

//    @POST("register")
//    @Headers("Content-Type: application/json")
//    fun getregister(@Body body: RequestBody): retrofit2.Call<Register>
//
//    @POST("sys/item/listItem")
//    fun getprojectlist(): retrofit2.Call<ProjectList>
//
//    @POST("sys/item/add")
//
//    @Multipart
//    fun getadd(
//        @Query("title") title: String,
//        @Query("content") content: String,
//        @Query("telephone") telephone: String,
//        @Query("userid") userid: Int,
//        @Part file: MultipartBody.Part
//    ): retrofit2.Call<Add>
//
//    @POST("sys/item/getmyitem/{id}")
//    fun getprojectpersonallist(@Path("id") id:Int ): retrofit2.Call<ProjectList>
//
//    @POST("sys/item/viewItem/0")
//    fun getprojectunknownlist(): retrofit2.Call<ProjectList>
//
//    @POST("sys/item/viewItem/-1")
//    fun getprojectfaillist(): retrofit2.Call<ProjectList>
//
//    @POST("sys/item/viewItem/1")
//    fun getprojectpasslist(): retrofit2.Call<ProjectList>
//
//    @POST("sys/user/contribution")
//    fun getcontribution(@Body body: RequestBody): retrofit2.Call<Contribution>
//
//    @POST("sys/item/verify")
//    fun getcheck(@Body body: RequestBody): retrofit2.Call<Check>
//
//    @POST("sys/item/deletelist")
//    fun getdel(@Body body: RequestBody): retrofit2.Call<Check>
//
//    @POST("sys/item/find")
//    fun getsearchlist(@Query("title")title: String): retrofit2.Call<ProjectList>
}