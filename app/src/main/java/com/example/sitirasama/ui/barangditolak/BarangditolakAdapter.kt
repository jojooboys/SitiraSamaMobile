package com.example.sitirasama.ui.barangditolak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse

class BarangditolakAdapter(private val barangDitolakList: List<UserResponse>) :
    RecyclerView.Adapter<BarangditolakAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textId: TextView = view.findViewById(R.id.textId)
        val textBarang: TextView = view.findViewById(R.id.textBarang)
        val textDeskripsi: TextView = view.findViewById(R.id.textDeskripsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barang_ditolak, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = barangDitolakList[position]
        holder.textId.text = "ID: ${item.id}"
        holder.textBarang.text = "Barang: ${item.barang}"
        holder.textDeskripsi.text = "Deskripsi: ${item.deskripsi}"
    }

    override fun getItemCount(): Int {
        return barangDitolakList.size
    }
}
