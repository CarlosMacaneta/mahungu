package com.macaneta.mahungu.utils

import okhttp3.Interceptor
import okhttp3.Response

object AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader("X-Api-Key", "YOUR_API_KEY")
            .build()

        return chain.proceed(newRequest)
    }
}