package com.example.anoopmohanan.bluetoothscanner.Adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anoopmohanan.bluetoothscanner.Model.BLEDevice
import com.example.anoopmohanan.bluetoothscanner.R
import kotlinx.android.synthetic.main.device_layout.view.*


/**
 * Created by anoopmohanan on 28/03/18.
 */
class DevicesAdapter(val items:List<BLEDevice>): RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val row = layoutInflater.inflate(R.layout.device_layout,parent,false)

        return ViewHolder(row)
    }


    class ViewHolder(deviceListCell: View): RecyclerView.ViewHolder(deviceListCell){

        val itemVw = deviceListCell

        fun bind(item: BLEDevice) {
            itemVw.textView.text = item.address
            itemVw.textView2.text = item.name
//            itemVw.mainText.text = item.countryName
//            itemVw.detailedText.text = item.countryCode
//            //itemView.setOnClickListener { Toast.makeText(it.context, "$item.countryName", Toast.LENGTH_SHORT).show() }
//            itemVw.setOnClickListener { loadDetails(itemView,item) }
        }

        fun loadDetails(itemView: View, item: BLEDevice){
//
//            val context = itemView.context
//            val showDetailsIntent = Intent(context, DetailActivity::class.java)
//            showDetailsIntent.putExtra("CountryName", item.countryCode)
//            context.startActivity(showDetailsIntent)

        }
    }
}