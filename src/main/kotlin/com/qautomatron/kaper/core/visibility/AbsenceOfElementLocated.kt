package com.qautomatron.kaper.core.visibility

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition

/**
 * Will check absence of element By locator
 */
class AbsenceOfElementLocated(private val locator: By) : ExpectedCondition<Boolean> {

    override fun apply(driver: WebDriver?): Boolean? {
        return try {
            val elements = driver?.findElements<WebElement>(locator)
            elements?.isEmpty() ?: false
        } catch (t: Throwable) {
            throw Error(t)
        }
    }
}