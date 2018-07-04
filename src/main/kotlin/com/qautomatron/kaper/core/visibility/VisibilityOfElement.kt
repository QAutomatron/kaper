package com.qautomatron.kaper.core.visibility

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition

/**
 * Will check element visibility by web element
 */
class VisibilityOfElement(private val element: WebElement) : ExpectedCondition<Boolean> {

    override fun apply(d: WebDriver?): Boolean? {
        return try {
            element.isDisplayed
        } catch (e: StaleElementReferenceException) {
            false
        } catch (e: NoSuchElementException) {
            false
        } catch (e: ElementNotVisibleException) {
            false
        } catch (t: Throwable) {
            throw Error(t)
        }

    }
}
