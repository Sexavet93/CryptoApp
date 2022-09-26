package com.cryptoapp.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("CoinInfo")
    val coinInfo: CoinInfo
)
