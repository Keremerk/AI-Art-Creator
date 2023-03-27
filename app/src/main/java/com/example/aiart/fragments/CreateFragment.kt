package com.example.aiart.fragments

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.aiart.R
import com.example.aiart.databinding.FragmentCreateBinding
import com.example.aiart.room.ImageDatabase
import com.example.aiart.room.ImageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class CreateFragment : Fragment() {

    private lateinit var arg1: String
    private lateinit var arg2: String
    private val viewModel by viewModels<DezgoViewModel>()
    private lateinit var binding: FragmentCreateBinding
    private lateinit var byteArray: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ImageName.visibility = INVISIBLE

        val db = ImageDatabase.getDatabase(requireContext())

        arg1 = arguments?.getString("arg1").toString()
        arg2 = arguments?.getString("arg2").toString()
        println(arg1)
        println(arg2)
        //   binding.textView21.text = arg1.toString()
        //   binding.textView20.text = arg2.toString()
        binding.backIV.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.downloadIV.setOnClickListener {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            val filename = "$arg2.png"
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            val outputStream: FileOutputStream
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = requireContext().contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "$arg2/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = (resolver.openOutputStream(imageUri!!) as FileOutputStream?)!!
            } else {
                val directory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!directory.exists()) {
                    directory.mkdirs()
                }
                val file = File(directory, filename)
                outputStream = FileOutputStream(file)
            }
            outputStream.write(byteArray)
            outputStream.close()

            // Create a dialog to show the download success message
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_download_complete)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            val okButton = dialog.findViewById<Button>(R.id.ok_button)
            okButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.generateImage("$arg1 , $arg2")
                .collect { response ->
                    response.enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            // Handle the response body here
                            if (response.isSuccessful) {
                                byteArray = response.body()?.bytes()!!
                                val bitmap =
                                    BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                                println(bitmap)
                                binding.CreateIV.setImageBitmap(bitmap)
                                val stream = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                                val byteArray = stream.toByteArray()
                                val imageEntity = ImageEntity(imageUrl = byteArray, arg1 = arg2)
                                binding.ImageName.text = arg2
                                CoroutineScope(Dispatchers.Main).launch {
                                    db.imageDao().insertImage(imageEntity)
                                    binding.ImageName.visibility = VISIBLE
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
        }
    }
}