package com.qautomatron.kaper.core.common

import io.appium.java_client.MobileBy
import org.openqa.selenium.By

fun id(id: String) : By = MobileBy.id(id)
fun xp(xpath: String) : By = MobileBy.xpath(xpath)
fun cn(className: String) : By = MobileBy.className(className)
fun ai(accessibilityId: String) : By = MobileBy.AccessibilityId(accessibilityId)