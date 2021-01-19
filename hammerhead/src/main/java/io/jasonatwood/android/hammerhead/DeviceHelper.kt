package io.jasonatwood.android.hammerhead

import android.os.Build
import android.view.KeyEvent


/**
They are keying off system prop directly, not getting Build.DEVICE

public final HardwareType getHardwareType() {
String str = System.INSTANCE.getProp("ro.product.device");
if (str != null) {
int i = str.hashCode();
if (i != 3344) { // TODO why is this special?
if (i == 3367 && str.equals("k2"))
return HardwareType.K2;
} else if (str.equals("hx")) {
return HardwareType.KAROO;
}
}
return HardwareType.KAROO;
}
 */
internal enum class DeviceType {
    K1,
    K2,
    UNKNOWN
}

internal fun getDeviceType(): DeviceType {
    return when (Build.DEVICE) {
        "hx" -> DeviceType.K1
        "k2" -> DeviceType.K2
        else -> DeviceType.UNKNOWN
    }
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
 *   https://support.hammerhead.io/hc/en-us/articles/360052360133-Karoo-2-Physical-Buttons-
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
internal fun handleKaroo1Button(event: KeyEvent): ButtonListener.Button? {
    return when (event.keyCode) {
        KeyEvent.KEYCODE_NAVIGATE_PREVIOUS -> ButtonListener.Button.NEXT // B
        KeyEvent.KEYCODE_NAVIGATE_NEXT -> ButtonListener.Button.PREVIOUS // C
        KeyEvent.KEYCODE_NAVIGATE_IN -> ButtonListener.Button.CONFIRM // D
        KeyEvent.KEYCODE_BACK -> ButtonListener.Button.BACK // Ez
        else -> null
    }
}

internal fun handleKaroo2Button(event: KeyEvent): ButtonListener.Button? {
    return when (event.keyCode) {
        KeyEvent.KEYCODE_NAVIGATE_PREVIOUS -> ButtonListener.Button.PREVIOUS // A
        KeyEvent.KEYCODE_BACK -> ButtonListener.Button.BACK // B
        KeyEvent.KEYCODE_NAVIGATE_NEXT -> ButtonListener.Button.NEXT // C
        KeyEvent.KEYCODE_NAVIGATE_IN -> ButtonListener.Button.CONFIRM // D
        else -> null
    }
}