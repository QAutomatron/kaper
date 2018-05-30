package com.qautomatron.kaper.steps

import io.appium.java_client.AppiumDriver

class KeyboardSteps: Steps() {

    fun typeOnKeyboard(s: String) = (driver as AppiumDriver<*>).keyboard.sendKeys(s)

    fun hideKeyboard() {
        try {
            (driver as AppiumDriver<*>).hideKeyboard()
        } catch (e: Exception) { }
    }
}