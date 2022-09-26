package com.cryptoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cryptoapp.databinding.CryptoListItemLayoutBinding
import com.cryptoapp.model.CoinInfo

class CryptoListAdapter(
    private val callback: AdapterCallback,
    var selectedCoinsList: List<CoinInfo>)
    :RecyclerView.Adapter<CryptoListAdapter.CoinHolder>(){

    var cryptoList: List<CoinInfo> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoListAdapter.CoinHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CryptoListItemLayoutBinding.inflate(inflater,parent,false)
        return CoinHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoListAdapter.CoinHolder, position: Int) {
        val coinInfo = cryptoList[position]
        holder.binding.apply {
            Glide.with(root).load(coinInfo.imageUrl).into(coinImage)
            selectedCoinsList.forEach{
                if(it.name == coinInfo.name){
                    checkBox.isChecked = true
                }
            }
        }
        holder.setImageClickListener(coinInfo)
    }

    override fun getItemCount() = cryptoList.size

    override fun onViewRecycled(holder: CoinHolder) {
        holder.binding.apply {
            checkBox.setOnCheckedChangeListener(null)
            root.setOnClickListener(null)
            checkBox.isChecked = false
        }
    }

    inner class CoinHolder(val binding: CryptoListItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

            fun setImageClickListener(coinInfo: CoinInfo){
                binding.apply {
                    checkBox.setOnCheckedChangeListener{_, isChecked ->
                        if(isChecked)
                            callback.addToSelectedList(coinInfo)
                        else
                            callback.removeToSelectedList(coinInfo)
                    }
                    root.setOnClickListener {
                        checkBox.isChecked = !checkBox.isChecked
                    }
                }
            }
    }
}

interface AdapterCallback{
    fun addToSelectedList(coinInfo: CoinInfo)
    fun removeToSelectedList(coinInfo: CoinInfo)
}