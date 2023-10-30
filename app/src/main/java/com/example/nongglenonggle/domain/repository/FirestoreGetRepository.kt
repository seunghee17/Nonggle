package com.example.nongglenonggle.domain.repository

import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.WorkerHomeData

interface FirestoreGetRepository {
    suspend fun getWorkerInfo() : WorkerHomeData?
}