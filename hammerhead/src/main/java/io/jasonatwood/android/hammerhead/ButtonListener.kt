package io.jasonatwood.android.hammerhead

import android.view.KeyEvent

/**
 * Listener for Hammerhead buttons, both short and long press
 */
class ButtonListener {

    enum class Button {
        PREVIOUS,
        NEXT,
        BACK,
        CONFIRM,
    }

    private val deviceType = getDeviceType()

    /**
     * Pass all [android.app.Activity.onKeyDown] events to this method.
     */
    fun onKeyDown(event: KeyEvent?): Boolean {
        return event?.let {
            when (deviceType) {
                DeviceType.K1,
                DeviceType.K2 -> {
                    it.startTracking()
                    true
                }
                DeviceType.UNKNOWN -> {
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
                DeviceType.K1 -> handleKaroo1Button(it)
                DeviceType.K2 -> handleKaroo2Button(it)
                DeviceType.UNKNOWN -> null
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
                DeviceType.K1 -> handleKaroo1Button(it)
                DeviceType.K2 -> handleKaroo2Button(it)
                DeviceType.UNKNOWN -> null
            }
        } ?: run { null }
    }
}

