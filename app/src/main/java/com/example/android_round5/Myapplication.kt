package com.example.android_round5;
import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class Myapplication :Application(){
//  companion object{
//   lateinit var application: Application
//   var UserData: HomeItem? =null
//        }
//     init {
//         application = this
//     }
    override fun onCreate() {
    super.onCreate()
    Fresco.initialize(this)
    }

}