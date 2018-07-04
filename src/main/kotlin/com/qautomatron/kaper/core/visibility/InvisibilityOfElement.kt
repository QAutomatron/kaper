package com.qautomatron.kaper.core.visibility

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition

/**
 * Will check invisibility of web element
 */
class InvisibilityOfElement(private val element: WebElement) : ExpectedCondition<Boolean> {

    override fun apply(d: WebDriver?): Boolean? {
        return try {
            !element.isDisplayed
        } catch (e: StaleElementReferenceException) {
            true
        } catch (e: NoSuchElementException) {
            true
        } catch (e: ElementNotVisibleException) {
            true
        } catch (t: Throwable) {
            throw Error(t)
        }
    }
}
