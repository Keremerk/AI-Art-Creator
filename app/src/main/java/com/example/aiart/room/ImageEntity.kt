package com.example.aiart.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val arg1 :String,
    val imageUrl : ByteArray
)