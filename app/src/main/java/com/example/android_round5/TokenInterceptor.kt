package com.example.android_round5
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val requestBuilder: Request.Builder = originalRequest.newBuilder()
            .header("Authorization", "$token") // 添加令牌头部
            .header("token", "$token") // 添加令牌头部
        val modifiedRequest: Request = requestBuilder.build()
        return chain.proceed(modifiedRequest)
    }
}
