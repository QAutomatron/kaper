package com.qautomatron.kaper.core.common

import com.qautomatron.kaper.core.config.Configuration
import com.qautomatron.kaper.core.driver.getDriver
import org.aeonbits.owner.ConfigFactory
import org.openqa.selenium.OutputType

val config: Configuration = ConfigFactory.create(
        Configuration::class.java,
        System.getProperties(),
        System.getenv())

val defaultPollingTime: Long = config.polling()
val defaultImplicitlyWait = config.implicitlyWait()
val defaultWait = config.wait()