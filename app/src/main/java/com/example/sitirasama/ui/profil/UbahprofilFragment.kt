package com.example.sitirasama.ui.profil

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.sitirasama.MainActivity
import com.example.sitirasama.R
import com.example.sitirasama.model.UserRequest
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.util.SessionManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UbahprofilFragment : Fragment() {

    private lateinit var edtEmail: EditText
    private lateinit var edtUsername: EditText
    private lateinit var btnSave: Button
    private lateinit var sessionManager: SessionManager
    private var currentEmail: String? = null
    private var currentUsername: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_ubahprofil, container, false)

        edtEmail = root.findViewById(R.id.editTextEmail)
        edtUsername = root.findViewById(R.id.editTextUsername)
        btnSave = root.findViewById(R.id.btnSaveProfil)
        sessionManager = SessionManager(requireContext())

        // 🔹 Ambil data profil pengguna saat fragment dibuka
        fetchProfil()

        btnSave.setOnClickListener {
            confirmSave()
        }

        return root
    }

    private fun fetchProfil() {
        ApiClient.apiService.getProfil().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    currentEmail = user?.email
                    currentUsername = user?.username

                    // 🔹 Set nilai EditText dengan data dari API
                    edtUsername.setText(currentUsername ?: "")
                    edtEmail.setText(currentEmail ?: "")

                } else {
                    Log.e("API_DEBUG", "Gagal mendapatkan profil: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Gagal mengambil data profil!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("API_DEBUG", "Kesalahan jaringan: ${t.message}")
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmSave() {
        val email = edtEmail.text.toString().trim()

        // 🔹 Pastikan email selalu dikirim ke API
        if (email.isEmpty()) {
            Toast.makeText(context, "Mohon isi email!", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Perubahan")
            .setMessage("Anda yakin dengan perubahan profil Anda?")
            .setPositiveButton("Yes") { _, _ -> updateProfil(email) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun updateProfil(email: String) {
        val request = UserRequest(email = email, username = currentUsername ?: "")

        Log.d("API_DEBUG", "Mengirim request update profil: email=$email")

        ApiClient.apiService.updateProfil(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("API_DEBUG", "Profil berhasil diperbarui!")

                    Toast.makeText(context, "Profil berhasil diperbarui, silakan login kembali!", Toast.LENGTH_SHORT).show()

                    // 🔹 Logout otomatis setelah profil berhasil diperbarui
                    logoutAndRedirect()

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_DEBUG", "Gagal update profil: ${response.code()} - $errorBody")

                    Toast.makeText(context, "Gagal memperbarui profil!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("API_DEBUG", "Kesalahan jaringan: ${t.message}")
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun logoutAndRedirect() {
        sessionManager.clearSession()  // 🔹 Hapus token dan session

        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()

        Log.d("SESSION_DEBUG", "User terlogout setelah update profil")
    }
}
