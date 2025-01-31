package com.example.sitirasama.ui.penitipan

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.R
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.util.SessionManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailpenitipanFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private var penitipan: UserResponse? = null
    private var userStatus: String? = null
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_detailpenitipan, container, false)
        sessionManager = SessionManager(requireContext())
        penitipan = arguments?.getSerializable("penitipan") as? UserResponse

        val txtBarang: TextView = root.findViewById(R.id.textBarang)
        val txtDeskripsi: TextView = root.findViewById(R.id.textDeskripsiPenitipan)
        btnDelete = root.findViewById(R.id.btnDeletePenitipan)
        btnUpdate = root.findViewById(R.id.btnUpdateStatusPenitipan)

        txtBarang.text = penitipan?.barang
        txtDeskripsi.text = penitipan?.deskripsi

        // ✅ Panggil API untuk mendapatkan status pengguna
        getUserProfile { status ->
            userStatus = status
            if (userStatus == "satpam") {
                btnDelete.setOnClickListener { deletePenitipan() }
                btnUpdate.setOnClickListener { updateStatusPenitipan() }
            } else {
                btnDelete.visibility = View.GONE
                btnUpdate.visibility = View.GONE
            }
        }
        return root
    }


    private fun deletePenitipan() {
        // Tampilkan dialog konfirmasi sebelum menghapus
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Hapus")
            .setMessage("Apakah Anda yakin ingin menghapus barang ini dari penitipan?")
            .setPositiveButton("Yes") { _, _ ->
                // Jika pengguna memilih "Yes", lakukan penghapusan
                penitipan?.id?.let { id ->
                    ApiClient.apiService.deleteBarang(id).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Barang berhasil dihapus", Toast.LENGTH_SHORT).show()
                                findNavController().navigateUp() // Kembali ke PenitipanFragment
                            } else {
                                Toast.makeText(context, "Gagal menghapus barang", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
            .setNegativeButton("No", null) // Jika "No", tidak ada aksi yang dilakukan
            .show()
    }

    private fun updateStatusPenitipan() {
        // Pastikan penitipan tidak null
        if (penitipan == null) {
            Toast.makeText(context, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
            return
        }

        // Tentukan status baru berdasarkan status saat ini
        val currentStatus = penitipan?.statuspenitipan ?: return
        val newStatus = if (currentStatus == "Dalam Penitipan") "Sudah Dikembalikan" else "Dalam Penitipan"

        // Tampilkan dialog konfirmasi sebelum mengubah status
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Perubahan Status")
            .setMessage("Anda yakin mengubah status penitipan barang ini?")
            .setPositiveButton("Yes") { _, _ ->
                // Jika pengguna memilih "Yes", lakukan update status
                penitipan?.id?.let { id ->
                    ApiClient.apiService.patchBarang(id, UserRequest(statuspenitipan = newStatus))
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Status diperbarui menjadi $newStatus", Toast.LENGTH_SHORT).show()
                                    findNavController().navigateUp() // Kembali ke PenitipanFragment
                                } else {
                                    Toast.makeText(context, "Gagal memperbarui status", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }
            .setNegativeButton("No", null) // Jika "No", tidak ada aksi yang dilakukan
            .show()
    }


    private fun getUserProfile(callback: (String?) -> Unit) {
        ApiClient.apiService.getProfil().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    callback(user?.status)
                } else {
                    callback(null)
                    Toast.makeText(context, "Gagal mengambil data profil!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                callback(null)
                Toast.makeText(context, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
