package com.example.jetpackcomposesetup.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class CharacterDto(
    val id: String?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: OriginDto?,
    val location: LocationDto?,
    val image : String?,
    val episode : List<String>?,
    val url : String?,
    val create : String?,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class OriginDto(
    val name : String?,
    val url : String?,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class LocationDto(
    val name: String?,
    val url: String?,
) : Parcelable