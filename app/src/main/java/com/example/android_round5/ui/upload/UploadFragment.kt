package com.example.android_round5.ui.upload

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.android_round5.AppService
import com.example.android_round5.TokenInterceptor
import com.example.android_round5.databinding.FragmentUploadBinding
import com.example.android_round5.entity.DetailAddData
import com.example.android_round5.entity.UrlData
import com.example.android_round5.util.URIPathHelper
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Part
import java.io.File

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var game: String

    private var pictureUrls = ""

    lateinit var part: MultipartBody.Part

    private val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(6)) { uris ->


            var cnt = 0
            if (uris.isNotEmpty()) {
                for (uri in uris) {
                    //获取真实路径
                    val helper = URIPathHelper()
                    val path = helper.getPath(requireContext(), uri)
                    when (cnt) {
                        0 -> {
                            val file = File(path.toString())
                            val requestBody = file.asRequestBody("image/jp".toMediaTypeOrNull())
                            part =
                                MultipartBody.Part.createFormData("picture", "picture", requestBody)
                            innerGetUrl(part, onResponse = { url ->
                                Glide.with(this@UploadFragment).load(url).into(binding.uploadImage1)
                                if (pictureUrls != "") {
                                    pictureUrls += ','
                                }
                                pictureUrls += url

                            })
                            cnt++
                        }
                        1 -> {
                            val file = File(path.toString())
                            val requestBody = file.asRequestBody("image/jp".toMediaTypeOrNull())
                            part =
                                MultipartBody.Part.createFormData("picture", "picture", requestBody)
                            innerGetUrl(part, onResponse = { url ->
                                Glide.with(this@UploadFragment).load(url).into(binding.uploadImage2)
                                if (pictureUrls != "") {
                                    pictureUrls += ','
                                }
                                pictureUrls += url
                            })
                            cnt++
                        }
                        2 -> {
                            val file = File(path.toString())
                            val requestBody = file.asRequestBody("image/jp".toMediaTypeOrNull())
                            part =
                                MultipartBody.Part.createFormData("picture", "picture", requestBody)
                            innerGetUrl(part, onResponse = { url ->
                                Glide.with(this@UploadFragment).load(url).into(binding.uploadImage3)
                                if (pictureUrls != "") {
                                    pictureUrls += ','
                                }
                                pictureUrls += url
                            })
                            cnt++
                        }
                        3 -> {
                            val file = File(path.toString())
                            val requestBody = file.asRequestBody("image/jp".toMediaTypeOrNull())
                            part =
                                MultipartBody.Part.createFormData("picture", "picture", requestBody)
                            innerGetUrl(part, onResponse = { url ->
                                Glide.with(this@UploadFragment).load(url).into(binding.uploadImage4)
                                if (pictureUrls != "") {
                                    pictureUrls += ','
                                }
                                pictureUrls += url
                            })
                            cnt++
                        }
                        4 -> {
                            val file = File(path.toString())
                            val requestBody = file.asRequestBody("image/jp".toMediaTypeOrNull())
                            part =
                                MultipartBody.Part.createFormData("picture", "picture", requestBody)
                            innerGetUrl(part, onResponse = { url ->
                                Glide.with(this@UploadFragment).load(url).into(binding.uploadImage5)
                                if (pictureUrls != "") {
                                    pictureUrls += ','
                                }
                                pictureUrls += url
                            })
                            cnt++
                        }
                        5 -> {
                            val file = File(path.toString())
                            val requestBody = file.asRequestBody("image/jp".toMediaTypeOrNull())
                            part =
                                MultipartBody.Part.createFormData("picture", "picture", requestBody)
                            innerGetUrl(part, onResponse = { url ->
                                Glide.with(this@UploadFragment).load(url).into(binding.uploadImage6)
                                if (pictureUrls != "") {
                                    pictureUrls += ','
                                }
                                pictureUrls += url
                            })
                            cnt++
                        }
                    }


                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)

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
        // 创建一个包含游戏选项的数组
        val games = arrayOf(
            "王者荣耀",
            "英雄联盟",
            "原神",
            "绝地求生",
            "和平精英",
            "崩坏：星穹铁道",
            "第五人格"
        )

        // 创建适配器，并设置游戏数组作为数据源
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, games)

        // 设置下拉列表的样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 将适配器设置给 Spinner
        binding.spinnerGames.adapter = adapter

        // 设置选择事件监听器
        binding.spinnerGames.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                game = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                game = "王者荣耀"
            }
        }

        binding.uploadAdd.setOnClickListener {
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        binding.uploadSubmit.setOnClickListener {
            appService.UploadGoods(
                game,
                binding.uploadTitle.text.toString(),
                binding.uploadContent.text.toString(),
                pictureUrls,
                binding.uploadAccount.text.toString(),
                binding.uploadPassword.text.toString(),
                binding.uploadPrice.text.toString(),
            ).enqueue(object : Callback<DetailAddData> {
                override fun onFailure(call: Call<DetailAddData>, t: Throwable) {
                    Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<DetailAddData>,
                    response: Response<DetailAddData>
                ) {
                    val resp = response.body()
                    if (resp != null) {

                        Toast.makeText(context, resp.message, Toast.LENGTH_SHORT).show()

                    }
                }
            })
        }
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun innerGetUrl(
        part: MultipartBody.Part,
        onResponse: (url: String) -> Unit,
    ) {
        //init
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
        appService.GetUrl(part).enqueue(object : Callback<UrlData> {
            override fun onResponse(
                call: Call<UrlData>,
                response: Response<UrlData>
            ) {

                if (response.body() != null) {
                    val url = response.body()!!.data
                    /**************************************************************/
                    Log.d("MEWWW", "我要输出了")
                    Log.d("MEWWW", response.message() + response.code().toString())
                    Toast.makeText(context, "获取成功", Toast.LENGTH_SHORT).show()
                    onResponse(url)
                    /**************************************************************/
                } else {
                    Log.d("MEWWW", "我要输出了")
                    Log.d("MEWWW", response.message() + response.code().toString())
                    Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<UrlData>,
                t: Throwable
            ) {
                t.printStackTrace()
            }
        })
    }
}