package com.qautomatron.kaper.steps

import com.qautomatron.kaper.core.driver.getDriver
import org.openqa.selenium.NoAlertPresentException

/**
 * Steps for handle alerts
 */
class AlertsSteps: Steps() {

    /**
     * Switch to alert and accept it
     */
    fun acceptAlert() = driver.switchTo().alert().accept()

    /**
     * Switch to alert and dismiss it
     */
    fun dismissAlert() = driver.switchTo().alert().dismiss()

    /**
     * Check alert present
     */
    fun isAlertPresent(): Boolean {
        return try {
            driver.switchTo().alert()
            true
        } catch (Ex: NoAlertPresentException) {
            false
        }
    }

    /**
     * Check alert with timeout
     * @param seconds timeout
     */
    fun isAlertPresent(seconds: Int): Boolean {
        for (i in 1..seconds) {
            try {
                getDriver().switchTo().alert()
                return true
            } catch (nape: NoAlertPresentException) {
                wait(1)
            }
        }
        return false
    }

    /**
     * Get alert text
     */
    fun getAlertText(): String = driver.switchTo().alert().text
}