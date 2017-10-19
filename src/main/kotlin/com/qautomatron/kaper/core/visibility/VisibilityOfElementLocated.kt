package com.qautomatron.kaper.core.visibility

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition

class VisibilityOfElementLocated(private val locator: By) : ExpectedCondition<Boolean> {

    override fun apply(driver: WebDriver?): Boolean? {
        return try {
            val elements = driver!!.findElements<WebElement>(locator)
            !elements.isEmpty() && elements[0].isDisplayed
        } catch (e: StaleElementReferenceException) {
            false
        } catch (e: NoSuchElementException) {
            false
        } catch (e: ElementNotVisibleException) {
            false
        } catch (e: NullPointerException) {
            false
        } catch (t: Throwable) {
            throw Error(t)
        }
    }
}
