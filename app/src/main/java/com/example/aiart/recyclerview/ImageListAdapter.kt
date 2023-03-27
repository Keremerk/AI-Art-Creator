package com.example.aiart.recyclerview

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aiart.databinding.AppRecyclerRowBinding
import com.example.aiart.room.ImageEntity

class ImageListAdapter(private var data: List<ImageEntity>) :
    RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    class ViewHolder(val binding: AppRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AppRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = data[position].imageUrl
        val bitmap = BitmapFactory.decodeByteArray(imageUrl, 0, imageUrl.size)
        holder.binding.imageView.setImageBitmap(bitmap)
        holder.binding.textView.text = data[position].arg1
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<ImageEntity>) {
        data = newData
        notifyDataSetChanged()
    }
}
