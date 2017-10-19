package com.qautomatron.kaper.core.visibility

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition

class PresenceOfElementLocated(private val locator: By) : ExpectedCondition<Boolean> {

    override fun apply(driver: WebDriver?): Boolean? {
        return try {
            val elements = driver!!.findElements<WebElement>(locator)
            !elements.isEmpty()
        } catch (t: Throwable) {
            throw Error(t)
        }

    }
}