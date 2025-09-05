package com.capstone.nongglenonggle.data.model.worker

import android.net.Uri
import java.util.Date

data class ResumeStep1State (
    val isLoading: Boolean = false,
    val userName: String = "",
    val selectedGender: String = "",
    val haveCertification: Boolean? = null,
    val imageProfileUri: Uri? = null,
    val birthDate: Date? = null,
    val birthDatePresnet: String = "생년월일을 선택해주세요.",
    val userCertificateType: String = "",
    val userCertificationList: MutableList<String> = mutableListOf()
)