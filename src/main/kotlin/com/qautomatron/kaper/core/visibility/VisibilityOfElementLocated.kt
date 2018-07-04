package com.qautomatron.kaper.core.visibility

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition

/**
 * Will check element visibility By locator
 */
class VisibilityOfElementLocated(private val locator: By) : ExpectedCondition<Boolean> {

    override fun apply(driver: WebDriver?): Boolean? {
        return try {
            val elements = driver?.findElements<WebElement>(locator)
            if (elements != null) {
                !elements.isEmpty() && elements[0].isDisplayed
            } else {
                false
            }
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
