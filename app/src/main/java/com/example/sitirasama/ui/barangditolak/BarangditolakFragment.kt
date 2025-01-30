package com.example.sitirasama.ui.barangditolak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sitirasama.databinding.FragmentBarangditolakBinding

class BarangditolakFragment : Fragment() {

    private var _binding: FragmentBarangditolakBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val barangditolakViewModel =
            ViewModelProvider(this).get(BarangditolakViewModel::class.java)

        _binding = FragmentBarangditolakBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBarangditolak
        barangditolakViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}