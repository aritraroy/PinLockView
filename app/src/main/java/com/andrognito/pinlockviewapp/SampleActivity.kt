package com.andrognito.pinlockviewapp

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.andrognito.pinlockview.PinLockView

class SampleActivity : AppCompatActivity() {

    private var mPinLockView: PinLockView? = null
    private var mIndicatorDotsFirst: IndicatorDots? = null
    private var mIndicatorDotsSecond: IndicatorDots? = null

    private val mPinLockFirstListener = object : PinLockListener {
        override fun onComplete(pin: String) {
            Log.d(TAG, "Pin complete: $pin")
            val shake = AnimationUtils.loadAnimation(this@SampleActivity, R.anim.shake_wrong)
            mIndicatorDotsFirst!!.startAnimation(shake)
            //            mIndicatorDotsFirst.setErrorDots();
            mIndicatorDotsFirst!!.setSuccessDots()
            Handler().postDelayed({
                mIndicatorDotsFirst!!.setDefaultDots()
                mPinLockView!!.resetPinLockView()

                //                    mPinLockView.attachIndicatorDots(mIndicatorDotsSecond);
            }, 1000)
        }

        override fun onEmpty() {
            Log.d(TAG, "Pin empty")
        }

        override fun onPinChange(pinLength: Int, intermediatePin: String) {
            Log.d(TAG, "Pin changed, new length $pinLength with intermediate pin $intermediatePin")
        }
    }

    private val mPinLockSecondListener = object : PinLockListener {
        override fun onComplete(pin: String) {
            Log.d(TAG, "Pin complete: $pin")
            val shake = AnimationUtils.loadAnimation(this@SampleActivity, R.anim.shake_wrong)
            mIndicatorDotsFirst!!.startAnimation(shake)
            //            mIndicatorDotsFirst.setErrorDots();
            mIndicatorDotsFirst!!.setSuccessDots()
            Handler().postDelayed({
                mIndicatorDotsFirst!!.setDefaultDots()
                mPinLockView!!.resetPinLockView()

                //                    mPinLockView.attachIndicatorDots(mIndicatorDotsSecond);
            }, 1000)
        }

        override fun onEmpty() {
            Log.d(TAG, "Pin empty")
        }

        override fun onPinChange(pinLength: Int, intermediatePin: String) {
            Log.d(TAG, "Pin changed, new length $pinLength with intermediate pin $intermediatePin")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_sample)

        mPinLockView = findViewById(R.id.pin_lock_view)
        mIndicatorDotsFirst = findViewById(R.id.indicator_dots_first)
        mIndicatorDotsSecond = findViewById(R.id.indicator_dots_second)


        mPinLockView!!.attachIndicatorDots(mIndicatorDotsFirst!!)
        mPinLockView!!.setPinLockListener(mPinLockFirstListener)
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        mPinLockView!!.pinLength = 4
        mPinLockView!!.textColor = ContextCompat.getColor(this, R.color.white)

        mIndicatorDotsFirst!!.indicatorType = IndicatorDots.IndicatorType.FIXED
    }

    companion object {

        val TAG = "PinLockView"
    }
}
