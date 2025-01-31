package com.example.sitirasama.ui.profil

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.R
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.service.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UbahpasswordFragment : Fragment() {

    private lateinit var edtOldPassword: EditText
    private lateinit var edtNewPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var toggleOldPassword: ImageButton
    private lateinit var toggleNewPassword: ImageButton
    private lateinit var toggleConfirmPassword: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_ubahpassword, container, false)

        edtOldPassword = root.findViewById(R.id.editTextOldPassword)
        edtNewPassword = root.findViewById(R.id.editTextNewPassword)
        edtConfirmPassword = root.findViewById(R.id.editTextConfirmPassword)
        btnSave = root.findViewById(R.id.btnSavePassword)
        toggleOldPassword = root.findViewById(R.id.toggleOldPassword)
        toggleNewPassword = root.findViewById(R.id.toggleNewPassword)
        toggleConfirmPassword = root.findViewById(R.id.toggleConfirmPassword)

        // 🔹 Event listener tombol toggle visibility password
        toggleOldPassword.setOnClickListener { togglePasswordVisibility(edtOldPassword, toggleOldPassword) }
        toggleNewPassword.setOnClickListener { togglePasswordVisibility(edtNewPassword, toggleNewPassword) }
        toggleConfirmPassword.setOnClickListener { togglePasswordVisibility(edtConfirmPassword, toggleConfirmPassword) }

        btnSave.setOnClickListener { confirmSave() }

        return root
    }

    private fun togglePasswordVisibility(editText: EditText, toggleButton: ImageButton) {
        val isPasswordVisible = editText.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
        editText.inputType = if (isPasswordVisible) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
        toggleButton.setImageResource(if (isPasswordVisible) android.R.drawable.ic_menu_view else android.R.drawable.ic_menu_close_clear_cancel)
        editText.setSelection(editText.text.length)
    }

    private fun confirmSave() {
        val oldPassword = edtOldPassword.text.toString().trim()
        val newPassword = edtNewPassword.text.toString().trim()
        val confirmPassword = edtConfirmPassword.text.toString().trim()

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(context, "Harap isi semua field!", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword.length < 6) {
            Toast.makeText(context, "Password baru harus minimal 6 karakter!", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword == oldPassword) {
            Toast.makeText(context, "Password baru tidak boleh sama dengan password lama!", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(context, "Konfirmasi password tidak cocok!", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Perubahan")
            .setMessage("Anda yakin ingin mengubah password Anda?")
            .setPositiveButton("Yes") { _, _ -> updatePassword(oldPassword, newPassword) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun updatePassword(oldPassword: String, newPassword: String) {
        val request = UserRequest(oldPassword = oldPassword, newPassword = newPassword)

        ApiClient.apiService.updatePassword(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when {
                    response.isSuccessful -> {
                        Toast.makeText(context, "Password berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                    response.code() == 400 || response.code() == 401 -> {
                        Toast.makeText(context, "Password Saat Ini salah!", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, "Gagal memperbarui password!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
