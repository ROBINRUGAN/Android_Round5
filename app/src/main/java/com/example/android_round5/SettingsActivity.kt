package com.example.android_round5
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.android_round5.adapter.SettingsAdapter

class SettingsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: SettingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        listView = findViewById(R.id.listView)

        val settingItems = listOf(
            com.example.android_round5.adapter.SettingItem("手机号", "1234567890"),
            com.example.android_round5.adapter.SettingItem("身份证号", "1234567890"),
            com.example.android_round5.adapter.SettingItem("账号类型", "普通用户")
        )

        val listView: ListView = findViewById(R.id.listView)
        val adapter = SettingsAdapter(this, settingItems)
        listView.adapter = adapter

    }
}

data class SettingItem(val title: String, val value: String)
