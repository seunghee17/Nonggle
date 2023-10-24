package com.example.nongglenonggle.domain.entity

import android.net.Uri

class Model {
    data class ImageEntity(val uri: Uri)

    data class ResumeSummary(
        val title : String,
        val date : String,
        val total : String,
        val description : String
    )

    //data class Location(val value: String? = null)
}