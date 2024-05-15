package com.example.jetpackcomposesetup.common

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.util.concurrent.TimeUnit


enum class CacheStrategy {
    LOCAL_FIRST, LOCAL_IF_ERROR, LOCAL_TTL_ELSE_REMOTE
}

const val REMOVE_PREFIX = "x-"
const val KEY_APP_VERSION_CODE = "appVersionCode"

val TTL_TILL_APP_FRESH_RESTART = TimeUnit.DAYS.toMillis(365)

@Parcelize
private data class CacheList<T : Parcelable>(
    val list: List<T>
) : Parcelable

class CachePagingSource<T : Parcelable>(
    private val cacheKey: String, private val block: suspend (page: Int) -> List<T>
) : SimplePagingSource<T>(block) {

    private val kv = MMKV.defaultMMKV()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val cacheClassType = CacheList<T>(listOf()).javaClass
            val page = params.key ?: 0
            val pageKey = "$cacheKey-$page"
            var remoteSaved = false
            val result: CacheList<T> = kv.decodeParcelable(pageKey, cacheClassType)
                ?: CacheList(block(page)).also {
                    remoteSaved = kv.encode(pageKey, it)
                }
            if (!remoteSaved) {
                launch(Dispatchers.IO) {
                    try {
                        block(page).takeIf { it.isNotEmpty() }?.let {
                            val items = CacheList(it)
                            kv.encode(pageKey, items)
                            if (items != result) {
                                // It means remote page & local page are out of sync, so refresh
                                kv.removeEntriesWithPrefix(cacheKey)
                                invalidate()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            LoadResult.Page(
                data = result.list,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (result.list.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}

inline fun <reified T : Parcelable> cacheFlow(
    key: String,
    strategy: CacheStrategy = CacheStrategy.LOCAL_FIRST,
    timeToLive: Long = TTL_TILL_APP_FRESH_RESTART,
    crossinline block: suspend () -> T
): Flow<T> = flow {
    val kv = MMKV.defaultMMKV()
    when (strategy) {
        CacheStrategy.LOCAL_FIRST -> {
            // read local, then read & write remote to local
            var remoteDataCached = false
            emit(kv.decodeParcelable(key, T::class.java) ?: block().also {
                if (kv.encode(key, it)) {
                    emit(it)
                    remoteDataCached = true
                }
            })
            if (!remoteDataCached) {
                // since we probably have the cached data, so ignore remote error (such as connection error,...etc)
                // by the time the remote data available again, the cached data is also refreshed
                try {
                    emit(block().also {
                        kv.encode(key, it)
                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        CacheStrategy.LOCAL_IF_ERROR -> {
            // read remote, if error, read local
            try {
                emit(block().also {
                    kv.encode(key, it)
                })
            } catch (e: Exception) {
                emit(kv.decodeParcelable(key, T::class.java) ?: throw e)
            }
        }

        CacheStrategy.LOCAL_TTL_ELSE_REMOTE -> {
            // if expired or non-existent, then read & write remote to local
            // else read local
            val dataKey = when (timeToLive) {
                TTL_TILL_APP_FRESH_RESTART -> "$REMOVE_PREFIX${key}" // entry to remove on app fresh restart
                else -> key
            }
            val ttlKey = when (timeToLive) {
                TTL_TILL_APP_FRESH_RESTART -> "$REMOVE_PREFIX${key}-ttl"
                else -> "${key}-ttl"
            }
            val ttl = kv.decodeLong(ttlKey)
            val cachedData = kv.decodeParcelable(dataKey, T::class.java)
            if (cachedData == null || System.currentTimeMillis() > ttl) {
                println(">>> cache expired or empty. loading remote data... ttl: $timeToLive")
                emit(block().also {
                    kv.encode(ttlKey, System.currentTimeMillis() + timeToLive)
                    kv.encode(dataKey, it)
                })
            } else {
                println(">>> cache && ttl still valid. loading cached data...")
                emit(cachedData)
            }
        }
    }
}.distinctUntilChanged()
    .flowOn(Dispatchers.IO)
    .catch {
        println(">>> error: ${it.message}")
        it.printStackTrace()
    }

fun <T : Parcelable> listCacheFlow(
    key: String,
    strategy: CacheStrategy = CacheStrategy.LOCAL_FIRST,
    timeToLive: Long = TTL_TILL_APP_FRESH_RESTART,
    block: suspend () -> List<T>
): Flow<List<T>> = cacheFlow(key = key, strategy = strategy, timeToLive = timeToLive) {
    CacheList(block())
}.map {
    it.list
}

private fun getAppVersionCode(): Int = 19

private fun clearAllOnAppUpgrade() {
    val kv = MMKV.defaultMMKV()
    val appVersion = getAppVersionCode()
    val previousVersion = kv.decodeInt(KEY_APP_VERSION_CODE)
    if (appVersion != previousVersion) {
        kv.clearAll()
        kv.encode(KEY_APP_VERSION_CODE, appVersion)
        println(">>> prev version: $previousVersion, current version: $appVersion")
        println(">>> all entries cleared on app upgrade")
    }
}

fun initMMKV(context: Context, clearOnAppUpgrade: Boolean = true) {
    MMKV.initialize(context)

    // clear all entries if app version name is different from the previously saved one
    if (clearOnAppUpgrade) {
        clearAllOnAppUpgrade()
    }

    // remove marked expired entries
    removeExpiredEntries()
}

fun clearMMKVCache() {
    val kv = MMKV.defaultMMKV()
    val appVersion = kv.decodeInt(KEY_APP_VERSION_CODE)
    kv.clearAll()
    kv.encode(KEY_APP_VERSION_CODE, appVersion)
    println(">>> all entries cleared")
}

private fun MMKV.removeEntriesWithPrefix(prefix: String): Int {
    val kv = this
    val keys = kv.allKeys()?.filter { it.startsWith(prefix) }?.toTypedArray()
    val numKeys = keys?.size ?: 0
    if (numKeys > 0) {
        kv.removeValuesForKeys(keys)
        println(">>> removed $numKeys entries with prefix: $prefix")
    }
    return numKeys
}

private fun removeExpiredEntries() {
    val kv = MMKV.defaultMMKV()
    kv.removeEntriesWithPrefix(REMOVE_PREFIX)
}
