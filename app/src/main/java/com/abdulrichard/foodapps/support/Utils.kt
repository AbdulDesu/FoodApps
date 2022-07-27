package com.abdulrichard.foodapps.support

import android.app.Activity
import android.view.View
import android.widget.Toast

// Toast
fun Activity.showToast(message: String?, isLongDuration: Boolean = false) {
    runOnUiThread {
        val duration = if (isLongDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(this, message, duration).show()
    }
}

// Layout Visibility
fun View.visible() {
    visibility = View.VISIBLE
}
fun View.invisible() {
    visibility = View.INVISIBLE
}
fun View.gone() {
    visibility = View.GONE
}