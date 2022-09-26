package com.cryptoapp.fragments

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cryptoapp.R
import com.cryptoapp.activities.MainActivity
import com.cryptoapp.adapters.SelectedCryptoListAdapter
import com.cryptoapp.databinding.FragmentSelectedCryptsBinding
import com.cryptoapp.model.CoinInfo
import com.cryptoapp.utils.SimpleCallbackImpl
import com.cryptoapp.viewmodels.SelectedCryptsFragmentViewModel

private const val HANDLER_TOKEN = "token"
private const val UPDATE_TIME = 20000L

class SelectedCryptsFragment : Fragment() {

    private var _binding: FragmentSelectedCryptsBinding? = null
    private val binding: FragmentSelectedCryptsBinding
        get() = _binding ?: throw RuntimeException("SelectedCryptsFragment binding is null")
    private val viewModel: SelectedCryptsFragmentViewModel by viewModels()
    private lateinit var adapter: SelectedCryptoListAdapter
    private val selectedCoinList: MutableList<CoinInfo> = mutableListOf()
    private val handler = Handler(Looper.getMainLooper())

    private val itemTouchHelper = ItemTouchHelper(object
        : SimpleCallbackImpl() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val swipedCoin = adapter.currentList[viewHolder.adapterPosition]
            val removedCoin = selectedCoinList.firstOrNull { it.name == swipedCoin.fromSymbol }
            removedCoin?.let {
                handler.removeCallbacksAndMessages(HANDLER_TOKEN)
                viewModel.deleteSelectedCoin(it)
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.title = "Selected Crypts"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectedCryptsBinding.inflate(inflater, container, false)
        itemTouchHelper.attachToRecyclerView(binding.recycleView)
        adapter = SelectedCryptoListAdapter{
            (requireActivity() as MainActivity)
                .beginTransactionAnimationRightToLeft(CryptoDetailsFragment.newInstance(it))
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SelectedCryptsFragment.adapter
        }
        setViewModelObservers()
        setOnAddButtonListener()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setViewModelObservers() {
        viewModel.selectedCoinsLiveData.observe(viewLifecycleOwner) {
            selectedCoinList.clear()
            selectedCoinList.addAll(it)
            viewModel.getCoinInfo(it)
            updateData()
        }

        viewModel.selectedCoinsInfoLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (binding.progressBar.visibility != View.GONE)
                binding.progressBar.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun updateData() {
        handler.postDelayed({
            viewModel.getCoinInfo(selectedCoinList)
            updateData()
        }, HANDLER_TOKEN, UPDATE_TIME)
    }

    private fun setOnAddButtonListener(){
        binding.addButton.setOnClickListener{
            (requireActivity() as MainActivity).selectTab(1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(HANDLER_TOKEN)
        _binding = null
    }

    companion object{
        @JvmStatic
        fun newInstance(): SelectedCryptsFragment{
            return SelectedCryptsFragment()
        }
    }
}