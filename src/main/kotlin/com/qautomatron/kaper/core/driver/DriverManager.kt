package com.qautomatron.kaper.core.driver

import com.qautomatron.kaper.core.common.config
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.remote.MobileCapabilityType
import mu.KLogging
import mu.KotlinLogging
import org.openqa.selenium.Capabilities
import org.openqa.selenium.OutputType
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.util.concurrent.ConcurrentHashMap

class DriverManager {

    private val container: MutableMap<Long, AppiumDriver<MobileElement>> = ConcurrentHashMap(4)

    private fun setDriver(webDriver: AppiumDriver<MobileElement>): AppiumDriver<MobileElement> {
        container.put(Thread.currentThread().id, webDriver)
        return webDriver
    }

    fun getDriver(): AppiumDriver<MobileElement> {
        val driver = container[Thread.currentThread().id]
        if (driver != null) {
            return driver
        }
        val newDriver = createAppiumDriver()
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

    private fun createAppiumDriver(): AppiumDriver<MobileElement> {
        val appiumHub = config.hub()
        return AppiumDriver(URL(appiumHub), getCapabilities())
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

        val capabilities = DesiredCapabilities()
        if (!deviceName.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName)
        if (!platformVersion.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion)
        if (!version.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.VERSION, version)
        if (!platform.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.PLATFORM, platform)
        if (!platformName.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName)
        if (!browserName.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName)
        if (!app.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.APP, app)
        if (!automationName.isNullOrEmpty()) capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName)
        if (fullReset != null && fullReset) capabilities.setCapability(MobileCapabilityType.FULL_RESET, fullReset)

        if (!xcodeSigningId.isNullOrEmpty()) capabilities.setCapability("xcodeSigningId", xcodeSigningId)
        if (!xcodeOrgId.isNullOrEmpty()) capabilities.setCapability("xcodeOrgId", xcodeOrgId)
        if (!appWaitActivity.isNullOrEmpty()) capabilities.setCapability("appWaitActivity", appWaitActivity)
        return capabilities
    }
}

val driverManager = DriverManager()
private val logger = KotlinLogging.logger {}

fun getDriver(): AppiumDriver<MobileElement> {
    return driverManager.getDriver()
}

fun closeSession() {
    logger.debug {"Will close debug session for thread ${Thread.currentThread().id}"}
    return driverManager.quitDriver()
}

fun isDriverExist(): Boolean {
    return driverManager.isDriverExist()
}

fun getScreenshot(): ByteArray {
    return getDriver().getScreenshotAs(OutputType.BYTES)
}