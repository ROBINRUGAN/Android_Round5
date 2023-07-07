package com.example.android_round5.ui.me

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.android_round5.*
import com.example.android_round5.databinding.FragmentMeBinding
import com.example.android_round5.entity.BidOrderData
import com.example.android_round5.entity.DetailAddData
import com.example.android_round5.entity.GetCode
import com.example.android_round5.entity.UserInfo
import jsonOf
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MeFragment : Fragment() {

    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        //******************************************************************************************
        val sharedPreferences = context!!.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", null)
        val httpClient = OkHttpClient.Builder()
        if (token != null) {
            httpClient.addInterceptor(TokenInterceptor(token))
        }
        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("http://api.mewtopia.cn:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        //******************************************************************************************
        appService.GetUserInfo().enqueue(object : Callback<UserInfo> {
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                println("请求失败")
            }

            override fun onResponse(
                call: Call<UserInfo>,
                response: retrofit2.Response<UserInfo>
            ) {
                val userInfo = response.body()
                /**************************************************************/
                if (userInfo == null) {
                    val errorConverter: Converter<ResponseBody, UserInfo> =
                        retrofit.responseBodyConverter(UserInfo::class.java, arrayOfNulls(0))

                    val errorResponse: UserInfo? = errorConverter.convert(response.errorBody()!!)
                    if (errorResponse != null) {
                        Toast.makeText(context, errorResponse.message, Toast.LENGTH_SHORT).show()
                        val intent = android.content.Intent(activity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
                if (userInfo != null) {
                    Toast.makeText(context, userInfo.message, Toast.LENGTH_SHORT).show()
                    binding.meUsername.text = userInfo.user.username
                    binding.meNickname.text = userInfo.user.nickname
                    binding.meMoney.text = userInfo.user.money
                    Glide.with(this@MeFragment)
                        .load(userInfo.user.profile_photo)
                        .into(binding.meAvatar)
                    if (userInfo.user.status == 3) {
                        binding.adminMenu.visibility = View.VISIBLE
                    }
                }
                /**************************************************************/
            }
        })
        binding.meSetting.setOnClickListener {
            val intent = android.content.Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.meFavoriteBtn.setOnClickListener {
            val intent = android.content.Intent(activity, MyFavoriteActivity::class.java)
            startActivity(intent)
        }
        binding.meBuyBtn.setOnClickListener {
            val intent = android.content.Intent(activity, MyBuyActivity::class.java)
            startActivity(intent)
        }
        binding.meSellerBtn.setOnClickListener {
            val intent = android.content.Intent(activity, MySellActivity::class.java)
            startActivity(intent)
        }
        binding.meAdminGoods.setOnClickListener {
            val intent = android.content.Intent(activity, AdminGoodsActivity::class.java)
            startActivity(intent)
        }
        binding.topup.setOnClickListener {
            // 创建一个 AlertDialog.Builder 对象
            val builder = AlertDialog.Builder(context!!)

            // 设置对话框的标题和消息
            builder.setTitle("充值")
            builder.setMessage("请输入充值金额：")

            // 创建一个 EditText 用于用户输入
            val inputEditText = EditText(context)
            builder.setView(inputEditText)

            // 设置对话框的按钮和点击事件
            builder.setPositiveButton("确认") { dialog, which ->
                val bidAmount = inputEditText.text.toString().toDoubleOrNull()
                if (bidAmount != null) {
                    val data = jsonOf(
                        "type" to "recharge",
                        "money" to bidAmount
                    )


                    appService.Money(data)
                        .enqueue(object : Callback<DetailAddData> {
                            override fun onFailure(call: Call<DetailAddData>, t: Throwable) {
                                t.printStackTrace()
                            }

                            override fun onResponse(
                                call: Call<DetailAddData>,
                                response: Response<DetailAddData>
                            ) {

                                Log.d("MEWWW", response.code().toString() + response.message())
                                /*******************      封装好的错误处理      ******************/
                                /**************************************************************/
                                if (response.body() == null) {
                                    val errorConverter: Converter<ResponseBody, DetailAddData> =
                                        retrofit.responseBodyConverter(
                                            DetailAddData::class.java,
                                            arrayOfNulls(0)
                                        )

                                    val errorResponse: DetailAddData? =
                                        errorConverter.convert(response.errorBody()!!)
                                    if (errorResponse != null) {
                                        Toast.makeText(
                                            context,
                                            errorResponse.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                                if (response.body() != null) {
                                    Toast.makeText(
                                        context,
                                        response.body()!!.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                /**************************************************************/
                            }
                        })

                } else {
                    // 输入的出价金额无效，显示错误提示
                    Toast.makeText(context, "请输入有效的充值金额", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("取消") { dialog, which ->
                dialog.dismiss()
            }
            // 创建并显示 AlertDialog
            val dialog = builder.create()
            dialog.show()
        }
        binding.withdraw.setOnClickListener {
            // 创建一个 AlertDialog.Builder 对象
            val builder = AlertDialog.Builder(context!!)

            // 设置对话框的标题和消息
            builder.setTitle("提现")
            builder.setMessage("请输入提现金额：")

            // 创建一个 EditText 用于用户输入
            val inputEditText = EditText(context)
            builder.setView(inputEditText)

            // 设置对话框的按钮和点击事件
            builder.setPositiveButton("确认") { dialog, which ->
                val bidAmount = inputEditText.text.toString().toDoubleOrNull()
                if (bidAmount != null) {
                    val data = jsonOf(
                        "type" to "withdrawal",
                        "money" to bidAmount
                    )


                    appService.Money(data)
                        .enqueue(object : Callback<DetailAddData> {
                            override fun onFailure(call: Call<DetailAddData>, t: Throwable) {
                                t.printStackTrace()
                            }

                            override fun onResponse(
                                call: Call<DetailAddData>,
                                response: Response<DetailAddData>
                            ) {

                                Log.d("MEWWW", response.code().toString() + response.message())
                                /*******************      封装好的错误处理      ******************/
                                /**************************************************************/
                                if (response.body() == null) {
                                    val errorConverter: Converter<ResponseBody, DetailAddData> =
                                        retrofit.responseBodyConverter(
                                            DetailAddData::class.java,
                                            arrayOfNulls(0)
                                        )

                                    val errorResponse: DetailAddData? =
                                        errorConverter.convert(response.errorBody()!!)
                                    if (errorResponse != null) {
                                        Toast.makeText(
                                            context,
                                            errorResponse.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                                if (response.body() != null) {
                                    Toast.makeText(
                                        context,
                                        response.body()!!.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                /**************************************************************/
                            }
                        })

                } else {
                    // 输入的出价金额无效，显示错误提示
                    Toast.makeText(context, "请输入有效的提现金额", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("取消") { dialog, which ->
                dialog.dismiss()
            }
            // 创建并显示 AlertDialog
            val dialog = builder.create()
            dialog.show()
        }
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}