package com.example.sitirasama.ui.barangditolak

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sitirasama.R
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlasanFragment : Fragment() {

    private var idBarangDitolak: Int? = null
    private lateinit var editTextAlasan: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_alasan, container, false)

        editTextAlasan = root.findViewById(R.id.editTextAlasan)
        val buttonSubmit = root.findViewById<Button>(R.id.buttonSubmit)
        val buttonCancel = root.findViewById<Button>(R.id.buttonCancel)

        idBarangDitolak = arguments?.getInt("id_barang")

        buttonSubmit.setOnClickListener { updateAlasanPenolakan() }
        buttonCancel.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() } // ✅ Perbaikan Deprecation Warning

        return root
    }

    private fun updateAlasanPenolakan() {
        val id = idBarangDitolak ?: return
        val alasan = editTextAlasan.text.toString().trim()

        if (alasan.isEmpty()) {
            Toast.makeText(context, "Masukkan alasan penolakan!", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UserRequest().apply {
            this.id = id
            this.alasan = alasan
        }

        ApiClient.apiService.patchBarangDitolak(request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Log.d("API_DEBUG", "Barang berhasil diperbarui")
                    Toast.makeText(context, "Alasan penolakan berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressedDispatcher.onBackPressed() // ✅ Perbaikan Deprecation Warning
                } else {
                    Log.e("API_DEBUG", "Gagal memperbarui barang: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Gagal memperbarui alasan!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("API_DEBUG", "Terjadi kesalahan: ${t.message}")
                Toast.makeText(context, "Kesalahan koneksi ke server!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
