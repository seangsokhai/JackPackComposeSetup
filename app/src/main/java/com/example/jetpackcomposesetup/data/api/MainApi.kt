package com.example.jetpackcomposesetup.data.api


import com.example.jetpackcomposesetup.data.model.CharacterDto
import com.example.jetpackcomposesetup.data.model.LoginResultDto
import com.example.jetpackcomposesetup.data.model.LoginWithFirebaseDto
import com.example.jetpackcomposesetup.data.model.PageResult
import com.example.jetpackcomposesetup.data.model.ValidateUserDto
import retrofit2.Response
import retrofit2.http.*

interface MainApi {
	@GET("character/{ids}")
	suspend fun getCharacters(
		@Path("ids") placement: String = "1,2,3"
	): List<CharacterDto>

	@GET("character")
	suspend fun getAllCharacters(): PageResult<CharacterDto>

}
