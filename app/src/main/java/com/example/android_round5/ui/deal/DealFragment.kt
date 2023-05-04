package com.example.android_round5.ui.deal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_round5.databinding.FragmentDealBinding

class DealFragment : Fragment() {

    private var _binding: FragmentDealBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dealViewModel =
            ViewModelProvider(this).get(DealViewModel::class.java)

        _binding = FragmentDealBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDeal
        dealViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}