package com.example.sitirasama.ui.profil

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.MainActivity
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.util.SessionManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var textEmail: TextView
    private lateinit var textUsername: TextView
    private lateinit var textStatus: TextView
    private lateinit var btnUbahEmail: Button
    private lateinit var btnUbahPassword: Button
    private lateinit var btnHapusAkun: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_profil, container, false)
        sessionManager = SessionManager(requireContext())

        textEmail = root.findViewById(R.id.textEmail)
        textUsername = root.findViewById(R.id.textUsername)
        textStatus = root.findViewById(R.id.textStatus)
        val logoutButton = root.findViewById<Button>(R.id.logoutButton)
        btnUbahEmail = root.findViewById(R.id.btnUbahEmail)
        btnUbahPassword = root.findViewById(R.id.btnUbahPassword)
        btnHapusAkun = root.findViewById(R.id.btnHapusAkun)

        getUserProfile()

        logoutButton.setOnClickListener {
            sessionManager.clearSession()
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }

        btnUbahEmail.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment_to_ubahProfilFragment)
        }

        btnUbahPassword.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment_to_ubahPasswordFragment)
        }

        btnHapusAkun.setOnClickListener {
            confirmDeleteAccount()
        }

        return root
    }

    private fun getUserProfile() {
        ApiClient.apiService.getProfil().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    textEmail.text = user?.email ?: "Tidak tersedia"
                    textUsername.text = user?.username ?: "Tidak tersedia"
                    textStatus.text = user?.status ?: "Tidak tersedia"
                } else {
                    Toast.makeText(context, "Gagal mengambil data profil!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(context, "Terjadi kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmDeleteAccount() {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Penghapusan")
            .setMessage("Apakah Anda yakin akan menghapus akun ini beserta seluruh datanya?")
            .setPositiveButton("Yes") { _, _ -> deleteAccount() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteAccount() {
        val email = textEmail.text.toString()

        ApiClient.apiService.deleteUser(email).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Akun Anda berhasil dihapus", Toast.LENGTH_SHORT).show()
                    sessionManager.clearSession()
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                } else {
                    Toast.makeText(context, "Gagal menghapus akun!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Terjadi kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
