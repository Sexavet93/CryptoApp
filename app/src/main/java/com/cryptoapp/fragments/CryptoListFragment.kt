package com.cryptoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.cryptoapp.R
import com.cryptoapp.activities.MainActivity
import com.cryptoapp.adapters.AdapterCallback
import com.cryptoapp.adapters.CryptoListAdapter
import com.cryptoapp.databinding.FragmentCryptoListBinding
import com.cryptoapp.model.CoinInfo
import com.cryptoapp.model.CoinMainData
import com.cryptoapp.viewmodels.CryptoListFragmentViewModel

class CryptoListFragment : Fragment() {

    private var _binding: FragmentCryptoListBinding? = null
    private val binding: FragmentCryptoListBinding
        get() = _binding ?: throw RuntimeException("CryptoListFragment binding is null")
    private val viewModel: CryptoListFragmentViewModel by viewModels()
    private lateinit var adapter: CryptoListAdapter
    private var selectedCoinList: List<CoinInfo>? = null
    private var coinList: List<CoinInfo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.title = "Crypts List"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCryptoListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterAndRV()
        setViewModelObservers()
    }

    private fun setAdapterAndRV(){
        adapter = CryptoListAdapter(object : AdapterCallback{
            override fun addToSelectedList(coinInfo: CoinInfo) {
                viewModel.insertSelectedCoin(coinInfo)
            }

            override fun removeToSelectedList(coinInfo: CoinInfo) {
                viewModel.deleteSelectedCoin(coinInfo)
            }
        }, emptyList())
        binding.recycleView.apply {
            layoutManager = GridLayoutManager(requireContext(),3)
            adapter = this@CryptoListFragment.adapter
        }
    }

    private fun setViewModelObservers(){
        viewModel.coinListLiveData.observe(viewLifecycleOwner){ list ->
            if(list.isNotEmpty()){
                binding.progressBar.visibility = View.GONE
                coinList = list
                selectedCoinList?.let {
                    adapter.cryptoList = list
                }
            } else showToast(getString(R.string.try_again))
        }

        viewModel.selectedCoinsLiveData.observe(viewLifecycleOwner){
            selectedCoinList = it
            adapter.selectedCoinsList = it
            if(adapter.cryptoList.isEmpty()){
                adapter.cryptoList = coinList
            }
        }
    }

    private fun showToast(expression: String){
        Toast.makeText(requireContext(), expression, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        @JvmStatic
        fun newInstance(): CryptoListFragment{
            return CryptoListFragment()
        }
    }

}