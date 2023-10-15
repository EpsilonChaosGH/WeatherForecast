package com.example.weatherforecast.ui.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.weatherforecast.R
import com.example.weatherforecast.model.SpinnerItem

class SettingSpinnerAdapter(context: Context, var items: Array<SpinnerItem>) :
    ArrayAdapter<SpinnerItem>(context, 0, items) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.item_language_spinner, parent, false)

        val label = rowView.findViewById<TextView>(R.id.languageItemTextView)
        val icon = rowView.findViewById<ImageView>(R.id.languageItemIcon)

        label.text = items[position].title
        icon.setImageResource(items[position].iconResId)

        return rowView
    }
}