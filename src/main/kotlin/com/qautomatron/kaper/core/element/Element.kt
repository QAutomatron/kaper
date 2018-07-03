package com.qautomatron.kaper.core.element

import com.qautomatron.kaper.core.common.defaultPollingTime
import com.qautomatron.kaper.core.common.defaultWait
import com.qautomatron.kaper.core.driver.resetImplicitTimeout
import com.qautomatron.kaper.core.driver.setImplicitTimeout
import com.qautomatron.kaper.core.visibility.*
import com.qautomatron.kaper.core.visibility.Condition.*
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

abstract class Element<out T>(protected val locator: ElementLocator<T>,
                              protected val driver: WebDriver) {

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
}