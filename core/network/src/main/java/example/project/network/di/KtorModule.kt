package example.project.network.di

import android.util.Log
import meysam.farmani.exampleproject.core.network.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.CacheStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.plugin
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorModule {
    companion object {
        private const val TIME_OUT = 60_000L
        fun provideKtor(cache: CacheStorage): HttpClient = HttpClient(Android) {
            install(HttpCache) {
                publicStorage(cache)
            }
            install(HttpRequestRetry) {
                retryIf(maxRetries = 100){_, _ ->
                    false
                }
                delayMillis { (3000).toLong() }
            }
            expectSuccess = true
            defaultRequest {
                url(BuildConfig.BASE_URL)
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                headers {
                    append("App-Version",BuildConfig.LIBRARY_VERSION_NAME)
                    append("Client-Name","Android")
                }
            }

            install(ContentNegotiation) {
//               val converter = KotlinxSerializationConverter(Json {
//                   prettyPrint = true
//                   ignoreUnknownKeys = true
//                   isLenient = true
//                   encodeDefaults = true
//                })
//                register(ContentType.Application.Json, converter)

                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        isLenient = true
                        encodeDefaults = true
                    }
                )
            }

            install(HttpTimeout) {
                connectTimeoutMillis = TIME_OUT
                socketTimeoutMillis = TIME_OUT
                requestTimeoutMillis = TIME_OUT
            }

            install(ResponseObserver) {
                onResponse { response ->
                    if (BuildConfig.DEBUG) {
                        Log.v("Logger Ktor =>", "status: ${response.status.value}")
                        Log.v("Logger Ktor =>", "Response: $response")
                    }
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        if (BuildConfig.DEBUG) {
                            Log.v("Logger Ktor =>", message)
                        }
                    }

                }
                level = LogLevel.ALL
            }
        }.also {
            it.plugin(HttpSend).intercept { request ->
                val originalCall = execute(request)
                if (originalCall.response.status.value !in 100..399) {
                    execute(request)
                } else {
                    originalCall
                }
            }

        }

       private fun provideMockEngine(cache: CacheStorage) = MockEngine { request ->
           val response = cache.findAll(request.url).first()
           respond(
               content = response.body,
               status = HttpStatusCode.OK,
               headers = headersOf(HttpHeaders.ContentType, "application/json")
           )
       }

        fun provideCachedKtor(cache: CacheStorage): HttpClient =
            HttpClient(provideMockEngine(cache)) {
                expectSuccess = true
                defaultRequest {
                    url(BuildConfig.BASE_URL)
                }
                defaultRequest {
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }

                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            ignoreUnknownKeys = true
                            isLenient = true
                            encodeDefaults = true
                        }
                    )
                }

                install(HttpTimeout) {
                    connectTimeoutMillis = TIME_OUT
                    socketTimeoutMillis = TIME_OUT
                    requestTimeoutMillis = TIME_OUT
                }

                install(ResponseObserver) {
                    onResponse { response ->
                        if (BuildConfig.DEBUG) {
                            Log.v("Logger Ktor Cache =>", "Response $response")
                        }
                    }
                }


                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            if (BuildConfig.DEBUG) {
                                Log.v("Logger Ktor Cache =>", message)
                            }
                        }

                    }
                    level = LogLevel.ALL
                }
            }.also {

                it.plugin(HttpSend).intercept { request ->
                    val originalCall = execute(request)
                    if (originalCall.response.status.value !in 100..399) {
                        execute(request)
                    } else {
                        originalCall
                    }
                }

            }
    }
}