package com.qautomatron.kaper.core.element

import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class MElementListLocator(override val by: By, private val driver: WebDriver) : ElementLocator<List<MobileElement>> {

    override fun find(): List<MobileElement> {
        return driver.findElements(by)
    }

    override val description: String
        get() = "collection located {$by}"
}