package com.sandbox.androidsandbox.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AffirmationsResponse(
    @SerializedName("affirmation")
    @Expose
    val affirmation: String
)