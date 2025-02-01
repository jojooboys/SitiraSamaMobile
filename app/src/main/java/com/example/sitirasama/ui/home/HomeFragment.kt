package com.example.sitirasama.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.databinding.FragmentHomeBinding
import com.example.sitirasama.R
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fetchProfil()

        binding.formButton.setOnClickListener {
            Toast.makeText(requireContext(), "Silahkan mengisi formulir pengajuan!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_navigation_home_to_formPengajuanFragment)
        }

        return root
    }

    private fun fetchProfil() {
        ApiClient.apiService.getProfil().enqueue(object : Callback<UserResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("API_DEBUG", "Response sukses: ${user?.username}")

                    binding.textWelcome.text = "Selamat datang di Sitira-Sama, ${user?.status} - ${user?.username}"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}