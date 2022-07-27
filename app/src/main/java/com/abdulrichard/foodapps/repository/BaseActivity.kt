package com.abdulrichard.foodapps.repository

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun initView()

    abstract fun initViewModel()

}