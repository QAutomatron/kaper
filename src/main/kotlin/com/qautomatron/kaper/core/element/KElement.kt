package com.qautomatron.kaper.core.element

import com.qautomatron.kaper.core.common.defaultWait
import com.qautomatron.kaper.core.visibility.visible
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

open class KElement(locator: ElementLocator<WebElement>,
                    driver: WebDriver) : Element<WebElement>(locator, driver) {

    constructor(locator: By, driver: WebDriver) :
            this(KElementLocator(locator, driver), driver)

    val wElement: WebElement
        get() = locator.find()

    val text: String
        get() = wElement.text

    val tagName: String
        get() = wElement.tagName

    val isEnabled: Boolean
        get() = wElement.isEnabled

    val isDisplayed: Boolean
        get() = wElement.isDisplayed

    val isSelected: Boolean
        get() = wElement.isSelected

    fun click() {
        execute { click() }
    }

    fun clear() {
        execute { clear() }
    }

    fun sendKeys(vararg keysToSend: CharSequence) {
        execute { sendKeys(*keysToSend) }
    }

    internal fun execute(commands: WebElement.() -> Unit): KElement {
        super.waitFor(visible, defaultWait)
        wElement.commands()
        return this
    }

    override fun toString(): String {
        return locator.description
    }

    fun element(by: By): KElement {
        return KElement(InnerKElementLocator(by, this), driver)
    }

    fun getAttribute(s: String): String = wElement.getAttribute(s)
}