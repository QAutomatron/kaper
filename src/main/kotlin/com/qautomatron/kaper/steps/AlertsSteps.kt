package com.qautomatron.kaper.steps

import com.qautomatron.kaper.core.driver.getDriver
import org.openqa.selenium.NoAlertPresentException

class AlertsSteps: Steps() {

    fun acceptAlert() = driver.switchTo().alert().accept()

    fun dismissAlert() = driver.switchTo().alert().dismiss()

    fun isAlertPresent(): Boolean {
        return try {
            driver.switchTo().alert()
            true
        } catch (Ex: NoAlertPresentException) {
            false
        }
    }

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

    fun getAlertText(): String = driver.switchTo().alert().text

}