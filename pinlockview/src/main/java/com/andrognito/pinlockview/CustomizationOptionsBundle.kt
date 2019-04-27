package com.andrognito.pinlockview

import android.graphics.drawable.Drawable

/**
 * The customization options for the buttons in [PinLockView]
 * passed to the [PinLockAdapter] to decorate the individual views
 *
 * Created by aritraroy on 01/06/16.
 */
class CustomizationOptionsBundle {

    var textColor: Int = 0
    var textSize: Int = 0
    var buttonSize: Int = 0
    var buttonBackgroundDrawable: Drawable? = null
    var deleteButtonDrawable: Drawable? = null
    var deleteButtonSize: Int = 0
    var isShowDeleteButton: Boolean = false
    var deleteButtonPressesColor: Int = 0
}
