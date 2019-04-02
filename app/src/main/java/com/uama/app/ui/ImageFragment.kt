package com.uama.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.cosmo.common.base.BaseFragment
import com.uama.app.R
import kotlinx.android.synthetic.main.fragment_image_preview.*

/**
 *Author:ruchao.jiang
 *Created: 2019/4/1 15:40
 *Email:ruchao.jiang@uama.com.cn
 */
class ImageFragment :BaseFragment(){
    override fun setLayout(): Int= R.layout.fragment_image_preview
    companion object {
        const val ARG_URL = "URL"
        fun getInstance(url:String):ImageFragment{
            val fragment = ImageFragment()
            val bundle = Bundle()
            bundle.putString(ARG_URL,url)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun fragmentStart() {
        arguments?.let {
            Glide.with(context!!).load(it.getString(ARG_URL)).into(itemImage)
        }
    }
}