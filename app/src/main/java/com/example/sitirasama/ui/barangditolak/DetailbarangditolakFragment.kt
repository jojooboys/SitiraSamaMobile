package com.example.sitirasama.ui.barangditolak

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.R
import com.example.sitirasama.model.UserResponse
import com.example.sitirasama.service.ApiClient
import com.example.sitirasama.util.SessionManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class DetailbarangditolakFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private var barangDitolak: UserResponse? = null
    private lateinit var btnDelete: Button
    private lateinit var btnUpdateAlasan: Button
    private lateinit var txtAlasan: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_detailbarangditolak, container, false)
        sessionManager = SessionManager(requireContext())

        // Log debugging untuk melihat arguments yang diterima
        Log.d("Detailbarangditolak", "Menerima arguments: ${arguments?.keySet()}")

        barangDitolak = arguments?.getSerializable("barangDitolak") as? UserResponse
        Log.d("Detailbarangditolak", "Data yang diterima: $barangDitolak")

        // Jika data tidak diterima, navigasi kembali
        if (barangDitolak == null) {
            Log.e("Detailbarangditolak", "Barang ditolak null setelah parsing.")
            Toast.makeText(context, "Data tidak ditemukan!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return root
        }

        val txtBarang: TextView = root.findViewById(R.id.textBarang)
        val txtDeskripsi: TextView = root.findViewById(R.id.textDeskripsi)
        txtAlasan = root.findViewById(R.id.textAlasan)

        btnDelete = root.findViewById(R.id.btnDeleteBarangDitolak)
        btnUpdateAlasan = root.findViewById(R.id.btnUpdateAlasan)

        // Setel tombol ke GONE sebagai default
        btnDelete.visibility = View.GONE
        btnUpdateAlasan.visibility = View.GONE

        // Tampilkan data barang ditolak
        txtBarang.text = barangDitolak?.barang ?: "Barang tidak diketahui"
        txtDeskripsi.text = barangDitolak?.deskripsi ?: "Deskripsi tidak diketahui"
        txtAlasan.text = barangDitolak?.alasan ?: "Alasan belum ditambahkan"

        // Cek status pengguna untuk menentukan visibilitas tombol
        getUserProfile { status ->
            if (status == "satpam") {
                btnDelete.visibility = View.VISIBLE
                btnUpdateAlasan.visibility = View.VISIBLE

                btnDelete.setOnClickListener { confirmDelete() }
                btnUpdateAlasan.setOnClickListener { navigateToAlasanFragment() }
            } else {
                Log.d("Detailbarangditolak", "Tombol disembunyikan untuk akun mahasiswa.")
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dengarkan hasil update dari AlasanFragment
        setFragmentResultListener("updateAlasan") { _, bundle ->
            val newAlasan = bundle.getString("newAlasan")

            barangDitolak?.alasan = newAlasan
            txtAlasan.text = newAlasan ?: "Alasan belum ditambahkan"
        }
    }

    private fun confirmDelete() {
        AlertDialog.Builder(requireContext())
            .setMessage("Apakah Anda yakin ingin menghapus barang ditolak ini dari database?")
            .setPositiveButton("Yes") { _, _ -> deleteBarangDitolak() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteBarangDitolak() {
        barangDitolak?.id?.let { id ->
            ApiClient.apiService.deleteBarangDitolak(id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Barang ditolak berhasil dihapus", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(context, "Gagal menghapus barang ditolak", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun getUserProfile(callback: (String?) -> Unit) {
        ApiClient.apiService.getProfil().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    callback(user?.status)
                } else {
                    callback(null)
                    Toast.makeText(context, "Gagal mengambil data profil!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                callback(null)
                Toast.makeText(context, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToAlasanFragment() {
        val bundle = Bundle().apply {
            putSerializable("barangDitolak", barangDitolak)
        }
        findNavController().navigate(R.id.action_detailBarangditolakFragment_to_alasanFragment, bundle)
    }
}
