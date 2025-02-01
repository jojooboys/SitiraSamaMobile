package com.example.sitirasama.ui.pengajuan

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.R
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class DetailpengajuanFragment : Fragment() {

    private lateinit var pengajuan: UserResponse
    private var userStatus: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_detailpengajuan, container, false)

        // Ambil data dari Bundle sebagai Serializable
        pengajuan = arguments?.getSerializable("pengajuan") as UserResponse

        val textDetail = root.findViewById<TextView>(R.id.textDetail)
        val deleteButton = root.findViewById<Button>(R.id.deleteButton)
        val terimaButton = root.findViewById<Button>(R.id.terimaButton)
        val tolakButton = root.findViewById<Button>(R.id.tolakButton)

        textDetail.text = "ID: ${pengajuan.id}\nBarang: ${pengajuan.barang}\nDeskripsi: ${pengajuan.deskripsi}"

        // Pastikan Delete Button memunculkan dialog konfirmasi
        deleteButton.setOnClickListener { confirmDelete() }

        // Panggil API untuk mendapatkan status pengguna dan atur tampilan tombol
        getUserProfile { status ->
            userStatus = status
            if (userStatus == "satpam") {
                terimaButton.visibility = View.VISIBLE
                tolakButton.visibility = View.VISIBLE

                terimaButton.setOnClickListener { confirmUpdateStatus("terima") }
                tolakButton.setOnClickListener { confirmUpdateStatus("tolak") }
            }
        }

        return root
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

    private fun confirmDelete() {
        AlertDialog.Builder(requireContext())
            .setMessage("Apakah Anda yakin menghapus pengajuan ini?")
            .setPositiveButton("Yes") { _, _ -> deletePengajuan() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deletePengajuan() {
        ApiClient.apiService.deletePengajuan(pengajuan.id ?: 0).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Pengajuan dihapus", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack() // Kembali ke PengajuanFragment setelah berhasil menghapus
                } else {
                    Toast.makeText(context, "Gagal menghapus pengajuan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmUpdateStatus(status: String) {
        val message = if (status == "terima") {
            "Apakah Anda yakin akan menerima barang ini?"
        } else {
            "Apakah Anda yakin akan menolak barang ini?"
        }

        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Yes") { _, _ -> updatePengajuanStatus(status) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun updatePengajuanStatus(status: String) {
        val request = UserRequest(id = pengajuan.id, status = status)

        ApiClient.apiService.patchPengajuan(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Pengajuan berhasil di$status", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack() // Kembali ke PengajuanFragment setelah update
                } else {
                    Toast.makeText(context, "Gagal memperbarui status pengajuan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
