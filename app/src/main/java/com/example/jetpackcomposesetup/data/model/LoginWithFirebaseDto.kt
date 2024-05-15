package com.example.jetpackcomposesetup.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class LoginWithFirebaseDto(
    val firebaseAuthToken: String,
    val firebaseAuthUID: String,
    val patientId: String,
    val phoneNumber: String,
) : Parcelable

