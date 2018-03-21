package com.qautomatron.kaper.core.driver

import com.qautomatron.kaper.core.common.config
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.remote.MobileCapabilityType
import mu.KotlinLogging
import org.openqa.selenium.Capabilities
import org.openqa.selenium.OutputType
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.BrowserType
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL
import java.util.concurrent.ConcurrentHashMap

class DriverManager {

    private val container: MutableMap<Long, WebDriver> = ConcurrentHashMap(4)

    private fun setDriver(webDriver: WebDriver): WebDriver {
        container[Thread.currentThread().id] = webDriver
        return webDriver
    }

    fun getDriver(): WebDriver {
        val driver = container[Thread.currentThread().id]
        if (driver != null) {
            return driver
        }
        val capabilities = getCapabilities()
        val newDriver = if (capabilities.platform != Platform.IOS || capabilities.platform != Platform.ANDROID) {
            createWebDriver(capabilities)
        }
        else createAppiumDriver(capabilities)
        newDriver.autoClose()
        return setDriver(newDriver)
    }

    fun quitDriver() {
        container[Thread.currentThread().id]?.quit()
        container.remove(Thread.currentThread().id)
    }

    fun isDriverExist(): Boolean {
        return container[Thread.currentThread().id] != null
    }

    private fun createWebDriver(capabilities: Capabilities): WebDriver {
        val grid = config.hub()
        if (!config.hub().isNullOrEmpty()) return RemoteWebDriver(URL(grid), capabilities)
        else return when (capabilities.browserName) {
            BrowserType.CHROME -> {
                val chromeOptions = ChromeOptions()
                chromeOptions.merge(capabilities)
                ChromeDriver(chromeOptions)
            }
            BrowserType.FIREFOX -> {
                val firefoxOptions = FirefoxOptions()
                firefoxOptions.merge(capabilities)
                FirefoxDriver(firefoxOptions)
            }
            else -> {
                throw Error("BrowserType is not set")
            }
        }
    }

    private fun createAppiumDriver(capabilities: Capabilities): AppiumDriver<MobileElement> {
        val appiumHub = config.hub()
        return when (capabilities.platform) {
            Platform.ANDROID -> AndroidDriver(URL(appiumHub), capabilities)
            Platform.IOS -> IOSDriver(URL(appiumHub), capabilities)
            else -> {
                AppiumDriver(URL(appiumHub), capabilities)
            }
        }
    }

    private fun getCapabilities() : Capabilities {
        val deviceName = config.deviceName()
        val platform = config.platform()
        val version = config.version()
        val platformVersion = config.platformVersion()
        val platformName = config.platformName()
        val browserName = config.browserName()
        val app = config.app()
        val automationName = config.automationName()
        val appWaitActivity = config.appWaitActivity()
        val fullReset = config.fullReset()
        val xcodeSigningId = config.xcodeSigningId()
        val xcodeOrgId = config.xcodeOrgId()
        val noSign = config.noSign()

        val capabilities = DesiredCapabilities()
        // General Caps
        if (!browserName.isNullOrEmpty()) capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName)
        if (!platformName.isNullOrEmpty()) capabilities.setCapability(CapabilityType.PLATFORM_NAME, platformName)
        if (!version.isNullOrEmpty()) capabilities.setCapability(CapabilityType.VERSION, version)
        if (!platform.isNullOrEmpty()) capabilities.setCapability(CapabilityType.PLATFORM, platform)

        // Mobile Caps
        if (!deviceName.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName)
        if (!platformVersion.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion)
        if (!app.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.APP, app)
        if (!automationName.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName)
        if (fullReset != null && fullReset) capabilities.setCapability(MobileCapabilityType.FULL_RESET, fullReset)

        if (!xcodeSigningId.isNullOrEmpty()) capabilities.setCapability("xcodeSigningId", xcodeSigningId)
        if (!xcodeOrgId.isNullOrEmpty()) capabilities.setCapability("xcodeOrgId", xcodeOrgId)
        if (!appWaitActivity.isNullOrEmpty()) capabilities.setCapability("appWaitActivity", appWaitActivity)
        if (noSign != null && noSign) capabilities.setCapability("noSign", noSign)
        return capabilities
    }
}

val driverManager = DriverManager()
private val logger = KotlinLogging.logger {}

fun getDriver(): WebDriver {
    return driverManager.getDriver()
}

fun closeSession() {
    logger.debug {"Will close session for thread ${Thread.currentThread().id}"}
    return driverManager.quitDriver()
}

fun isDriverExist(): Boolean {
    return driverManager.isDriverExist()
}

fun getScreenshot(): ByteArray {
    return (getDriver() as RemoteWebDriver).getScreenshotAs(OutputType.BYTES)
}