package com.qautomatron.kaper.core.element

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class KElements(locator: ElementLocator<List<WebElement>>,
                driver: WebDriver) :
        Element<List<WebElement>>(locator, driver), Collection<KElement> {

    constructor(locator: By, driver: WebDriver)
            : this(KElementListLocator(locator, driver), driver)

    val webElements: List<WebElement>
        get() = locator.find()

    override val size: Int
        get() = webElements.size

    override fun isEmpty(): Boolean {
        return webElements.isEmpty()
    }

    override fun iterator(): Iterator<KElement> = object : Iterator<KElement> {
        var index = 0

        override fun hasNext(): Boolean {
            return webElements.size > this.index
        }

        override fun next(): KElement {
            val indexedElement = get(index)
            this.index += 1
            return indexedElement
        }
    }

    override fun toString(): String {
        return locator.description
    }

    override fun contains(element: KElement): Boolean {
        throw NotImplementedError()
    }

    override fun containsAll(elements: Collection<KElement>): Boolean {
        throw NotImplementedError()
    }

    operator fun get(index: Int): KElement {
        return KElement(CachedKElementLocator(locator, index, locator.by), driver)
    }
}