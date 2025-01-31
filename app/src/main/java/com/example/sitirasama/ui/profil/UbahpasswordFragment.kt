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

class UbahpasswordFragment : Fragment() {

    private lateinit var editTextOldPassword: EditText
    private lateinit var editTextNewPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_ubahpassword, container, false)

        editTextOldPassword = root.findViewById(R.id.editTextOldPassword)
        editTextNewPassword = root.findViewById(R.id.editTextNewPassword)
        val buttonSubmit = root.findViewById<Button>(R.id.buttonSubmit)
        val buttonCancel = root.findViewById<Button>(R.id.buttonCancel)

        buttonSubmit.setOnClickListener { updatePassword() }
        buttonCancel.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        return root
    }

    private fun updatePassword() {
        val oldPassword = editTextOldPassword.text.toString()
        val newPassword = editTextNewPassword.text.toString()

        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(context, "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UserRequest(oldPassword = oldPassword, newPassword = newPassword)

        ApiClient.apiService.updatePassword(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Password berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressedDispatcher.onBackPressed() // ✅ Perbaikan Deprecation Warning
                } else {
                    Toast.makeText(context, "Gagal memperbarui password!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}