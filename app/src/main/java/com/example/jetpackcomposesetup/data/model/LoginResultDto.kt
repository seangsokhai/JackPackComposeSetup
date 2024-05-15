package com.example.jetpackcomposesetup.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class LoginResultDto(
    val user: String,
    val sessionToken: String
) : Parcelable

