package com.qautomatron.kaper.core.element

import com.qautomatron.kaper.core.common.defaultPollingTime
import com.qautomatron.kaper.core.common.defaultWait
import com.qautomatron.kaper.core.data.Direction
import com.qautomatron.kaper.core.driver.resetImplicitTimeout
import com.qautomatron.kaper.core.driver.setImplicitTimeout
import com.qautomatron.kaper.core.visibility.*
import com.qautomatron.kaper.core.visibility.Condition.*
import io.appium.java_client.MobileElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

abstract class Element<out T>(protected val locator: ElementLocator<T>,
                              protected val driver: WebDriver) {

    private val js = driver as JavascriptExecutor

    @JvmOverloads
    fun waitFor(condition: Condition, timeout: Int = defaultWait): Boolean {

        val method = when (condition) {
            VISIBLE -> VisibilityOfElementLocated(locator.by)
            INVISIBLE -> InvisibilityOfElementLocated(locator.by)
            PRESENCE -> PresenceOfElementLocated(locator.by)
            ABSENCE -> AbsenceOfElementLocated(locator.by)
        }

        driver.setImplicitTimeout()
        val wait = WebDriverWait(driver, timeout.toLong(), defaultPollingTime)
        return try {
            wait.until(method)
        } catch (e: TimeoutException) {
            false
        } catch (t: Throwable) {
            throw Error(t)
        } finally {
            driver.resetImplicitTimeout()
        }
    }

    @JvmOverloads
    fun waitForVisibility(timeout: Int = defaultWait): Boolean = waitFor(visible, timeout)

    @JvmOverloads
    fun waitForInvisibility(timeout: Int = defaultWait): Boolean = waitFor(invisible, timeout)

    @JvmOverloads
    fun waitForPresence(timeout: Int = defaultWait): Boolean = waitFor(presence, timeout)

    @JvmOverloads
    fun waitForAbsence(timeout: Int = defaultWait): Boolean = waitFor(absence, timeout)

    /**
     * Scroll in element to child by it accessibility id
     */
    fun scrollTo(ai: String) {
        val params = hashMapOf(
                "element" to driver.findElement<MobileElement>(locator.by).id,
                "name" to ai)
        js.executeScript("mobile: scroll", params)
    }

    /**
     * Swipe on element in specific direction
     */
    fun swipe(direction: Direction) {
        val params = hashMapOf(
                "element" to driver.findElement<MobileElement>(locator.by).id,
                "direction" to direction.name.toLowerCase())
        js.executeScript("mobile: swipe", params)
    }
}