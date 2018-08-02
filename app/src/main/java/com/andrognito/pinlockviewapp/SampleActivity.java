package com.andrognito.pinlockviewapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.view.View;
import android.view.View.*;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.InputField;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.andrognito.pinlockview.SeparateDeleteButton;

public class SampleActivity extends AppCompatActivity {

    public static final String TAG = "PinLockView";

    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private InputField mInputField;
    private SeparateDeleteButton mSeparateDeleteButton;
    private ImageView logo;
    private boolean isEnterButtonEnabled = true;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);
            Toast.makeText(SampleActivity.this, "Pin complete: " + pin, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sample);
        logo = (ImageView) findViewById(R.id.profile_image);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mInputField = (InputField) findViewById(R.id.input_field);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mSeparateDeleteButton = (SeparateDeleteButton) findViewById(R.id.separate_delete_button);

        mPinLockView.attachInputField(mInputField);
        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.attachSeparateDeleteButton(mSeparateDeleteButton);

        mPinLockView.setPinLockListener(mPinLockListener);
        mPinLockView.setPinLength(6);
        mPinLockView.setShowDeleteButton(false);
        ((RelativeLayout.LayoutParams) mPinLockView.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.input_field);
        mInputField.setVisibility(View.VISIBLE);
        mInputField.requestFocus();

        mSeparateDeleteButton.setShowSeparateDeleteButton(true);
        mSeparateDeleteButton.setSeparateDeleteButtonColor(Color.TRANSPARENT);
        mSeparateDeleteButton.setSeparateDeleteButtonPressedColor(Color.GRAY);
        mSeparateDeleteButton.setImageResource(R.drawable.ic_keyboard_backspace);

        mPinLockView.setUseCustomEnterButtonImages(true);
        mPinLockView.setEnterButtonEnabledDrawableId(R.drawable.ic_check_box);
        mPinLockView.setEnterButtonDisabledDrawableId(R.drawable.ic_check_box_outline);
        mPinLockView.setEnterButtonPressedDrawableId(R.drawable.ic_check_box);
        mPinLockView.setDeleteButtonDrawable(getResources().getDrawable(R.drawable.ic_cheveron_left));

        mPinLockView.detachIndicatorDots();
        mIndicatorDots.setVisibility(View.GONE);
        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FIXED);

        logo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (isEnterButtonEnabled) {

                    isEnterButtonEnabled = false;
                    mPinLockView.setShowEnterButton(false);
                    mPinLockView.setSwapEnterDeleteButtons(false);
                    mPinLockView.setShowDeleteButton(true);
                    mSeparateDeleteButton.setShowSeparateDeleteButton(false);
                    mInputField.setVisibility(View.GONE);
                    mIndicatorDots.setVisibility(View.VISIBLE);
                    ((RelativeLayout.LayoutParams) mPinLockView.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.indicator_dots);
                    mPinLockView.resetPinLockView();

                    mPinLockView.detachInputField();
                    mPinLockView.detachSeparateDeleteButton();
                    mPinLockView.attachIndicatorDots(mIndicatorDots);
                } else{

                    isEnterButtonEnabled = true;
                    mPinLockView.setShowEnterButton(true);
                    mPinLockView.setSwapEnterDeleteButtons(true);
                    mPinLockView.setShowDeleteButton(false);
                    mSeparateDeleteButton.setShowSeparateDeleteButton(true);
                    mInputField.setVisibility(View.VISIBLE);
                    mIndicatorDots.setVisibility(View.GONE);
                    ((RelativeLayout.LayoutParams) mPinLockView.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.input_field);
                    mInputField.requestFocus();
                    mPinLockView.resetPinLockView();

                    mPinLockView.attachInputField(mInputField);
                    mPinLockView.attachSeparateDeleteButton(mSeparateDeleteButton);
                    mPinLockView.detachIndicatorDots();
                }
            }
        });
    }
}
