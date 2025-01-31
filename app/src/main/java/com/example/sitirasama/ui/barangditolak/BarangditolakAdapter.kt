package com.example.sitirasama.ui.barangditolak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse

class BarangditolakAdapter(
    private var barangDitolakList: List<UserResponse>,
    private val onItemClick: (UserResponse) -> Unit
) : RecyclerView.Adapter<BarangditolakAdapter.BarangDitolakViewHolder>() {

    class BarangDitolakViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardBarangDitolak: CardView = view.findViewById(R.id.cardBarangditolak)
        val textId: TextView = view.findViewById(R.id.textId)
        val textUsername: TextView = view.findViewById(R.id.textUsername)
        val textBarang: TextView = view.findViewById(R.id.textBarang)
        val textDeskripsi: TextView = view.findViewById(R.id.textDeskripsi)
        val textAlasan: TextView = view.findViewById(R.id.textAlasan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangDitolakViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barangditolak, parent, false)
        return BarangDitolakViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarangDitolakViewHolder, position: Int) {
        val item = barangDitolakList[position]
        holder.textId.text = "ID: ${item.id}"
        holder.textUsername.text = "Username: ${item.username}"
        holder.textBarang.text = "Barang: ${item.barang}"
        holder.textDeskripsi.text = "Deskripsi: ${item.deskripsi}"
        holder.textAlasan.text = "Alasan: ${item.alasan ?: "Alasan belum ditambahkan"}"

        holder.cardBarangDitolak.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = barangDitolakList.size

    fun updateData(newList: List<UserResponse>) {
        barangDitolakList = newList
        notifyDataSetChanged()
    }
}
