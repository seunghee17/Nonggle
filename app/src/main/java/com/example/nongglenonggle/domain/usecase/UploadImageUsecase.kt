package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.repository.ImageRepository
import javax.inject.Inject

class UploadImageUsecase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend fun uploadImage(imageEntity: Model.ImageEntity) : Result<String>{
        return imageRepository.uploadImage(imageEntity)
    }
}