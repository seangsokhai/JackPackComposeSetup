package com.example.jetpackcomposesetup.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class PageResult<T>(
    val results: List<T>,
    val info: InfoDto
)

@Parcelize
@JsonClass(generateAdapter = true)
data class InfoDto(
    val count : String?,
    val pages : String?,
    val next : String?,
    val prev : String?,
) : Parcelable