package com.cryptoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoapp.db.CoinRoom
import com.cryptoapp.model.CoinInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    protected val dao = CoinRoom.getInstance().getDao()
    val selectedCoinsLiveData: LiveData<List<CoinInfo>> = dao.getSelectedCoins()

    fun deleteSelectedCoin(coin: CoinInfo){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteSelectedCoin(coin)
        }
    }

}