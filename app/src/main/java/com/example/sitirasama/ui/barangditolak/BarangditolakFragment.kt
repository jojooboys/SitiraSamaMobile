package com.example.sitirasama.ui.barangditolak

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarangditolakFragment : Fragment() {

    private lateinit var recyclerViewBarangditolak: RecyclerView
    private lateinit var adapter: BarangditolakAdapter
    private lateinit var sessionManager: SessionManager

    private var barangDitolakList: List<UserResponse> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_barangditolak, container, false)
        sessionManager = SessionManager(requireContext())

        recyclerViewBarangditolak = root.findViewById(R.id.recyclerViewBarangditolak)
        recyclerViewBarangditolak.layoutManager = LinearLayoutManager(context)

        adapter = BarangditolakAdapter(emptyList()) { item -> showDetailFragment(item) }
        recyclerViewBarangditolak.adapter = adapter

        fetchBarangDitolak()

        return root
    }

    private fun fetchBarangDitolak() {
        ApiClient.apiService.getBarangDitolak().enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                if (response.isSuccessful) {
                    barangDitolakList = response.body() ?: emptyList()
                    adapter.updateData(barangDitolakList)
                } else {
                    Toast.makeText(context, "Gagal mengambil data barang ditolak", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDetailFragment(item: UserResponse) {
        val bundle = Bundle().apply {
            putSerializable("barangDitolak", item)
        }
        findNavController().navigate(R.id.action_navigation_barangditolak_to_detailBarangditolakFragment, bundle)
    }
}
