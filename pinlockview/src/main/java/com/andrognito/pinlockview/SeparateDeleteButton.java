package com.andrognito.pinlockview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ClickableViewAccessibility")
public class SeparateDeleteButton extends AppCompatImageButton {

    private PinLockAdapter.OnDeleteClickListener mOnDeleteClickListener;

    private int mSeparateDeleteButtonColor;
    private int mSeparateDeleteButtonPressedColor;
    private boolean mShowSeparateDeleteButton = true;

    public SeparateDeleteButton(Context context) {
        super(context);
        initView(context, null);
    }

    public SeparateDeleteButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SeparateDeleteButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PinLockView);

        try {
            mSeparateDeleteButtonColor = typedArray.getColor(R.styleable.PinLockView_separateDeleteButtonColor, ResourceUtils.getColor(getContext(), R.color.white));
            mSeparateDeleteButtonPressedColor = typedArray.getColor(R.styleable.PinLockView_separateDeleteButtonPressedColor, ResourceUtils.getColor(getContext(), R.color.greyish));
        } finally {
            typedArray.recycle();
        }

        ViewCompat.setLayoutDirection(this, ViewCompat.LAYOUT_DIRECTION_LTR);

        setImageResource(R.drawable.ic_backspace);
        setBackgroundColor(Color.TRANSPARENT);
        setScaleType(ScaleType.FIT_CENTER);
        setColorFilter(getSeparateDeleteButtonColor(), PorterDuff.Mode.SRC_ATOP);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDeleteClickListener != null) {
                    mOnDeleteClickListener.onDeleteClicked();
                }
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnDeleteClickListener != null) {
                    mOnDeleteClickListener.onDeleteLongClicked();
                }
                return true;
            }
        });

        setOnTouchListener(new OnTouchListener() {
            private Rect rect;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    SeparateDeleteButton.this.setColorFilter(
                            getSeparateDeleteButtonPressedColor(), PorterDuff.Mode.SRC_ATOP);
                    rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SeparateDeleteButton.this.setColorFilter(
                            getSeparateDeleteButtonColor(), PorterDuff.Mode.SRC_ATOP);
                }
                if (leftButtonArea(v, event)) {
                    SeparateDeleteButton.this.setColorFilter(
                            getSeparateDeleteButtonColor(), PorterDuff.Mode.SRC_ATOP);
                }
                return false;
            }

            private boolean leftButtonArea(View v, MotionEvent event) {
                return rect != null && !rect.contains(v.getLeft() + (int) event.getX(),
                        v.getTop() + (int) event.getY());
            }
        });

    }

    public void setOnDeleteClickListener(PinLockAdapter.OnDeleteClickListener mOnDeleteClickListener) {
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }

    public int getSeparateDeleteButtonColor() {
        return mSeparateDeleteButtonColor;
    }

    public void setSeparateDeleteButtonColor(int mSeparateDeleteButtonColor) {
        this.mSeparateDeleteButtonColor = mSeparateDeleteButtonColor;
        setColorFilter(getSeparateDeleteButtonColor(), PorterDuff.Mode.SRC_ATOP);
    }

    public int getSeparateDeleteButtonPressedColor() {
        return mSeparateDeleteButtonPressedColor;
    }

    public void setSeparateDeleteButtonPressedColor(int mSeparateDeleteButtonPressedColor) {
        this.mSeparateDeleteButtonPressedColor = mSeparateDeleteButtonPressedColor;
    }

    public boolean isShowSeparateDeleteButton() {
        return mShowSeparateDeleteButton;
    }

    public void setShowSeparateDeleteButton(boolean mShowSeparateDeleteButton) {
        this.mShowSeparateDeleteButton = mShowSeparateDeleteButton;
    }
}
