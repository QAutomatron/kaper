package com.qautomatron.kaper.core.element

import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class MElementLocator(override val by: By,
                      private val driver: WebDriver) : ElementLocator<MobileElement> {

    override fun find(): MobileElement {
        return driver.findElement(by)
    }

    override val description: String
        get() = by.toString()
}