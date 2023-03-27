package com.example.aiart.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aiart.R
import com.example.aiart.databinding.FragmentInappBinding

class InappFragment : Fragment() {
    private lateinit var binding: FragmentInappBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInappBinding.inflate(inflater, container, false)

        binding.view3Days.setOnClickListener {
            selectPlan(binding.view3Days, binding.viewYearly)
            // Handle click for view3Days
            binding.view3Days.setBackgroundResource(R.drawable.inappp_background_black)
            binding.viewYearly.setBackgroundResource(R.drawable.inapp_background)
        }

        binding.viewYearly.setOnClickListener {
            selectPlan(binding.viewYearly, binding.view3Days)
            // Handle click for viewYearly
            binding.viewYearly.setBackgroundResource(R.drawable.inappp_background_black)
            binding.view3Days.setBackgroundResource(R.drawable.inapp_background)
        }

        binding.StartTV.setOnClickListener {
            if (binding.view3Days.isSelected || binding.viewYearly.isSelected) {
                findNavController().navigate(InappFragmentDirections.actionInappFragmentToHomeFragment())
            } else {
                Toast.makeText(
                    requireContext(),
                    "You have to choose one of the plans!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

    private fun selectPlan(selectedView: View, unselectedView: View) {
        selectedView.isSelected = true
        unselectedView.isSelected = false
    }
}
