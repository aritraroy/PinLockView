package com.andrognito.pinlockview

/**
 * The listener that triggers callbacks for various events
 * in the [PinLockView]
 *
 * Created by aritraroy on 31/05/16.
 */
interface PinLockListener {

    /**
     * Triggers when the complete pin is entered,
     * depends on the pin length set by the user
     *
     * @param pin the complete pin
     */
    fun onComplete(pin: String)


    /**
     * Triggers when the pin is empty after manual deletion
     */
    fun onEmpty()

    /**
     * Triggers on a key press on the [PinLockView]
     *
     * @param pinLength       the current pin length
     * @param intermediatePin the intermediate pin
     */
    fun onPinChange(pinLength: Int, intermediatePin: String)
}
