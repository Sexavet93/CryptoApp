package com.cryptoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cryptoapp.api.ApiFactory
import com.cryptoapp.model.CoinInfo
import com.cryptoapp.model.CoinMainData
import com.cryptoapp.model.CommonData
import com.cryptoapp.utils.CallbackImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CryptoListFragmentViewModel: BaseViewModel() {


    private val cryptoApi = ApiFactory.cryptoApi
    private val callOb = cryptoApi.getTopCoinsInfo()
    private val _coinListLiveData: MutableLiveData<List<CoinInfo>> = MutableLiveData()
    val coinListLiveData: LiveData<List<CoinInfo>>
        get() = _coinListLiveData

    init{
        val nameList = mutableListOf<CoinInfo>()
        callOb.enqueue(object: CallbackImpl<CommonData>() {
            override fun onResponse(call: Call<CommonData>, response: Response<CommonData>) {
                response.body()?.coinList?.forEach{
                    it.coinInfo.imageUrl = CoinMainData.getImageUrl(it.coinInfo.imageUrl)
                    nameList.add(it.coinInfo)
                }
                _coinListLiveData.value = nameList
            }
        })

    }

    fun insertSelectedCoin(coin: CoinInfo){
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertSelectedCoin(coin)
        }
    }

}