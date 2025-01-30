package com.example.sitirasama.ui.pengajuan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengajuanFragment : Fragment() {

    private lateinit var recyclerViewPengajuan: RecyclerView
    private lateinit var adapter: PengajuanAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_pengajuan, container, false)
        sessionManager = SessionManager(requireContext())

        recyclerViewPengajuan = root.findViewById(R.id.recyclerViewPengajuan)
        recyclerViewPengajuan.layoutManager = LinearLayoutManager(context)

        fetchPengajuan()

        return root
    }

    private fun fetchPengajuan() {
        ApiClient.apiService.getPengajuan().enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                if (response.isSuccessful) {
                    val pengajuanList = response.body()
                    Log.d("API_DEBUG", "Pengajuan: ${pengajuanList?.size}")
                } else {
                    Log.e("API_DEBUG", "Gagal mengambil pengajuan: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Log.e("API_DEBUG", "Terjadi kesalahan: ${t.message}")
            }
        })
    }

}