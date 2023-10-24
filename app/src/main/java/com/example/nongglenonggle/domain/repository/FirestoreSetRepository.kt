package com.example.nongglenonggle.domain.repository

import com.example.nongglenonggle.domain.entity.NoticeContent

interface FirestoreSetRepository {
    suspend fun addNoticeData(noticeContent: NoticeContent)
}