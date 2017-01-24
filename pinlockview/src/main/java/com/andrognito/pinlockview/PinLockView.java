package com.andrognito.pinlockview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Represents a numeric lock view which can used to taken numbers as input.
 * The length of the input can be customized using {@link PinLockView#setPinLength(int)}, the default value being 4
 * <p/>
 * It can also be used as dial pad for taking number inputs.
 * Optionally, {@link IndicatorDots} can be attached to this view to indicate the length of the input taken
 * Created by aritraroy on 31/05/16.
 */
public class PinLockView extends RecyclerView {

    private static final int DEFAULT_PIN_LENGTH = 4;

    private String mPin = "";
    private int mPinLength;
    private int mHorizontalSpacing, mVerticalSpacing;
    private int mTextColor, mDeleteButtonPressedColor;
    private int mTextSize, mButtonSize, mDeleteButtonSize, mLeftButtonTextSize;
    private int mButtonBackgroundId;
    private Drawable mDeleteButtonDrawable;
    private boolean mShowDeleteButton, mAutoHideDeleteButton, mShowLeftButton, mVibrateButtonClick;
    private String mLeftButtonText, mTextFontName;

    private IndicatorDots mIndicatorDots;
    private PinLockAdapter mAdapter;
    private PinLockListener mPinLockListener;
    private LeftButtonClickListener mLeftButtonListener;
    private CustomizationOptionsBundle mCustomizationOptionsBundle;

    @NonNull
    private PinLockAdapter.OnLeftButtonClickListener createLeftButtonClickListener() {
        return new PinLockAdapter.OnLeftButtonClickListener() {
            @Override
            public void onLeftButtonClicked(PinLockAdapter.LeftButtonViewHolder numberViewHolder) {
                if (mLeftButtonListener != null) {
                    mLeftButtonListener.onLeftButtonClicked(numberViewHolder);
                }
            }
        };
    }

    @NonNull
    private PinLockAdapter.OnNumberClickListener createNumberClickListener() {
        return new PinLockAdapter.OnNumberClickListener() {
            @Override
            public void onNumberClicked(PinLockAdapter.NumberViewHolder numberViewHolder) {
                int position = numberViewHolder.getAdapterPosition();
                String key = getData(position);
                if (mPin.length() < getPinLength()) {
                    mPin = mPin.concat(key);

                    if (isIndicatorDotsAttached()) {
                        mIndicatorDots.updateDot(mPin.length());
                    }

                    if (mPin.length() == 1) {
                        mAdapter.setPinLength(mPin.length());
                        mAdapter.notifyItemChanged(position);
                    }

                    if (mPinLockListener != null) {
                        if (mPin.length() == mPinLength) {
                            mPinLockListener.onComplete(mPin);
                        } else {
                            mPinLockListener.onPinChange(mPin.length(), mPin);
                        }
                    }
                } else {
                    if (!isShowDeleteButton()) {
                        resetPinLockView();
                        mPin = mPin.concat(key);

                        if (isIndicatorDotsAttached()) {
                            mIndicatorDots.updateDot(mPin.length());
                        }

                        if (mPinLockListener != null) {
                            mPinLockListener.onPinChange(mPin.length(), mPin);
                        }

                    } else {
                        if (mPinLockListener != null) {
                            mPinLockListener.onComplete(mPin);
                        }
                    }
                }
            }
        };
    }

    @NonNull
    private PinLockAdapter.OnDeleteClickListener createDeleteClickListener() {
        return new PinLockAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClicked() {
                if (mPin.length() > 0) {
                    mPin = mPin.substring(0, mPin.length() - 1);

                    if (isIndicatorDotsAttached()) {
                        mIndicatorDots.updateDot(mPin.length());
                    }

                    if (mPin.length() == 0) {
                        mAdapter.setPinLength(mPin.length());
                        mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
                    }

                    if (mPinLockListener != null) {
                        if (mPin.length() == 0) {
                            mPinLockListener.onEmpty();
                            clearInternalPin();
                        } else {
                            mPinLockListener.onPinChange(mPin.length(), mPin);
                        }
                    }
                } else {
                    if (mPinLockListener != null) {
                        mPinLockListener.onEmpty();
                    }
                }
            }

            @Override
            public void onDeleteLongClicked() {
                resetPinLockView();
                if (mPinLockListener != null) {
                    mPinLockListener.onEmpty();
                }
            }
        };
    }

    public PinLockView(Context context) {
        super(context);
        init(null, 0);
    }

    public PinLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PinLockView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attributeSet, int defStyle) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.PinLockView);

        try {
            mPinLength = typedArray.getInt(R.styleable.PinLockView_pinLength, DEFAULT_PIN_LENGTH);
            mHorizontalSpacing = (int) typedArray.getDimension(R.styleable.PinLockView_keypadHorizontalSpacing, ResourceUtils.getDimensionInPx(getContext(), R.dimen.default_horizontal_spacing));
            mVerticalSpacing = (int) typedArray.getDimension(R.styleable.PinLockView_keypadVerticalSpacing, ResourceUtils.getDimensionInPx(getContext(), R.dimen.default_vertical_spacing));
            mTextColor = typedArray.getColor(R.styleable.PinLockView_keypadTextColor, ResourceUtils.getColor(getContext(), R.color.white));
            mTextSize = (int) typedArray.getDimension(R.styleable.PinLockView_keypadTextSize, ResourceUtils.getDimensionInPx(getContext(), R.dimen.default_text_size));
            mTextFontName = typedArray.getString(R.styleable.PinLockView_keypadTextFont);
            mVibrateButtonClick = typedArray.getBoolean(R.styleable.PinLockView_keypadVibrateButtonClick, true);
            mButtonSize = (int) typedArray.getDimension(R.styleable.PinLockView_keypadButtonSize, ResourceUtils.getDimensionInPx(getContext(), R.dimen.default_button_size));
            mButtonBackgroundId = typedArray.getResourceId(R.styleable.PinLockView_keypadButtonBackground, 0);
            mDeleteButtonSize = (int) typedArray.getDimension(R.styleable.PinLockView_keypadDeleteButtonSize, ResourceUtils.getDimensionInPx(getContext(), R.dimen.default_delete_button_size));
            mDeleteButtonDrawable = typedArray.getDrawable(R.styleable.PinLockView_keypadDeleteButtonDrawable);
            mDeleteButtonPressedColor = typedArray.getColor(R.styleable.PinLockView_keypadDeleteButtonPressedColor, ResourceUtils.getColor(getContext(), R.color.greyish));
            mShowDeleteButton = typedArray.getBoolean(R.styleable.PinLockView_keypadShowDeleteButton, true);
            mShowLeftButton = typedArray.getBoolean(R.styleable.PinLockView_keypadShowLeftButton, false);
            mLeftButtonText = typedArray.getString(R.styleable.PinLockView_keypadLeftButtonText);
            mLeftButtonTextSize = (int) typedArray.getDimension(R.styleable.PinLockView_keypadLeftButtonTextSize, ResourceUtils.getDimensionInPx(getContext(), R.dimen.default_text_size));
            mAutoHideDeleteButton = typedArray.getBoolean(R.styleable.PinLockView_keypadAutoHideDeleteButton, true);
        } finally {
            typedArray.recycle();
        }

        mCustomizationOptionsBundle = new CustomizationOptionsBundle();
        mCustomizationOptionsBundle.setTextColor(mTextColor);
        mCustomizationOptionsBundle.setTextSize(mTextSize);
        mCustomizationOptionsBundle.setTextFontName(mTextFontName);
        mCustomizationOptionsBundle.setVibrateButtonClick(mVibrateButtonClick);
        mCustomizationOptionsBundle.setButtonSize(mButtonSize);
        mCustomizationOptionsBundle.setButtonBackgroundId(mButtonBackgroundId);
        mCustomizationOptionsBundle.setDeleteButtonDrawable(mDeleteButtonDrawable);
        mCustomizationOptionsBundle.setDeleteButtonSize(mDeleteButtonSize);
        mCustomizationOptionsBundle.setShowLeftButton(mShowLeftButton);
        mCustomizationOptionsBundle.setLeftButtonText(mLeftButtonText);
        mCustomizationOptionsBundle.setLeftButtonTextSize(mLeftButtonTextSize);
        mCustomizationOptionsBundle.setShowDeleteButton(mShowDeleteButton);
        mCustomizationOptionsBundle.setAutoHideDeleteButton(mAutoHideDeleteButton);
        mCustomizationOptionsBundle.setDeleteButtonPressesColor(mDeleteButtonPressedColor);
        initView();
    }

    private void initView() {
        setLayoutManager(new GridLayoutManager(getContext(), 3));

        mAdapter = new PinLockAdapter(getContext());
        mAdapter.setOnItemClickListener(createNumberClickListener());
        mAdapter.setOnDeleteClickListener(createDeleteClickListener());
        mAdapter.setOnLeftButtonClickListener(createLeftButtonClickListener());
        mAdapter.setCustomizationOptions(mCustomizationOptionsBundle);
        setAdapter(mAdapter);

        addItemDecoration(new ItemSpaceDecoration(mHorizontalSpacing, mVerticalSpacing, 3, false));
        setOverScrollMode(OVER_SCROLL_NEVER);
    }


    /**
     * Sets a {@link PinLockListener} to the to listen to pin update events
     *
     * @param pinLockListener the listener
     */
    public void setPinLockListener(PinLockListener pinLockListener) {
        this.mPinLockListener = pinLockListener;
    }

    /**
     * Sets a {@link LeftButtonClickListener} to the to listen to left button update events
     *
     * @param leftButtonListener the listener
     */
    public void setOnClickButtonLeftListener(LeftButtonClickListener leftButtonListener) {
        this.mLeftButtonListener = leftButtonListener;
    }

    /**
     * Get the length of the current pin length
     *
     * @return the length of the pin
     */
    public int getPinLength() {
        return mPinLength;
    }

    /**
     * Sets the pin length dynamically
     *
     * @param pinLength the pin length
     */
    public void setPinLength(int pinLength) {
        this.mPinLength = pinLength;

        if (isIndicatorDotsAttached()) {
            mIndicatorDots.setPinLength(pinLength);
        }
    }

    /**
     * Get the text color in the buttons
     *
     * @return the text color
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * Set the text color of the buttons dynamically
     *
     * @param textColor the text color
     */
    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        mCustomizationOptionsBundle.setTextColor(textColor);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Get the size of the text in the buttons
     *
     * @return the size of the text in pixels
     */
    public int getTextSize() {
        return mTextSize;
    }

    /**
     * Set the size of text in pixels
     *
     * @param textSize the text size in pixels
     */
    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
        mCustomizationOptionsBundle.setTextSize(textSize);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Gets text font name.
     *
     * @return the text font name
     */
    public String getTextFontName() {
        return mTextFontName;
    }

    /**
     * Sets text font.
     *
     * @param fontPathName the font path name
     */
    public void setTextFont(String fontPathName) {
        this.mTextFontName = fontPathName;
        mCustomizationOptionsBundle.setTextFontName(fontPathName);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Is vibrate button click boolean.
     *
     * @return the boolean
     */
    public boolean isVibrateButtonClick() {
        return mVibrateButtonClick;
    }

    /**
     * Sets vibrate button click.
     *
     * @param isVibrate the is vibrate
     */
    public void setVibrateButtonClick(boolean isVibrate) {
        this.mVibrateButtonClick = isVibrate;
        mCustomizationOptionsBundle.setVibrateButtonClick(isVibrate);
    }

    /**
     * Get the size of the pin buttons
     *
     * @return the size of the button in pixels
     */
    public int getButtonSize() {
        return mButtonSize;
    }

    /**
     * Set the size of the pin buttons dynamically
     *
     * @param buttonSize the button size
     */
    public void setButtonSize(int buttonSize) {
        this.mButtonSize = buttonSize;
        mCustomizationOptionsBundle.setButtonSize(buttonSize);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Get the current background drawable of the buttons, can be null
     *
     * @return the background id
     */
    public int getButtonBackgroundDrawable() {
        return mButtonBackgroundId;
    }

    /**
     * Set the background id of the buttons dynamically
     *
     * @param id the background id
     */
    public void setButtonBackgroundId(int id) {
        this.mButtonBackgroundId = id;
        mCustomizationOptionsBundle.setButtonBackgroundId(id);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Get the drawable of the delete button
     *
     * @return the delete button drawable
     */
    public Drawable getDeleteButtonDrawable() {
        return mDeleteButtonDrawable;
    }

    /**
     * Set the drawable of the delete button dynamically
     *
     * @param deleteBackgroundDrawable the delete button drawable
     */
    public void setDeleteButtonDrawable(Drawable deleteBackgroundDrawable) {
        this.mDeleteButtonDrawable = deleteBackgroundDrawable;
        mCustomizationOptionsBundle.setDeleteButtonDrawable(deleteBackgroundDrawable);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Get the delete button size in pixels
     *
     * @return size in pixels
     */
    public int getDeleteButtonSize() {
        return mDeleteButtonSize;
    }

    /**
     * Set the size of the delete button in pixels
     *
     * @param deleteButtonSize size in pixels
     */
    public void setDeleteButtonSize(int deleteButtonSize) {
        this.mDeleteButtonSize = deleteButtonSize;
        mCustomizationOptionsBundle.setDeleteButtonSize(deleteButtonSize);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Is the delete button shown
     *
     * @return returns true if shown, false otherwise
     */
    public boolean isShowDeleteButton() {
        return mShowDeleteButton;
    }

    /**
     * Dynamically set if the delete button should be shown
     *
     * @param showDeleteButton true if the delete button should be shown, false otherwise
     */
    public void setShowDeleteButton(boolean showDeleteButton) {
        this.mShowDeleteButton = showDeleteButton;
        mCustomizationOptionsBundle.setShowDeleteButton(showDeleteButton);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Get the delete button pressed/focused state color
     *
     * @return color of the button
     */
    public int getDeleteButtonPressedColor() {
        return mDeleteButtonPressedColor;
    }

    /**
     * Set the pressed/focused state color of the delete button
     *
     * @param deleteButtonPressedColor the color of the delete button
     */
    public void setDeleteButtonPressedColor(int deleteButtonPressedColor) {
        this.mDeleteButtonPressedColor = deleteButtonPressedColor;
        mCustomizationOptionsBundle.setDeleteButtonPressesColor(deleteButtonPressedColor);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Is show left button boolean.
     *
     * @return the boolean
     */
    public boolean isShowLeftButton() {
        return mShowLeftButton;
    }

    /**
     * Sets show left button.
     *
     * @param isShow  is show left button
     */
    public void setShowLeftButton(boolean isShow) {
        this.mShowLeftButton = isShow;
        mCustomizationOptionsBundle.setShowLeftButton(isShow);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Gets left button text.
     *
     * @return the left button text
     */
    public String getLeftButtonText() {
        return mLeftButtonText;
    }

    /**
     * Sets left button text.
     *
     * @param text the text show in left button
     */
    public void setLeftButtonText(String text) {
        this.mLeftButtonText = text;
        mCustomizationOptionsBundle.setLeftButtonText(text);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Gets left button text size.
     *
     * @return the left button text size
     */
    public int getLeftButtonTextSize() {
        return mLeftButtonTextSize;
    }

    /**
     * Sets left button text size.
     *
     * @param textSize the text size in left button
     */
    public void setLeftButtonTextSize(int textSize) {
        this.mLeftButtonTextSize = textSize;
        mCustomizationOptionsBundle.setLeftButtonTextSize(textSize);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Is auto hide delete button boolean.
     *
     * @return the boolean auto hide delete button
     */
    public boolean isAutoHideDeleteButton() {
        return mAutoHideDeleteButton;
    }

    /**
     * Sets auto hide delete button.
     *
     * @param isAutoHide the is auto hide delete button
     */
    public void setAutoHideDeleteButton(boolean isAutoHide) {
        this.mAutoHideDeleteButton = isAutoHide;
        mCustomizationOptionsBundle.setAutoHideDeleteButton(isAutoHide);
        mAdapter.notifyDataSetChanged();
    }

    private void clearInternalPin() {
        mPin = "";
    }

    /**
     * Resets the {@link PinLockView}, clearing the entered pin
     * and resetting the {@link IndicatorDots} if attached
     */
    public void resetPinLockView() {

        clearInternalPin();

        mAdapter.setPinLength(mPin.length());
        mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);

        if (mIndicatorDots != null) {
            mIndicatorDots.updateDot(mPin.length());
        }
    }

    /**
     * Returns true if {@link IndicatorDots} are attached to {@link PinLockView}
     *
     * @return true if attached, false otherwise
     */
    public boolean isIndicatorDotsAttached() {
        return mIndicatorDots != null;
    }

    /**
     * Attaches {@link IndicatorDots} to {@link PinLockView}
     *
     * @param mIndicatorDots the view to attach
     */
    public void attachIndicatorDots(IndicatorDots mIndicatorDots) {
        this.mIndicatorDots = mIndicatorDots;
    }

    public String getData(int position) {
        if (position == 10)
            return "0";
        return String.valueOf((position + 1) % 10);
    }

}
