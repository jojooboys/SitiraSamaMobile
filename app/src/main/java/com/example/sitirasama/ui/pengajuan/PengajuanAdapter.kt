package com.example.sitirasama.ui.pengajuan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse

class PengajuanAdapter(
    private var pengajuanList: List<UserResponse>,
    private val onItemClick: (UserResponse) -> Unit
) : RecyclerView.Adapter<PengajuanAdapter.PengajuanViewHolder>() {

    class PengajuanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardPengajuan: CardView = view.findViewById(R.id.cardPengajuan)
        val textId: TextView = view.findViewById(R.id.textId)
        val textUsername: TextView = view.findViewById(R.id.textUsername)
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
        holder.textUsername.text = "Username: ${item.username}"
        holder.textBarang.text = "Barang: ${item.barang}"
        holder.textDeskripsi.text = "Deskripsi: ${item.deskripsi}"

        // Klik item untuk menampilkan DetailPengajuanFragment
        holder.cardPengajuan.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = pengajuanList.size

    fun updateData(newList: List<UserResponse>) {
        pengajuanList = newList
        notifyDataSetChanged()
    }
}
