package com.connect.android.client.keyboard

/**
 * The observer that will be notified when the height of
 * the keyboard has changed
 */
// forked from here https://github.com/siebeprojects/samples-keyboardheight
interface KeyboardHeightObserver {

    /**
     * Called when the keyboard height has changed, 0 means keyboard is closed,
     * >= 1 means keyboard is opened.
     *
     * @param height        The height of the keyboard in pixels
     * @param orientation   The orientation either: Configuration.ORIENTATION_PORTRAIT or
     * Configuration.ORIENTATION_LANDSCAPE
     */
    fun onKeyboardHeightChanged(height: Int, orientation: Int)
}