package com.abdulrichard.foodapps.ui.detail.activity

import android.R.id
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.abdulrichard.foodapps.databinding.ActivityDetailFoodBinding
import com.abdulrichard.foodapps.network.ApiClient
import com.abdulrichard.foodapps.network.ApiService
import com.abdulrichard.foodapps.repository.BaseActivity
import com.abdulrichard.foodapps.support.gone
import com.abdulrichard.foodapps.support.showToast
import com.abdulrichard.foodapps.support.visible
import com.abdulrichard.foodapps.ui.detail.module.DetailFoodViewModel
import com.squareup.picasso.Picasso


class DetailFoodActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailFoodBinding
    private lateinit var viewModel: DetailFoodViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
    }

    override fun initView() {

    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[DetailFoodViewModel::class.java]
        ApiClient.getApiClient()?.let { viewModel.setDetailFoodService(it.create(ApiService::class.java)) }
        viewModel.getDetailFood( intent.getStringExtra("foodId") ?: "0")
        subscribeFoodDetailData()
        initView()
    }

    private fun subscribeFoodDetailData(){
        viewModel.onLoading.observe(this) {
            if (it) {
                binding.loadingScreen.visible()
            } else {
                binding.loadingScreen.gone()
            }
        }

        viewModel.onSuccess.observe(this) { list ->
            //Set Images
            Picasso.get()
                .load(list[0].foodImage)
                .resize(1480, 720)
                .centerCrop()
                .into(binding.ivFood)

            //Set other data
            binding.tvFoodName.text = list[0].foodName
            binding.tvFoodDesc.text = list[0].foodDesc
            binding.tvFoodLocation.text = list[0].foodLocation
            binding.tvFoodTag.text = list[0].foodTags

            binding.btPlayVideo.setOnClickListener {
                val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${list[0].foodVideo}"))
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=${list[0].foodVideo}")
                )
                try {
                    startActivity(appIntent)
                } catch (ex: ActivityNotFoundException) {
                    startActivity(webIntent)
                }
            }
        }

        viewModel.onFailure.observe(this) { message ->
            showToast(message)
        }
    }
}