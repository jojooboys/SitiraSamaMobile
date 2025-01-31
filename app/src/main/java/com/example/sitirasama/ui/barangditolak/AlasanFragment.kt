package com.example.sitirasama.ui.barangditolak

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.R
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlasanFragment : Fragment() {

    private var barangDitolak: UserResponse? = null
    private lateinit var edtAlasan: EditText
    private lateinit var btnSubmit: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_alasan, container, false)
        barangDitolak = arguments?.getSerializable("barangDitolak") as? UserResponse

        val txtAlasan = root.findViewById<TextView>(R.id.textAlasan)
        edtAlasan = root.findViewById(R.id.editTextAlasan)
        btnSubmit = root.findViewById(R.id.btnSubmitAlasan)

        txtAlasan.text = "Alasan Penolakan"
        edtAlasan.setText(barangDitolak?.alasan ?: "")

        btnSubmit.setOnClickListener { confirmSubmitAlasan() }
        return root
    }

    private fun confirmSubmitAlasan() {
        AlertDialog.Builder(requireContext())
            .setMessage("Apakah Anda yakin memberikan alasan ini?")
            .setPositiveButton("Yes") { _, _ -> updateAlasanPenolakan() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun updateAlasanPenolakan() {
        barangDitolak?.id?.let { id ->
            val alasan = edtAlasan.text.toString()
            ApiClient.apiService.patchBarangDitolak(UserRequest(id = id, alasan = alasan))
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            // Kirim hasil ke DetailbarangditolakFragment
                            setFragmentResult("updateAlasan", Bundle().apply {
                                putString("newAlasan", alasan)
                            })

                            Toast.makeText(context, "Alasan berhasil diperbarui", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        } else {
                            Toast.makeText(context, "Gagal memperbarui alasan", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
