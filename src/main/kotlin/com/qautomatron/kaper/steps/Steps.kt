package com.qautomatron.kaper.steps

import com.qautomatron.kaper.core.driver.getDriver
import com.qautomatron.kaper.core.driver.resetImplicitTimeout
import com.qautomatron.kaper.core.element.KElement
import com.qautomatron.kaper.core.element.KElements
import com.qautomatron.kaper.core.element.MElement
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

abstract class Steps(val driver: WebDriver = getDriver()) {

    protected val js = driver as JavascriptExecutor

    init {
        driver.resetImplicitTimeout()
    }

    fun wait(sec: Int) {
        Thread.sleep((sec * 1000).toLong())
    }

    protected fun mEl(locator: By) = MElement(locator, driver)

    @Deprecated("use kEls instead", ReplaceWith("KElements(locator, driver)", "com.qautomatron.kaper.core.element.KElements"))
    protected fun mEls(locator: By) = KElements(locator, driver)

    protected fun kEl(locator: By) = KElement(locator, driver)

    protected fun kEls(locator: By) = KElements(locator, driver)
}