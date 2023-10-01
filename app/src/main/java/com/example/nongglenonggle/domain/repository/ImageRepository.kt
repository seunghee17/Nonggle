package com.example.nongglenonggle.domain.repository

import com.example.nongglenonggle.domain.entity.Model

interface ImageRepository {
    suspend fun uploadImage(imageEntity: Model.ImageEntity) : Result<String>
}