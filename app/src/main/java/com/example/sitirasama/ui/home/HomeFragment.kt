package com.example.sitirasama.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sitirasama.databinding.FragmentHomeBinding
import com.example.sitirasama.R


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Gunakan binding untuk menghubungkan layout
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi ViewModel
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Menghubungkan data dari ViewModel ke TextView
        homeViewModel.text.observe(viewLifecycleOwner) { text ->
            binding.textHome.text = text
        }

        // Menambahkan event klik pada tombol
        binding.formButton.setOnClickListener {
            Toast.makeText(requireContext(), "Tombol ditekan!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_navigation_home_to_formPengajuanFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
