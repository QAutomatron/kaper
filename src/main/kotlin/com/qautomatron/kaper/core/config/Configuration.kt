package com.qautomatron.kaper.core.config

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.*

@Sources("file:\${config.file}.properties",
        "file:kaper.properties")
interface Configuration : Config {

    @Key("driver.hub")
    fun hub(): String?

    @Key("appium.app")
    fun app(): String?

    @Key("driver.platformName")
    fun platformName(): String?

    @Key("appium.platform")
    fun platform(): String?

    @Key("appium.deviceName")
    fun deviceName(): String?

    @Key("driver.browserName")
    fun browserName(): String?

    @Key("appium.platformVersion")
    fun platformVersion(): String?

    @Key("appium.automationName")
    fun automationName(): String?

    @Key("appium.fullReset")
    fun fullReset(): Boolean?

    @Key("appium.noReset")
    fun noReset(): Boolean?

    @Key("appium.noSign")
    fun noSign(): Boolean?

    @Key("driver.implicitlyWait")
    @DefaultValue("0")
    fun implicitlyWait(): Int

    @Key("driver.wait")
    @DefaultValue("15")
    fun wait(): Int

    @Key("driver.uniqueSession")
    @DefaultValue("true")
    fun uSession(): Boolean

    @Key("driver.autoClose")
    @DefaultValue("false")
    fun autoClose(): Boolean

    @Key("appium.version")
    fun version(): String?

    @Key("appium.appWaitActivity")
    fun appWaitActivity(): String?

    @Key("appium.appWaitPackage")
    fun appWaitPackage(): String?

    @Key("appium.xcodeOrgId")
    fun xcodeOrgId(): String?

    @Key("appium.xcodeSigningId")
    fun xcodeSigningId(): String?

    @Key("appium.bundleId")
    fun bundleId(): String?

    @Key("appium.updatedWDABundleId")
    fun updatedWDABundleId(): String?

    @Key("driver.polling")
    @DefaultValue("500")
    fun polling(): Long
}