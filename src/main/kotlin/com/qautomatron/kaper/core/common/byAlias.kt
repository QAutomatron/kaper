package com.qautomatron.kaper.core.common

import io.appium.java_client.MobileBy
import org.openqa.selenium.By

fun id(id: String): By = By.id(id)
fun xp(xpath: String): By = By.xpath(xpath)
fun css(css: String): By = By.cssSelector(css)
fun cn(className: String): By = By.className(className)
fun ai(accessibilityId: String): By = MobileBy.AccessibilityId(accessibilityId)