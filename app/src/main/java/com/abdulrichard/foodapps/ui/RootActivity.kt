package com.abdulrichard.foodapps.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.abdulrichard.foodapps.R
import com.abdulrichard.foodapps.repository.BaseActivity
import com.abdulrichard.foodapps.support.showToast
import com.abdulrichard.foodapps.ui.home.activity.HomeActivity

class RootActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        initViewModel()
    }

    override fun initView() {
        Handler().postDelayed({
            if (verifyConnection((this))) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                showToast("Oops, Silahkan periksa koneksi internet anda.")
            }
        }, 3000)
    }

    override fun initViewModel() {
        initView()
    }

    private fun verifyConnection(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            else -> false
        }
    }


}