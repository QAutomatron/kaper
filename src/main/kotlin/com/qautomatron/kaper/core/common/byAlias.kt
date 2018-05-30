package com.qautomatron.kaper.core.common

import io.appium.java_client.MobileBy
import org.openqa.selenium.By

// General
fun id(id: String): By = By.id(id)
fun xp(xpath: String): By = By.xpath(xpath)
fun css(css: String): By = By.cssSelector(css)
fun cn(className: String): By = By.className(className)
fun lt(linkText: String): By = MobileBy.linkText(linkText)

// iOS only
fun ai(accessibilityId: String): By = MobileBy.AccessibilityId(accessibilityId)
fun pr(predicate: String): By = MobileBy.iOSNsPredicateString(predicate)
fun cc(chain: String): By = MobileBy.iOSClassChain(chain)

// Android
fun aa(automator: String): By = MobileBy.AndroidUIAutomator(automator)