package com.example.nikita_lebedev_interval_timer.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("App-Token", "test-app-token")
            .addHeader("Authorization", "Bearer test-token")
            .build()
        return chain.proceed(request)
    }
}