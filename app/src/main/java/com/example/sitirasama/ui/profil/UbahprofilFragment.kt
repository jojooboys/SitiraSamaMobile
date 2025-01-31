package com.example.sitirasama.ui.profil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sitirasama.R
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.service.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UbahprofilFragment : Fragment() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextUsername: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_ubahprofil, container, false)

        editTextEmail = root.findViewById(R.id.editTextEmail)
        editTextUsername = root.findViewById(R.id.editTextUsername)
        val buttonSubmit = root.findViewById<Button>(R.id.buttonSubmit)
        val buttonCancel = root.findViewById<Button>(R.id.buttonCancel)

        buttonSubmit.setOnClickListener { updateProfil() }
        buttonCancel.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        return root
    }

    private fun updateProfil() {
        val email = editTextEmail.text.toString()
        val username = editTextUsername.text.toString()

        if (email.isEmpty() || username.isEmpty()) {
            Toast.makeText(context, "Email dan Username tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UserRequest(email = email, username = username)

        ApiClient.apiService.updateProfil(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressedDispatcher.onBackPressed() // ✅ Perbaikan Deprecation Warning
                } else {
                    Toast.makeText(context, "Gagal memperbarui profil!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}