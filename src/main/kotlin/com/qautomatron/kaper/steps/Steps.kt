package com.qautomatron.kaper.steps

import com.qautomatron.kaper.core.driver.getDriver
import com.qautomatron.kaper.core.driver.resetImplicitTimeout
import com.qautomatron.kaper.core.element.KElement
import com.qautomatron.kaper.core.element.KElements
import org.openqa.selenium.By
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.WebDriver

abstract class Steps(val driver: WebDriver = getDriver()) {

    init {
        driver.resetImplicitTimeout()
    }

    fun wait(sec: Int) {
        Thread.sleep((sec * 1000).toLong())
    }

    // TODO move this
//    fun typeOnKeyboard(s: String) {
//        driver.keyboard.sendKeys(s)
//    }
//
//    fun hideKeyboard() {
//        try {
//            driver.hideKeyboard()
//        } catch (e: Exception) { }
//    }

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

    @Deprecated("use kEl instead")
    protected fun mEl(locator: By) = KElement(locator, driver)

    @Deprecated("use kEls instead")
    protected fun mEls(locator: By) = KElements(locator, driver)

    protected fun kEl(locator: By) = KElement(locator, driver)

    protected fun kEls(locator: By) = KElements(locator, driver)
}