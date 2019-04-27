package com.andrognito.pinlockview

import android.content.Context
import android.util.AttributeSet

import androidx.recyclerview.widget.GridLayoutManager

/**
 * Used to always maintain an LTR layout no matter what is the real device's layout direction
 * to avoid an unwanted reversed direction in RTL devices
 * Created by Idan on 7/6/2017.
 */
class LTRGridLayoutManager : GridLayoutManager {

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context, spanCount: Int) : super(context, spanCount)

    constructor(context: Context, spanCount: Int, orientation: Int, reverseLayout: Boolean)
            : super(context, spanCount, orientation, reverseLayout)

    override fun isLayoutRTL(): Boolean {
        return false
    }
}
