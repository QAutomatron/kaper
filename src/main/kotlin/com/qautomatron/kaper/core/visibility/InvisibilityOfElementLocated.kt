package com.qautomatron.kaper.core.visibility

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition

/**
 * Will check invisibility of element By locator
 */
class InvisibilityOfElementLocated(private val locator: By) : ExpectedCondition<Boolean> {

    override fun apply(driver: WebDriver?): Boolean? {
        return try {
            val elements = driver!!.findElements<WebElement>(locator)
            elements.isEmpty() || !elements[0].isDisplayed
        } catch (e: StaleElementReferenceException) {
            true
        } catch (e: NoSuchElementException) {
            true
        } catch (e: ElementNotVisibleException) {
            true
        } catch (e: NullPointerException) {
            true
        } catch (t: Throwable) {
            throw Error(t)
        }

    }
}
