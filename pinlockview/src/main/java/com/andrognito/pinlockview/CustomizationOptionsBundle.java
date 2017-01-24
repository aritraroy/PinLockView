package com.andrognito.pinlockview;

import android.graphics.drawable.Drawable;

/**
 * The customization options for the buttons in {@link PinLockView}
 * passed to the {@link PinLockAdapter} to decorate the individual views
 * <p>
 * Created by aritraroy on 01/06/16.
 */
public class CustomizationOptionsBundle {

    private int textColor;
    private int textSize;
    private int buttonSize;
    private int buttonBackgroundId;
    private Drawable deleteButtonDrawable;
    private int deleteButtonSize;
    private boolean showDeleteButton;
    private int deleteButtonPressesColor;

    private boolean autoHideDeleteButton;
    private boolean showLeftButton;
    private String leftButtonText;
    private int leftButtonTextSize;
    private String textFontName;
    private boolean vibrateButtonClick;

    public CustomizationOptionsBundle() {
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getButtonSize() {
        return buttonSize;
    }

    public void setButtonSize(int buttonSize) {
        this.buttonSize = buttonSize;
    }

    public int getButtonBackgroundId() {
        return buttonBackgroundId;
    }

    public void setButtonBackgroundId(int buttonBackgroundId) {
        this.buttonBackgroundId = buttonBackgroundId;
    }

    public Drawable getDeleteButtonDrawable() {
        return deleteButtonDrawable;
    }

    public void setDeleteButtonDrawable(Drawable deleteButtonDrawable) {
        this.deleteButtonDrawable = deleteButtonDrawable;
    }

    public int getDeleteButtonSize() {
        return deleteButtonSize;
    }

    public void setDeleteButtonSize(int deleteButtonSize) {
        this.deleteButtonSize = deleteButtonSize;
    }

    public boolean isShowDeleteButton() {
        return showDeleteButton;
    }

    public void setShowDeleteButton(boolean showDeleteButton) {
        this.showDeleteButton = showDeleteButton;
    }

    public int getDeleteButtonPressesColor() {
        return deleteButtonPressesColor;
    }

    public void setDeleteButtonPressesColor(int deleteButtonPressesColor) {
        this.deleteButtonPressesColor = deleteButtonPressesColor;
    }

    public boolean isAutoHideDeleteButton() {
        return autoHideDeleteButton;
    }

    public void setAutoHideDeleteButton(boolean autoHideDeleteButton) {
        this.autoHideDeleteButton = autoHideDeleteButton;
    }

    public boolean isShowLeftButton() {
        return showLeftButton;
    }

    public void setShowLeftButton(boolean showLeftButton) {
        this.showLeftButton = showLeftButton;
    }

    public String getLeftButtonText() {
        return leftButtonText;
    }

    public void setLeftButtonText(String leftButtonText) {
        this.leftButtonText = leftButtonText;
    }

    public int getLeftButtonTextSize() {
        return leftButtonTextSize;
    }

    public void setLeftButtonTextSize(int leftButtonTextSize) {
        this.leftButtonTextSize = leftButtonTextSize;
    }

    public String getTextFontName() {
        return textFontName;
    }

    public void setTextFontName(String textFontName) {
        this.textFontName = textFontName;
    }


    public boolean isVibrateButtonClick() {
        return vibrateButtonClick;
    }

    public void setVibrateButtonClick(boolean vibrateButtonClick) {
        this.vibrateButtonClick = vibrateButtonClick;
    }
}
