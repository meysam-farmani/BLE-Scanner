package example.project.network.api

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import example.project.network.NetworkDataSource
import example.project.network.di.KtorModule
import example.project.network.model.response.*
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.io.use

@Singleton
class KtorNetwork @Inject constructor(@ApplicationContext context: Context) : NetworkDataSource {

    private val cache = ResponseCacheStorage(FileCacheStorage(File(context.cacheDir, "ktor_cache")))
    private val httpClient: HttpClient
        get() = KtorModule.provideKtor(cache)

}

