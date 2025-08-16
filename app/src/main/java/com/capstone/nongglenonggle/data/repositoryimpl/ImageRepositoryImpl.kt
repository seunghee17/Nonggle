package com.capstone.nongglenonggle.data.repositoryimpl

import com.capstone.nongglenonggle.domain.entity.Model
import com.capstone.nongglenonggle.domain.repository.ImageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth : FirebaseAuth
) : ImageRepository{
    override suspend fun uploadImage(imageEntity: Model.ImageEntity,folderName:String): Result<String> {
        val user = firebaseAuth.currentUser
        val uid = user?.uid
        val imageFileName = "Image_" + uid + "_png"
        val storageRef = firebaseStorage.reference.child(folderName).child(imageFileName)

        return try{
            storageRef.putFile(imageEntity.uri).await()
            val imageurl = storageRef.downloadUrl.await()
            //업로드 성공시 이미지 url 반환하도록 구현
            Result.success(imageurl.toString())
        } catch (e:Exception){
            Result.failure(e)
        }
    }
}