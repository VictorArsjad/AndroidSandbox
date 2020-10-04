package com.sandbox.androidsandbox.rest

import com.sandbox.androidsandbox.dto.AffirmationsResponse
import retrofit2.Call
import retrofit2.http.GET

interface Affirmations {

    @GET("/")
    fun getAffirmations(): Call<AffirmationsResponse>
}