package com.qautomatron.kaper.core.driver

import com.qautomatron.kaper.core.common.config
import com.qautomatron.kaper.core.common.defaultImplicitlyWait
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit

@JvmOverloads
fun WebDriver.setImplicitTimeout(value: Int = 0, timeUnit: TimeUnit = TimeUnit.SECONDS) {
    if (defaultImplicitlyWait > 0) this.manage().timeouts().implicitlyWait(value.toLong(), timeUnit)
}

fun WebDriver.resetImplicitTimeout() { this.setImplicitTimeout(defaultImplicitlyWait) }

@JvmOverloads // Works only if 1 test = 1 thread (forkEvery > 1)
fun WebDriver.autoClose(enabled: Boolean = config.autoClose()): WebDriver {
    if (enabled) {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                closeSession()
            }
        })
    }
    return this
}