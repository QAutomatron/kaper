package com.qautomatron.kaper.steps

import com.qautomatron.kaper.core.driver.getDriver
import com.qautomatron.kaper.core.driver.resetImplicitTimeout
import com.qautomatron.kaper.core.element.MElement
import com.qautomatron.kaper.core.element.MElements
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import org.openqa.selenium.NoAlertPresentException

abstract class Steps(val driver: AppiumDriver<MobileElement> = getDriver()) {

    init {
        driver.resetImplicitTimeout()
    }

    fun wait(sec: Int) {
        Thread.sleep((sec * 1000).toLong())
    }

    fun typeOnKeyboard(s: String) {
        driver.keyboard.sendKeys(s)
    }

    fun hideKeyboard() {
        try {
            driver.hideKeyboard()
        } catch (e: Exception) { }
    }

    fun dismissAlert() {
        driver.switchTo().alert().dismiss()
    }

    fun acceptAlert() {
        driver.switchTo().alert().accept()
    }

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

    protected fun mEl(locator: By) = MElement(locator, driver)

    protected fun mEls(locator: By) = MElements(locator, driver)
}