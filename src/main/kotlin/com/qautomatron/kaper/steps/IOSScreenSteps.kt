package com.qautomatron.kaper.steps

import com.qautomatron.kaper.core.data.Direction

class IOSScreenSteps: Steps() {

    fun swipe(direction: Direction) {
        val params = hashMapOf(
                "direction" to direction.name.toLowerCase())
        js.executeScript("mobile: swipe", params)
    }
}