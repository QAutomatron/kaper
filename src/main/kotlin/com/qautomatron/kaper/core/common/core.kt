package com.qautomatron.kaper.core.common

import com.qautomatron.kaper.core.config.Configuration
import org.aeonbits.owner.ConfigFactory

val config: Configuration = ConfigFactory.create(
        Configuration::class.java,
        System.getProperties(),
        System.getenv())

val defaultPollingTime: Long = config.polling()
val defaultImplicitlyWait = config.implicitlyWait()
val defaultWait = config.wait()