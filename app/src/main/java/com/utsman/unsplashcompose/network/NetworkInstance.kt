package com.utsman.unsplashcompose.network

import com.utsman.unsplashcompose.ConstantValue
import com.utsman.unsplashcompose.model.Result
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkInstance {

    @GET("/photos")
    suspend fun photos(
        @Query("page") page: Int,
        @Query("client_id") clientId: String = ConstantValue.clientId
    ): List<Result.Photo>

    @GET("/photos/{id}")
    suspend fun photo(
        @Path("id") id: String,
        @Query("client_id") clientId: String = ConstantValue.clientId
    ): Result.Photo

    companion object {
        private val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(ConstantValue.baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun create(): NetworkInstance = retrofit.create(NetworkInstance::class.java)
    }
}