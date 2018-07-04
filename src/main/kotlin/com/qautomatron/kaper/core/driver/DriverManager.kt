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

/**
 * Driver manager - will create, remove driver
 */
class DriverManager {

    private val container: MutableMap<Long, WebDriver> = ConcurrentHashMap(4)

    private fun setDriver(webDriver: WebDriver): WebDriver {
        container[Thread.currentThread().id] = webDriver
        return webDriver
    }

    /**
     * Return existed driver or create a new one
     */
    fun getDriver(): WebDriver {
        val driver = container[Thread.currentThread().id]
        if (driver != null) {
            return driver
        }
        val commonCapabilities = getCommonCapabilities()
        val newDriver = if (!arrayOf(Platform.IOS, Platform.ANDROID).contains(commonCapabilities.platform)) {
            createWebDriver(commonCapabilities)
        } else createAppiumDriver(commonCapabilities.merge(getMobileCapabilities()))
        newDriver.autoClose()
        return setDriver(newDriver)
    }

    /**
     * Quit current driver and remove it from thread
     */
    fun quitDriver() {
        container[Thread.currentThread().id]?.quit()
        container.remove(Thread.currentThread().id)
    }

    /**
     * Check if driver is exist
     */
    fun isDriverExist(): Boolean {
        return container[Thread.currentThread().id] != null
    }

    /**
     * Will create WebDriver based on browser type or Remote if grid is specified
     */
    private fun createWebDriver(capabilities: Capabilities): WebDriver {
        val grid = config.hub()
        return if (!config.hub().isNullOrEmpty()) RemoteWebDriver(URL(grid), capabilities)
        else when (capabilities.browserName) {
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
            else -> throw Error("BrowserType is not set")
        }
    }

    /**
     * Will create Appium driver for specified platform
     */
    private fun createAppiumDriver(capabilities: Capabilities): AppiumDriver<MobileElement> {
        val appiumHub = config.hub()
        return when (capabilities.platform) {
            Platform.ANDROID -> AndroidDriver(URL(appiumHub), capabilities.merge(getAndroidCapabilities()))
            Platform.IOS -> IOSDriver(URL(appiumHub), capabilities.merge(getIOSCapabilities()))
            else -> throw Error("Mobile Platform is not set")
        }
    }

    /**
     * Load capabilities from config
     */
    private fun getCommonCapabilities(): Capabilities {
        val platform = config.platform()
        val version = config.version()
        val platformName = config.platformName()
        val browserName = config.browserName()

        val capabilities = DesiredCapabilities()

        // General Caps
        if (!browserName.isNullOrEmpty()) capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName)
        if (!platformName.isNullOrEmpty()) capabilities.setCapability(CapabilityType.PLATFORM_NAME, platformName)
        if (!version.isNullOrEmpty()) capabilities.setCapability(CapabilityType.VERSION, version)
        if (!platform.isNullOrEmpty()) capabilities.setCapability(CapabilityType.PLATFORM, platform)

        return capabilities
    }

    private fun getMobileCapabilities(): Capabilities {
        val deviceName = config.deviceName()
        val platformVersion = config.platformVersion()
        val app = config.app()
        val automationName = config.automationName()
        val fullReset = config.fullReset()
        val noReset = config.noReset()
        val capabilities = DesiredCapabilities()

        // Mobile Caps
        if (!deviceName.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName)
        if (!platformVersion.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion)
        if (!app.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.APP, app)
        if (!automationName.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName)
        if (fullReset != null) capabilities.setCapability(MobileCapabilityType.FULL_RESET, fullReset)
        if (noReset != null) capabilities.setCapability(MobileCapabilityType.NO_RESET, noReset)

        return capabilities
    }

    private fun getIOSCapabilities(): DesiredCapabilities {
        val xcodeSigningId = config.xcodeSigningId()
        val xcodeOrgId = config.xcodeOrgId()
        val updatedWDABundleId = config.updatedWDABundleId()
        val bundleId = config.bundleId()
        val capabilities = DesiredCapabilities()

        if (!xcodeSigningId.isNullOrEmpty()) capabilities.setCapability("xcodeSigningId", xcodeSigningId)
        if (!xcodeOrgId.isNullOrEmpty()) capabilities.setCapability("xcodeOrgId", xcodeOrgId)
        if (!updatedWDABundleId.isNullOrEmpty()) capabilities.setCapability("updatedWDABundleId", updatedWDABundleId)
        if (!bundleId.isNullOrEmpty()) capabilities.setCapability("bundleId", bundleId)

        return capabilities
    }

    private fun getAndroidCapabilities(): DesiredCapabilities {
        val appWaitActivity = config.appWaitActivity()
        val noSign = config.noSign()
        val capabilities = DesiredCapabilities()

        if (!appWaitActivity.isNullOrEmpty()) capabilities.setCapability("appWaitActivity", appWaitActivity)
        if (noSign != null) capabilities.setCapability("noSign", noSign)

        return capabilities
    }
}

val driverManager = DriverManager()
private val logger = KotlinLogging.logger {}

/**
 * Get driver instance within project
 */
fun getDriver(): WebDriver {
    return driverManager.getDriver()
}

/**
 * Close session within project
 */
fun closeSession() {
    logger.debug { "Will close session for thread ${Thread.currentThread().id}" }
    return driverManager.quitDriver()
}

/**
 * Driver exist check alias
 */
fun isDriverExist(): Boolean {
    return driverManager.isDriverExist()
}

/**
 * Get screenshot as bytes
 */
fun getScreenshot(): ByteArray {
    return (getDriver() as RemoteWebDriver).getScreenshotAs(OutputType.BYTES)
}