package com.qautomatron.kaper.core.element

import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class MElement(locator: ElementLocator<WebElement>,
               driver: WebDriver) : KElement(locator, driver) {

    constructor(locator: By, driver: WebDriver) :
            this(KElementLocator(locator, driver), driver)

    val mElement: MobileElement
        get() = locator.find() as MobileElement

    fun setValue(value: String) {
        execute { setValue(value) }
    }
}