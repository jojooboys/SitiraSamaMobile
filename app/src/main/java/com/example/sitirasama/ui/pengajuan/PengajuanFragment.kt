package com.example.sitirasama.ui.pengajuan

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

class PengajuanFragment : Fragment() {

    private lateinit var recyclerViewPengajuan: RecyclerView
    private lateinit var adapter: PengajuanAdapter
    private lateinit var sessionManager: SessionManager

    private var pengajuanList: List<UserResponse> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
                    pengajuanList = response.body() ?: emptyList()
                    adapter = PengajuanAdapter(pengajuanList) { item -> showDetailFragment(item) }
                    recyclerViewPengajuan.adapter = adapter
                } else {
                    Toast.makeText(context, "Gagal mengambil pengajuan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDetailFragment(item: UserResponse) {
        val bundle = Bundle().apply {
            putSerializable("pengajuan", item) // ✅ Perbaikan: Gunakan Serializable
        }
        findNavController().navigate(R.id.action_navigation_pengajuan_to_detailpengajuanFragment, bundle)
    }

}
