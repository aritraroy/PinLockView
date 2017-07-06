package com.andrognito.pinlockview;

import android.graphics.drawable.Drawable;

/**
 * The customization options for the buttons in {@link PinLockView}
 * passed to the {@link PinLockAdapter} to decorate the individual views
 *
 * Created by aritraroy on 01/06/16.
 */
public class CustomizationOptionsBundle {

    private int numberTextColor;
    private int lettersTextColor;
    private int deleteButtonColor;
    private int textColor;
    private int textSize;
    private int numberTextSize;
    private int lettersTextSize;
    private boolean showLetters;
    private boolean isNumbersTextBold;
    private boolean isLettersTextBold;
    private boolean isDeleteDefaultColor;
    private int buttonSize;
    private Drawable buttonBackgroundDrawable;
    private Drawable deleteButtonDrawable;
    private int deleteButtonSize;
    private boolean showDeleteButton;
    private boolean useDeprecated;
    private int deleteButtonPressesColor;

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

    public int getNumbersTextColor() {
        return numberTextColor;
    }

    public void setNumbersTextColor(int textColor) {
        this.numberTextColor = textColor;
    }

    public int getLettersTextColor() {
        return lettersTextColor;
    }

    public void setLettersTextColor(int textColor) {
        this.lettersTextColor = textColor;
    }

    public int getNumbersTextSize() {
        return numberTextSize;
    }

    public void setNumbersTextSize(int textSize) {
        this.numberTextSize = textSize;
    }

    public int getLettersTextSize() {
        return lettersTextSize;
    }

    public void setLettersTextSize(int textSize) {
        this.lettersTextSize = textSize;
    }

    public boolean getShowLetters() {
        return showLetters;
    }

    public void setShowLetters(boolean showLetters) {
        this.showLetters = showLetters;
    }

    // Deprecated colour options
    public boolean getUseDeprecated() {
        return useDeprecated;
    }

    public void setUseDeprecated(boolean useDeprecated) {
        this.useDeprecated = useDeprecated;
    }

    public int getDeleteButtonColor() {
        return deleteButtonColor;
    }

    public void setDeleteButtonColor(int defaultColor) {
        this.deleteButtonColor = defaultColor;
    }

    public boolean getDeleteButtonDefault() {
        return isDeleteDefaultColor;
    }

    public void setDeleteButtonDefault(boolean isDefaultColor) {
        this.isDeleteDefaultColor = isDefaultColor;
    }

    public boolean getIsNumbersTextBold() {
        return isNumbersTextBold;
    }

    public void setIsNumbersTextBold(boolean isNumbersTextBold) {
        this.isNumbersTextBold = isNumbersTextBold;
    }

    public boolean getIsLettersTextBold() {
        return isLettersTextBold;
    }

    public void setIsLettersTextBold(boolean isLettersTextBold) {
        this.isLettersTextBold = isLettersTextBold;
    }

    public int getButtonSize() {
        return buttonSize;
    }

    public void setButtonSize(int buttonSize) {
        this.buttonSize = buttonSize;
    }

    public Drawable getButtonBackgroundDrawable() {
        return buttonBackgroundDrawable;
    }

    public void setButtonBackgroundDrawable(Drawable buttonBackgroundDrawable) {
        this.buttonBackgroundDrawable = buttonBackgroundDrawable;
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
}
