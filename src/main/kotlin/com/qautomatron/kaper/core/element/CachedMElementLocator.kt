package com.qautomatron.kaper.core.element

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class CachedMElementLocator(private val locator: ElementLocator<List<WebElement>>,
                            private val index: Int, override val by: By) : ElementLocator<WebElement> {
    override val description: String
        get() = "${locator.description}[$index]"

    override fun find(): WebElement {
        return locator.find()[index]
    }
}