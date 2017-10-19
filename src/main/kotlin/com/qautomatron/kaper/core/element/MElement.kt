package com.qautomatron.kaper.core.element

import com.qautomatron.kaper.core.common.defaultWait
import com.qautomatron.kaper.core.visibility.visible
import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class MElement(locator: ElementLocator<WebElement>,
               driver: WebDriver) : Element<WebElement>(locator, driver) {

    constructor(locator: By, driver: WebDriver) :
            this(MElementLocator(locator, driver), driver)

    val mElement: MobileElement
        get() = locator.find() as MobileElement

    val text: String
        get() = mElement.text

    val tagName: String
        get() = mElement.tagName

    val isEnabled: Boolean
        get() = mElement.isEnabled

    val isDisplayed: Boolean
        get() = mElement.isDisplayed

    val isSelected: Boolean
        get() = mElement.isSelected

    fun click() {
        execute { click() }
    }

    fun clear() {
        execute { clear() }
    }

    fun sendKeys(vararg keysToSend: CharSequence) {
        execute { sendKeys(*keysToSend) }
    }

    fun setValue(value: String) {
        execute { setValue(value) }
    }

    private fun execute(commands: MobileElement.() -> Unit): MElement {
        super.waitFor(visible, defaultWait)
        mElement.commands()
        return this
    }

    override fun toString(): String {
        return locator.description
    }

    fun element(by: By): MElement {
        return MElement(InnerMElementLocator(by, this), driver)
    }

    fun getAttribute(s: String): String = mElement.getAttribute(s)
}