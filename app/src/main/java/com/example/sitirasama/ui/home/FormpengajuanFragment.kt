package com.example.sitirasama.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.R
import com.example.sitirasama.databinding.FragmentFormpengajuanBinding
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormpengajuanFragment : Fragment() {

    private var _binding: FragmentFormpengajuanBinding? = null
    private val binding get() = _binding!!
    private var username: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFormpengajuanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fetchProfil()

        binding.btnSubmit.setOnClickListener {
            confirmSubmit()
        }

        return root
    }

    private fun fetchProfil() {
        ApiClient.apiService.getProfil().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    username = user?.username
                    binding.textWelcome.text = "Hi, $username silahkan mengisi formulir berikut untuk penitipan barang di satpam :)"
                } else {
                    Log.e("API_DEBUG", "Gagal mendapatkan profil: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Gagal mengambil profil. Coba login ulang.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("API_DEBUG", "Terjadi kesalahan: ${t.message}")
                Toast.makeText(context, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmSubmit() {
        val barang = binding.editTextBarang.text.toString().trim()
        val deskripsi = binding.editTextDeskripsi.text.toString().trim()

        if (barang.isEmpty() || deskripsi.isEmpty()) {
            Toast.makeText(context, "Mohon isi semua data!", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Pengajuan")
            .setMessage("Apakah sudah yakin dengan isian formulir barang Anda?")
            .setPositiveButton("Yes") { _, _ ->
                submitPengajuan(barang, deskripsi)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun submitPengajuan(barang: String, deskripsi: String) {
        val request = UserRequest(barang = barang, deskripsi = deskripsi)

        ApiClient.apiService.createPengajuan(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Pengajuan berhasil dikirim!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack(R.id.navigation_home, false) // ✅ Kembali ke HomeFragment
                } else {
                    Toast.makeText(context, "Gagal mengirim pengajuan!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
