package com.cryptoapp.model

import com.google.gson.annotations.SerializedName

data class Usd(
    @SerializedName("USD")
    val usd: CoinMainData
)
