package com.example.sitirasama.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.MainActivity
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.util.SessionManager
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_profil, container, false)
        sessionManager = SessionManager(requireContext())

        textEmail = root.findViewById(R.id.textEmail)
        textUsername = root.findViewById(R.id.textUsername)
        textStatus = root.findViewById(R.id.textStatus)
        val logoutButton = root.findViewById<Button>(R.id.logoutButton)
        btnUbahEmail = root.findViewById(R.id.btnUbahEmail)
        btnUbahPassword = root.findViewById(R.id.btnUbahPassword)

        // 🔹 Panggil API untuk mendapatkan data pengguna
        getUserProfile()

        logoutButton.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        btnUbahEmail.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment_to_ubahProfilFragment)
        }

        btnUbahPassword.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment_to_ubahPasswordFragment)
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
                Toast.makeText(context, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
