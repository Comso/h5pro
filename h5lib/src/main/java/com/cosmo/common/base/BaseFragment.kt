package com.cosmo.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *Author:ruchao.jiang
 *Created: 2019/4/1 15:43
 *Email:ruchao.jiang@uama.com.cn
 */
abstract class BaseFragment:Fragment() {
    var mActivity:BaseActivity? = null
    abstract fun setLayout(): Int
    abstract fun fragmentStart()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let {
            mActivity = it as BaseActivity
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(setLayout(),container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentStart()

    }


}