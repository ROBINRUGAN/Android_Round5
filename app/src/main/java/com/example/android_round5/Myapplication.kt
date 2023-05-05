package com.example.android_round5;
import android.app.Application;
import com.example.android_round5.entity.HomeItem

class Myapplication :Application(){
  companion object{
   lateinit var application: Application
   var UserData: HomeItem? =null
        }
     init {
         application = this
     }
}
