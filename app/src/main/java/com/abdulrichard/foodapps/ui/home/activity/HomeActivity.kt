package com.abdulrichard.foodapps.ui.home.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulrichard.foodapps.databinding.ActivityHomeBinding
import com.abdulrichard.foodapps.network.ApiClient
import com.abdulrichard.foodapps.network.ApiService
import com.abdulrichard.foodapps.repository.BaseActivity
import com.abdulrichard.foodapps.support.gone
import com.abdulrichard.foodapps.support.showToast
import com.abdulrichard.foodapps.support.visible
import com.abdulrichard.foodapps.ui.home.module.HomeAdapter
import com.abdulrichard.foodapps.ui.home.module.HomeViewModel


class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter
    private lateinit var layoutManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
    }

    override fun initView() {
        setRecyclerView()

    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        ApiClient.getApiClient()?.let { viewModel.setHomeService(it.create(ApiService::class.java)) }
        viewModel.getListFood()
        initView()
        subscribeFoodLiveData()
    }

    private fun setRecyclerView(){
        layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        adapter = HomeAdapter(this)

        binding.rvFoodList.layoutManager = layoutManager
        binding.rvFoodList.adapter = adapter
    }

    private fun subscribeFoodLiveData() {
        viewModel.onLoading.observe(this) {
            if (it) {
                binding.loadingScreen.visible()
            } else {
                binding.loadingScreen.gone()
            }
        }

        viewModel.onSuccess.observe(this) { list ->
            adapter.addList(list)
        }

        viewModel.onFailure.observe(this) { message ->
            showToast(message)
        }
    }
}