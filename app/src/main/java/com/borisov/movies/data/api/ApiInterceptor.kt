package com.borisov.movies.data.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Borisov Andrey on 28.06.2022
 **/
class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .build()
        )
}