package com.cryptoapp.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Raw(
    @SerializedName("RAW")
    val raw: JsonObject
)
