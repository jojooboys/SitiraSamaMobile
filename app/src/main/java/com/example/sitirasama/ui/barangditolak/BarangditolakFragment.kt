package com.example.sitirasama.ui.barangditolak

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

    private lateinit var checkId: CheckBox
    private lateinit var checkUsername: CheckBox
    private lateinit var checkBarang: CheckBox
    private lateinit var checkDeskripsi: CheckBox
    private lateinit var searchId: EditText
    private lateinit var searchUsername: EditText
    private lateinit var searchBarang: EditText
    private lateinit var searchDeskripsi: EditText

    private var barangDitolakList: List<UserResponse> = emptyList()
    private var filteredList: List<UserResponse> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_barangditolak, container, false)
        sessionManager = SessionManager(requireContext())

        recyclerViewBarangditolak = root.findViewById(R.id.recyclerViewBarangditolak)
        recyclerViewBarangditolak.layoutManager = LinearLayoutManager(context)

        checkId = root.findViewById(R.id.checkId)
        checkUsername = root.findViewById(R.id.checkUsername)
        checkBarang = root.findViewById(R.id.checkBarang)
        checkDeskripsi = root.findViewById(R.id.checkDeskripsi)

        searchId = root.findViewById(R.id.searchId)
        searchUsername = root.findViewById(R.id.searchUsername)
        searchBarang = root.findViewById(R.id.searchBarang)
        searchDeskripsi = root.findViewById(R.id.searchDeskripsi)

        adapter = BarangditolakAdapter(emptyList()) { item -> showDetailFragment(item) }
        recyclerViewBarangditolak.adapter = adapter

        setupCheckboxListeners()
        setupSearchListeners()
        fetchBarangDitolak()

        return root
    }

    private fun setupCheckboxListeners() {
        val checkboxes = listOf(
            checkId to searchId,
            checkUsername to searchUsername,
            checkBarang to searchBarang,
            checkDeskripsi to searchDeskripsi
        )

        checkboxes.forEach { (checkbox, searchBar) ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                searchBar.visibility = if (isChecked) View.VISIBLE else View.GONE
                filterBarangDitolak()
            }
        }
    }

    private fun setupSearchListeners() {
        val searchFields = listOf(searchId, searchUsername, searchBarang, searchDeskripsi)
        searchFields.forEach { editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) { filterBarangDitolak() }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun fetchBarangDitolak() {
        ApiClient.apiService.getBarangDitolak().enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                if (response.isSuccessful) {
                    barangDitolakList = response.body() ?: emptyList()
                    filteredList = barangDitolakList
                    adapter.updateData(filteredList)
                } else {
                    Toast.makeText(context, "Gagal mengambil data barang ditolak", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterBarangDitolak() {
        val idFilter = searchId.text.toString().trim()
        val usernameFilter = searchUsername.text.toString().trim()
        val barangFilter = searchBarang.text.toString().trim()
        val deskripsiFilter = searchDeskripsi.text.toString().trim()

        filteredList = barangDitolakList.filter { item ->
            val idMatches = if (checkId.isChecked && idFilter.isNotEmpty()) item.id.toString().contains(idFilter, true) else true
            val usernameMatches = if (checkUsername.isChecked && usernameFilter.isNotEmpty()) item.username?.contains(usernameFilter, true) == true else true
            val barangMatches = if (checkBarang.isChecked && barangFilter.isNotEmpty()) item.barang?.contains(barangFilter, true) == true else true
            val deskripsiMatches = if (checkDeskripsi.isChecked && deskripsiFilter.isNotEmpty()) item.deskripsi?.contains(deskripsiFilter, true) == true else true

            idMatches && usernameMatches && barangMatches && deskripsiMatches
        }

        adapter.updateData(filteredList)
    }

    private fun showDetailFragment(item: UserResponse) {
        val bundle = Bundle().apply {
            putSerializable("barangDitolak", item)
        }
        findNavController().navigate(R.id.action_navigation_barangditolak_to_detailBarangditolakFragment, bundle)
    }
}
