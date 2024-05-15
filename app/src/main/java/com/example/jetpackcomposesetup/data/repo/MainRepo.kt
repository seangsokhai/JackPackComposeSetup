package com.example.jetpackcomposesetup.data.repo



import com.example.jetpackcomposesetup.AppSharedPrefs
import com.example.jetpackcomposesetup.common.listCacheFlow
import com.example.jetpackcomposesetup.data.api.MainApi
import com.example.jetpackcomposesetup.data.model.CharacterDto
import com.example.jetpackcomposesetup.data.model.LoginResultDto
import com.example.jetpackcomposesetup.data.model.LoginWithFirebaseDto
import com.example.jetpackcomposesetup.data.model.ValidateUserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import retrofit2.Response
import javax.inject.Inject

class MainRepo @Inject constructor(
    private val api: MainApi, private val prefs: AppSharedPrefs
) : CoroutineScope by MainScope() {
    companion object {
        private const val PageSize = 10
        private const val MaxPageSize = 100
    }
    fun getCharacters(): Flow<List<CharacterDto>> =
        listCacheFlow("getCharacters") {
            api.getCharacters()
        }
    fun getAllCharacters(): Flow<List<CharacterDto>> =
        listCacheFlow("getCharacters") {
            api.getAllCharacters().results.take(20)
        }
}

