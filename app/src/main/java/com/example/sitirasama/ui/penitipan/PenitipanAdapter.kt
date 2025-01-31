package com.example.sitirasama.ui.penitipan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse

class PenitipanAdapter(
    private var penitipanList: List<UserResponse>,
    private val onItemClick: (UserResponse) -> Unit
) : RecyclerView.Adapter<PenitipanAdapter.PenitipanViewHolder>() {

    class PenitipanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardPenitipan: CardView = view.findViewById(R.id.cardPenitipan) // Menggunakan ID yang sama dengan Pengajuan
        val textId: TextView = view.findViewById(R.id.textId)
        val textUsername: TextView = view.findViewById(R.id.textUsername)
        val textBarang: TextView = view.findViewById(R.id.textBarang)
        val textDeskripsi: TextView = view.findViewById(R.id.textDeskripsi)
        val textStatus: TextView = view.findViewById(R.id.textStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenitipanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_penitipan, parent, false)
        return PenitipanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PenitipanViewHolder, position: Int) {
        val item = penitipanList[position]
        holder.textId.text = "ID: ${item.id}"
        holder.textUsername.text = "Username: ${item.username}"
        holder.textBarang.text = "Barang: ${item.barang}"
        holder.textDeskripsi.text = "Deskripsi: ${item.deskripsi}"
        holder.textStatus.text = "Status: ${item.statuspenitipan ?: "Tidak diketahui"}"

        // Klik item untuk menampilkan DetailPenitipanFragment
        holder.cardPenitipan.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = penitipanList.size

    fun updateData(newList: List<UserResponse>) {
        penitipanList = newList
        notifyDataSetChanged()
    }
}
