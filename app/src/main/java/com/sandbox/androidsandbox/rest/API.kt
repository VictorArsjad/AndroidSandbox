package com.sandbox.androidsandbox.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API {
    companion object {
        const val AFFIRMATIONS_BASE_URL_PATH = "https://www.affirmations.dev"
    }

    private fun <T> builder(endpoint: Class<T>): T {
        return Retrofit
            .Builder()
            .baseUrl(AFFIRMATIONS_BASE_URL_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(endpoint)
    }

    fun affirmationsApi(): Affirmations {
        return builder(Affirmations::class.java)
    }

}