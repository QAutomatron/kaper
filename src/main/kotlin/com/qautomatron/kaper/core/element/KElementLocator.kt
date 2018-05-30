package com.qautomatron.kaper.core.element

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class KElementLocator(override val by: By,
                      private val driver: WebDriver) : ElementLocator<WebElement> {

    override fun find(): WebElement {
        return driver.findElement(by)
    }

    override val description: String
        get() = by.toString()
}