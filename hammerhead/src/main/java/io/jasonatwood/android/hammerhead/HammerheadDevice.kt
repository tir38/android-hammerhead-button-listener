package io.jasonatwood.android.hammerhead

import android.os.Build

enum class HammerheadDeviceType {
    K1,
    K2,
    UNKNOWN
}

fun getHammerheadDeviceType(): HammerheadDeviceType {
    return when (Build.DEVICE) {
        "hx" -> HammerheadDeviceType.K1
        "k2" -> HammerheadDeviceType.K2
        else -> HammerheadDeviceType.UNKNOWN
    }
}