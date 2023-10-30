package com.example.nongglenonggle.domain.repository

import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.entity.ResumeContent

interface FirestoreSetRepository {
    suspend fun addNoticeData(noticeContent: NoticeContent)
    suspend fun addResumeData(resumeContent: ResumeContent, id1:String, id2:String)
}