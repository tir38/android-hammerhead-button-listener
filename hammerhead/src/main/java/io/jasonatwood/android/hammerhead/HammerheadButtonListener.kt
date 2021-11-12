package io.jasonatwood.android.hammerhead

import android.view.KeyEvent

/**
 * Listener for Hammerhead buttons, both short and long press
 */
class HammerheadButtonListener {

    enum class Button {
        PREVIOUS,
        NEXT,
        BACK,
        CONFIRM,
    }

    private val deviceType = getHammerheadDeviceType()

    /**
     * Pass all [android.app.Activity.onKeyDown] events to this method.
     */
    fun onKeyDown(event: KeyEvent?): Boolean {
        return event?.let {
            when (deviceType) {
                HammerheadDeviceType.K1,
                HammerheadDeviceType.K2 -> {
                    it.startTracking()
                    true
                }
                HammerheadDeviceType.UNKNOWN -> {
                    false
                }
            }
        } ?: run { false }
    }

    /**
     * Pass all [android.app.Activity.onKeyLongPress] events to this method. This will return [Button]
     * for any long press events, or null for unknown button/key
     */
    fun onKeyLongPress(event: KeyEvent?): Button? {
        return event?.let {
            when (deviceType) {
                HammerheadDeviceType.K1 -> handleKaroo1Button(it)
                HammerheadDeviceType.K2 -> handleKaroo2Button(it)
                HammerheadDeviceType.UNKNOWN -> null
            }
        } ?: run { null }

    }

    /**
     * Pass all [android.app.Activity.onKeyUp] events to this method. This will only return
     * [Button] for short press key-up events, or null for unknown button/key. Since OS calls
     * [android.app.Activity.onKeyUp] for key ups even for for short and long presses, this method
     * will return null for key-ups after long press.
     */
    fun onKeyUp(event: KeyEvent?): Button? {
        return event?.let {
            // if this key was handled as a long press then when we hear up event,
            // it will have this flag... so ignore
            if (it.flags and (KeyEvent.FLAG_CANCELED_LONG_PRESS) != 0) {
                return null
            }
            return when (deviceType) {
                HammerheadDeviceType.K1 -> handleKaroo1Button(it)
                HammerheadDeviceType.K2 -> handleKaroo2Button(it)
                HammerheadDeviceType.UNKNOWN -> null
            }
        } ?: run { null }
    }

    /**
     * Karoo 1
     *   https://support.hammerhead.io/hc/en-us/articles/360011094513-Physical-Buttons
     *
     *   karooButtonKeyMapping = new HwButtonKeyHolder(261, 260, 262, 4);
     *      // button keycodes mapped from usage
     *
     *      "A" button -> "power / sleep" -> not mapped
     *      "B" button ->  "next, right, long press zoom in" -> 260 -> HwButtonKeyHolder.nextKeyCode
     *      "C" button -> "previous, left, longpress zoom out" -> 261 -> HwButtonKeyHolder.previousKeyCode
     *      "D" button -> "action / confirm" -> 262 ->  HwButtonKeyHolder.selectKeyCode
     *      "E" button -> " back" -> 4 -> HwButtonKeyHolder.backKeyCode
     *
     * Karoo 2
     *   https://support.hammerhead.io/hc/en-us/articles/360052360133-Karoo-2-Physical-Buttons
     *
     *   k2ButtonKeyMapping = new HwButtonKeyHolder(260, 261, 262, 4);
     *      // button keycodes inferred from same UX behaviour
     *
     *      "A" button -> "previous, left, long press to zoom out" -> 260
     *      "B" button -> "back and power" -> 4
     *      "C" button -> "next, right, long press to zoom in" -> 261
     *      "D" button -> "confirm" -> 262
     *      no "E" button
     *
     *
     *   public HwButtonKeyHolder(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
     *      this.previousKeyCode = paramInt1;
     *      this.nextKeyCode = paramInt2;
     *      this.selectKeyCode = paramInt3;
     *      this.backKeyCode = paramInt4;
     *   }
     *
     *   https://developer.android.com/reference/android/view/KeyEvent#KEYCODE_NAVIGATE_PREVIOUS : 260
     *   https://developer.android.com/reference/android/view/KeyEvent#KEYCODE_NAVIGATE_NEXT : 261
     *   https://developer.android.com/reference/android/view/KeyEvent#KEYCODE_NAVIGATE_IN : 262
     *   https://developer.android.com/reference/android/view/KeyEvent#KEYCODE_BACK : 4
     */
    private fun handleKaroo1Button(event: KeyEvent): Button? {
        return when (event.keyCode) {
            KeyEvent.KEYCODE_NAVIGATE_PREVIOUS -> Button.NEXT // B
            KeyEvent.KEYCODE_NAVIGATE_NEXT -> Button.PREVIOUS // C
            KeyEvent.KEYCODE_NAVIGATE_IN -> Button.CONFIRM // D
            KeyEvent.KEYCODE_BACK -> Button.BACK // E
            else -> null
        }
    }

    private fun handleKaroo2Button(event: KeyEvent): Button? {
        return when (event.keyCode) {
            KeyEvent.KEYCODE_NAVIGATE_PREVIOUS -> Button.PREVIOUS // A
            KeyEvent.KEYCODE_BACK -> Button.BACK // B
            KeyEvent.KEYCODE_NAVIGATE_NEXT -> Button.NEXT // C
            KeyEvent.KEYCODE_NAVIGATE_IN -> Button.CONFIRM // D
            else -> null
        }
    }
}

