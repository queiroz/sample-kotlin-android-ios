package com.otb.sampleandroid

import platform.UIKit.UIDevice

actual fun platformName(): String {
    return UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion
}