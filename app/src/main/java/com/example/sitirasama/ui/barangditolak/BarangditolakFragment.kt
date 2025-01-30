package com.example.sitirasama.ui.barangditolak

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarangditolakFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BarangditolakAdapter
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_barangditolak, container, false)

        recyclerView = root.findViewById(R.id.recyclerViewBarangDitolak)
        recyclerView.layoutManager = LinearLayoutManager(context)

        token = "Bearer " + activity?.intent?.getStringExtra("TOKEN")

        fetchBarangDitolak()

        return root
    }

    private fun fetchBarangDitolak() {
        ApiClient.apiService.getBarangDitolak().enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                if (response.isSuccessful) {
                    val barangDitolakList = response.body()
                    Log.d("API_DEBUG", "Barang Ditolak: ${barangDitolakList?.size}")
                } else {
                    Log.e("API_DEBUG", "Gagal mengambil barang ditolak: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Log.e("API_DEBUG", "Terjadi kesalahan: ${t.message}")
            }
        })
    }

}
