package com.qautomatron.kaper.core.element

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class MElements(locator: ElementLocator<List<WebElement>>,
                driver: WebDriver) :
        Element<List<WebElement>>(locator, driver), Collection<MElement> {

    constructor(locator: By, driver: WebDriver)
            : this(MElementListLocator(locator, driver), driver)

    val webElements: List<WebElement>
        get() = locator.find()

    override val size: Int
        get() = webElements.size

    override fun isEmpty(): Boolean {
        return webElements.isEmpty()
    }

    override fun iterator(): Iterator<MElement> = object : Iterator<MElement> {
        var index = 0

        override fun hasNext(): Boolean {
            return webElements.size > this.index
        }

        override fun next(): MElement {
            val indexedElement = get(index)
            this.index += 1
            return indexedElement
        }
    }

    override fun toString(): String {
        return locator.description
    }

    override fun contains(element: MElement): Boolean {
        throw NotImplementedError()
    }

    override fun containsAll(elements: Collection<MElement>): Boolean {
        throw NotImplementedError()
    }

    operator fun get(index: Int): MElement {
        return MElement(CachedMElementLocator(locator, index, locator.by), driver)
    }
}