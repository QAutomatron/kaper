package com.qautomatron.kaper.steps

import com.qautomatron.kaper.core.data.Direction

/**
 * Steps for IOS screen interactions
 */
class IOSScreenSteps: Steps() {

    /**
     * Swipe to direction
     * @param direction direction of swipe
     */
    fun swipe(direction: Direction) {
        val params = hashMapOf(
                "direction" to direction.name.toLowerCase())
        js?.executeScript("mobile: swipe", params)
    }
}