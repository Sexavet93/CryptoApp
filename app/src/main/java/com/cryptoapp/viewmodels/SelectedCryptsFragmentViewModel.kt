package com.cryptoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cryptoapp.api.ApiFactory
import com.cryptoapp.model.CoinInfo
import com.cryptoapp.model.CoinMainData
import com.cryptoapp.model.Raw
import com.cryptoapp.utils.CallbackImpl
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class SelectedCryptsFragmentViewModel : BaseViewModel() {

    private val cryptoApi = ApiFactory.cryptoApi
    private val _selectedCoinsInfoLiveData: MutableLiveData<List<CoinMainData>> = MutableLiveData()
    val selectedCoinsInfoLiveData: LiveData<List<CoinMainData>>
        get() = _selectedCoinsInfoLiveData

    fun getCoinInfo(coinInfoList: List<CoinInfo>) {
        var requestCounter = coinInfoList.size
        val coinList = mutableListOf<CoinMainData>()
        val callObList = coinInfoList.map { cryptoApi.getFullCoinInfo(fSyms = it.name) }
        callObList.forEach{
            it.enqueue(object : CallbackImpl<Raw>() {
                override fun onResponse(call: Call<Raw>, response: Response<Raw>) {
                    val jsonObject = response.body()?.raw ?: return
                    val coinKeySet = jsonObject.keySet()
                    for (coinKey in coinKeySet) {
                        val currencyJson = jsonObject.getAsJsonObject(coinKey)
                        val currencyKeySet = currencyJson.keySet()
                        for (currencyKey in currencyKeySet) {
                            val coinInfo = Gson().fromJson(
                                currencyJson.getAsJsonObject(currencyKey),
                                CoinMainData::class.java
                            )
                            coinInfo.imageUrl = CoinMainData.getImageUrl(coinInfo.imageUrl)
                            coinList.add(coinInfo)
                            requestCounter--
                            if(requestCounter == 0){
                                _selectedCoinsInfoLiveData.value = coinList
                            }
                        }
                    }
                }
            })
        }
    }
}