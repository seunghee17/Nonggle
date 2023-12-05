package com.capstone.nongglenonggle.domain.repository

import com.capstone.nongglenonggle.domain.entity.Model

interface ImageRepository {
    suspend fun uploadImage(imageEntity: Model.ImageEntity,folderName:String) : Result<String>
}