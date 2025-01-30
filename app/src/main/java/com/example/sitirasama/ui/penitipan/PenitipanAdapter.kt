package com.example.sitirasama.ui.penitipan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse

class PenitipanAdapter(private val penitipanList: List<UserResponse>) :
    RecyclerView.Adapter<PenitipanAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textId: TextView = view.findViewById(R.id.textId)
        val textBarang: TextView = view.findViewById(R.id.textBarang)
        val textDeskripsi: TextView = view.findViewById(R.id.textDeskripsi)
        val textStatus: TextView = view.findViewById(R.id.textStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_penitipan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = penitipanList[position]
        holder.textId.text = "ID: ${item.id}"
        holder.textBarang.text = "Barang: ${item.barang}"
        holder.textDeskripsi.text = "Deskripsi: ${item.deskripsi}"
        holder.textStatus.text = "Status: ${item.statuspenitipan}"
    }

    override fun getItemCount(): Int {
        return penitipanList.size
    }
}
