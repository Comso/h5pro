package com.cosmo.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.cosmo.common.R
import kotlinx.android.synthetic.main.widget_place_holder_view.view.*

/**
 * Author:ruchao.jiang
 * Created: 2019/3/5 11:33
 * Email:ruchao.jiang@uama.com.cn
 */
class PlaceHolderView : LinearLayout {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.widget_place_holder_view, this)

    }

    fun setPlaceText(text:String){
        placeHolderText.text = text
    }

    fun setPlaceImage(img:Int){
        placeHolderImg.setImageResource(img)
    }

}
