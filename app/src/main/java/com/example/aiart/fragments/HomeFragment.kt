package com.example.aiart.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aiart.R
import com.example.aiart.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var arg1: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            historyIcon.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHistoryFragment())
            }

            SettingsIcon.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
            }

            CreateTV.setOnClickListener {
                val prompt = PromptET.text.toString()

                if (arg1.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please choose a category",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (prompt.isBlank()) {
                    Toast.makeText(
                        requireContext(),
                        "Please enter a prompt",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToCreateFragment(
                        arg1 = arg1,
                        arg2 = prompt
                    )
                )
            }

            // Set click listeners for the category options
            val categoryOptions = mapOf(
                SurrealistTV to " Surrealist",
                SteamPunkTV to " Steampunk",
                RickAndMortyTV to " Rick and Morty",
                RenPaintingTV to " Renaissance Painting",
                PortraitTV to " Portrait Photo"
            )

            categoryOptions.keys.forEach { tv ->
                tv.setOnClickListener {
                    categoryOptions.forEach { (key, value) ->
                        key.setBackgroundResource(if (tv == key) R.drawable.black_btn_home_using else R.drawable.btn_home_use)
                        key.text = if (tv == key) "Using" else "Use"
                        key.setTextColor(ContextCompat.getColor(requireContext(), if (tv == key) R.color.white else R.color.black))
                    }
                    arg1 = categoryOptions[tv] ?: ""
                }
            }
        }
    }
}