package com.example.sitirasama.ui.penitipan

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

class PenitipanFragment : Fragment() {

    private lateinit var recyclerViewPenitipan: RecyclerView
    private lateinit var adapter: PenitipanAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_penitipan, container, false)
        sessionManager = SessionManager(requireContext())

        recyclerViewPenitipan = root.findViewById(R.id.recyclerViewPenitipan)
        recyclerViewPenitipan.layoutManager = LinearLayoutManager(context)

        fetchPenitipan()

        return root
    }

    private fun fetchPenitipan() {
        ApiClient.apiService.getBarang().enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                if (response.isSuccessful) {
                    val barangList = response.body()
                    Log.d("API_DEBUG", "Barang: ${barangList?.size}")
                } else {
                    Log.e("API_DEBUG", "Gagal mengambil barang: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Log.e("API_DEBUG", "Terjadi kesalahan: ${t.message}")
            }
        })
    }

}
