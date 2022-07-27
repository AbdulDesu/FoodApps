package com.abdulrichard.foodapps.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulrichard.foodapps.databinding.ActivityDetailFoodBinding
import com.abdulrichard.foodapps.repository.BaseActivity

class DetailFoodActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initViewModel() {
        TODO("Not yet implemented")
    }
}