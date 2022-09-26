package com.cryptoapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
@Parcelize
data class CoinMainData(
    @SerializedName("TYPE")
    val type: String?,
    @SerializedName("MARKET")
    val market: String?,
    @SerializedName("FROMSYMBOL")
    val fromSymbol: String?,
    @SerializedName("TOSYMBOL")
    val toSymbol: String?,
    @SerializedName("FLAGS")
    val flags: String?,
    @SerializedName("PRICE")
    val price: String?,
    @SerializedName("LASTUPDATE")
    val lastUpdate: Long?,
    @SerializedName("LASTVOLUME")
    val lastVolume: String?,
    @SerializedName("LASTVOLUMETO")
    val lastVolumeTo: String?,
    @SerializedName("LASTTRADEID")
    val lastTradeId: String?,
    @SerializedName("VOLUMEDAY")
    val volumeDay: String?,
    @SerializedName("VOLUMEDAYTO")
    val volumeDayTo: String?,
    @SerializedName("VOLUME24HOUR")
    val volume24Hour: String?,
    @SerializedName("VOLUME24HOURTO")
    val volume24HourTo: String?,
    @SerializedName("OPENDAY")
    val openDay: String?,
    @SerializedName("HIGHDAY")
    val highDay: String?,
    @SerializedName("LOWDAY")
    val lowDay: String?,
    @SerializedName("OPEN24HOUR")
    val open24Hour: String?,
    @SerializedName("HIGH24HOUR")
    val high24Hour: String?,
    @SerializedName("LOW24HOUR")
    val low24Hour: String?,
    @SerializedName("LASTMARKET")
    val lastMarket: String?,
    @SerializedName("VOLUMEHOUR")
    val volumeHour: String?,
    @SerializedName("VOLUMEHOURTO")
    val volumeHourTo: String?,
    @SerializedName("OPENHOUR")
    val openHour: String?,
    @SerializedName("HIGHHOUR")
    val highHour: String?,
    @SerializedName("LOWHOUR")
    val lowHour: String?,
    @SerializedName("TOPTIERVOLUME24HOUR")
    val topTierVolume24Hour: String?,
    @SerializedName("TOPTIERVOLUME24HOURTO")
    val topTierVolume24HourTo: String?,
    @SerializedName("CHANGE24HOUR")
    val change24Hour: String?,
    @SerializedName("CHANGEPCT24HOUR")
    val changePCT24Hour: String?,
    @SerializedName("CHANGEDAY")
    val changeDay: String?,
    @SerializedName("CHANGEPCTDAY")
    val changePCTDay: String?,
    @SerializedName("SUPPLY")
    val supply: String?,
    @SerializedName("MKTCAP")
    val mktCap: String?,
    @SerializedName("TOTALVOLUME24H")
    val totalVolume24Hour: String?,
    @SerializedName("TOTALVOLUME24HTO")
    val totalVolume24HourTo: String?,
    @SerializedName("TOTALTOPTIERVOLUME24H")
    val totalTopTierVolume24Hour: String?,
    @SerializedName("TOTALTOPTIERVOLUME24HTO")
    val totalTopTierVolume24HourTo: String?,
    @SerializedName("IMAGEURL")
    var imageUrl: String?
) : Parcelable {
    companion object{
        fun getImageUrl(url: String?): String{
            return "https://cryptocompare.com$url"
        }

        fun getCoinNameAndCurrency(coin:CoinMainData): String{
            return "${coin.fromSymbol}/${coin.toSymbol}"
        }

        fun getLastUpdateTime(time: Long?): String{
            if (time == null) return ""
            val stamp = Timestamp(time * 1000)
            val date = Date(stamp.time)
            val pattern = "HH:mm:ss"
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(date)
        }
    }
}