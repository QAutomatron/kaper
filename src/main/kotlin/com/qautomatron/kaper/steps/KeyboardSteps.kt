package com.qautomatron.kaper.steps

import io.appium.java_client.AppiumDriver

/**
 * Keyboard interaction steps
 */
class KeyboardSteps: Steps() {

    /**
     * Type text on keyboard
     */
    fun typeOnKeyboard(s: String) = (driver as? AppiumDriver<*>)?.keyboard?.sendKeys(s)

    /**
     * Hide keyboard and catch exception if it's already present on screen
     */
    fun hideKeyboard() {
        try {
            (driver as AppiumDriver<*>).hideKeyboard()
        } catch (e: Exception) { }
    }
}