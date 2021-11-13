package com.uvita.andrey.modules.repository.remote

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.uvita.andrey.BuildConfig
import okhttp3.ConnectionPool

import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val READ_TIMEOUT = 3000
const val CONNECT_TIMEOUT = 3000

abstract class BaseRepository protected constructor() {

    @JvmField
    protected var retrofit: Retrofit

//    private val customInterceptor: Interceptor
//        get() = Interceptor { chain: Interceptor.Chain ->
////            val original = chain.request()
////            val request = original.newBuilder()
////                .header("host", "xxx")
////                .method(original.method, original.body)
////                .build()
//            chain.proceed(request)
//        }

    init {

        val httpClientBuilder = OkHttpClient.Builder()
            // this params fixes SSL Handshake SocketTimeout issue
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
            .protocols(listOf(Protocol.HTTP_1_1))

        httpClientBuilder.addNetworkInterceptor(StethoInterceptor())
//        httpClientBuilder.addInterceptor(customInterceptor)
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonStringConverterFactory())
            .client(httpClientBuilder.build())
            .build()
    }
}