package com.example.aiart.fragments

import com.example.aiart.recyclerview.ImageListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aiart.databinding.FragmentHistoryBinding
import com.example.aiart.room.ImageDatabase
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: ImageListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        binding.BackImageView.setOnClickListener {
            findNavController().navigateUp()
        }
        val adapter = ImageListAdapter(emptyList())
        binding.RecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.RecyclerView.adapter = adapter


        // Get an instance of your ImageDao interface
        val imageDao = ImageDatabase.getDatabase(requireContext()).imageDao()

        // Retrieve data from Room using the ImageDao and update the adapter
        lifecycleScope.launch {
            val images = imageDao.getAllImages()
            adapter.updateData(images)
        }

        return binding.root
    }


}