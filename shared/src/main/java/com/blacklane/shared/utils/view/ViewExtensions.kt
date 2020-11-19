package com.blacklane.shared.utils.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.blacklane.shared.app.BaseFragment

/**
 * Easy toast function for Fragment.
 */
fun BaseFragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(baseActivity, text, duration).show()
}


/**
 * Inflate the layout specified by [layoutRes].
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}