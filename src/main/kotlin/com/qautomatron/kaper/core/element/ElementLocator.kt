package com.qautomatron.kaper.core.element

import org.openqa.selenium.By

interface ElementLocator<out T> {

    fun find(): T

    val description: String

    val by: By
}