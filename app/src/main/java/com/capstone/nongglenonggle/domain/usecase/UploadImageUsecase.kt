package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.Model
import com.capstone.nongglenonggle.domain.repository.ImageRepository
import javax.inject.Inject

class UploadImageUsecase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend fun uploadImage(imageEntity: Model.ImageEntity,folderName:String) : Result<String>{
        return imageRepository.uploadImage(imageEntity,folderName)
    }
}