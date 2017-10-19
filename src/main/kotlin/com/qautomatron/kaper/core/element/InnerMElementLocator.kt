package com.qautomatron.kaper.core.element

import io.appium.java_client.MobileElement
import org.openqa.selenium.By

class InnerMElementLocator(override val by: By, private val element: MElement) : ElementLocator<MobileElement> {
    override fun find(): MobileElement {
        return element.mElement.findElement(by)
    }

    override val description: String
        get() = "($element).find($by)"
}