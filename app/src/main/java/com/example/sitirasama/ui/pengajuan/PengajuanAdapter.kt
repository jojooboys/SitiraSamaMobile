package com.example.sitirasama.ui.pengajuan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse

class PengajuanAdapter(private val pengajuanList: List<UserResponse>) :
    RecyclerView.Adapter<PengajuanAdapter.PengajuanViewHolder>() {

    class PengajuanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textId: TextView = view.findViewById(R.id.textId)
        val textBarang: TextView = view.findViewById(R.id.textBarang)
        val textDeskripsi: TextView = view.findViewById(R.id.textDeskripsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PengajuanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pengajuan, parent, false)
        return PengajuanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PengajuanViewHolder, position: Int) {
        val item = pengajuanList[position]
        holder.textId.text = "ID: ${item.id}"
        holder.textBarang.text = "Barang: ${item.barang}"
        holder.textDeskripsi.text = "Deskripsi: ${item.deskripsi}"
    }

    override fun getItemCount(): Int {
        return pengajuanList.size
    }
}
