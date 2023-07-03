package com.example.android_round5.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.android_round5.R

class SettingsAdapter(private val context: Context, private val settingItems: List<SettingItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return settingItems.size
    }

    override fun getItem(position: Int): Any {
        return settingItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_setting, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val settingItem = settingItems[position]

        holder.titleTextView.text = settingItem.title
        holder.valueTextView.text = settingItem.value

        return view!!
    }

    private class ViewHolder(view: View) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val valueTextView: TextView = view.findViewById(R.id.valueTextView)
    }
}

data class SettingItem(val title: String, val value: String)
