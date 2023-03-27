package com.example.aiart.room

import androidx.room.*
@Dao
interface ImageDao {
    @Insert
    suspend fun insertImage(image: ImageEntity)

    @Query("SELECT * FROM images")
    suspend fun getAllImages(): List<ImageEntity>
}
