package com.abdulrichard.foodapps.network

import com.abdulrichard.foodapps.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        private var retrofit: Retrofit? = null

        private val baseUrl: String
            get() {
                return BuildConfig.BASE_URL
            }

        private fun provideHttpLoggingInterceptor() = run {
            HttpLoggingInterceptor().apply {
                apply { level = HttpLoggingInterceptor.Level.BODY }
            }
        }

        fun getApiClient(): Retrofit? {
            if (retrofit == null) {
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(provideHttpLoggingInterceptor())
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}