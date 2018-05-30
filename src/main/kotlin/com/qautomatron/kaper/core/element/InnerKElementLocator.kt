package com.qautomatron.kaper.core.element

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class InnerKElementLocator(override val by: By, private val element: KElement) : ElementLocator<WebElement> {
    override fun find(): WebElement {
        return element.wElement.findElement(by)
    }

    override val description: String
        get() = "($element).find($by)"
}