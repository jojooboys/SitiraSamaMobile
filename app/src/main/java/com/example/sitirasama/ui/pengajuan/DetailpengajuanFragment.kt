package com.example.sitirasama.ui.pengajuan

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sitirasama.R
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.util.SessionManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailpengajuanFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private var id: Int = 0
    private var username: String? = null
    private var barang: String? = null
    private var deskripsi: String? = null

    private lateinit var pengajuan: UserResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_detailpengajuan, container, false)

        sessionManager = SessionManager(requireContext())

        // ✅ Ambil data dari Bundle sebagai Serializable
        pengajuan = arguments?.getSerializable("pengajuan") as UserResponse

        val textDetail = root.findViewById<TextView>(R.id.textDetail)
        textDetail.text = "ID: ${pengajuan.id}\nBarang: ${pengajuan.barang}\nDeskripsi: ${pengajuan.deskripsi}"

        return root
    }


    private fun confirmDelete() {
        AlertDialog.Builder(requireContext())
            .setMessage("Anda yakin menghapus pengajuan ini?")
            .setPositiveButton("Yes") { _, _ -> deletePengajuan() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deletePengajuan() {
        ApiClient.apiService.deletePengajuan(id).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Pengajuan dihapus", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                } else {
                    Toast.makeText(context, "Gagal menghapus pengajuan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updatePengajuanStatus(status: String) {
        val request = UserRequest(id = id, status = status) // ✅ Perbaikan: Gunakan objek `UserRequest`

        ApiClient.apiService.patchPengajuan(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(context, "Pengajuan berhasil $status", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
