package web.steps

import com.qautomatron.kaper.core.common.id
import com.qautomatron.kaper.steps.Steps
import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue

class IndexPageSteps : Steps() {

    private val input = kEl(id("text"))

    fun open() {
        driver.get("https://ya.ru")
    }

    fun inputShouldBePresent() {
        assertTrue(input.waitForVisibility(), "Input field should be present")
    }

    fun setSearch(value: String) {
        input.sendKeys(value)
    }

    fun inputValueShouldBeSameAs(value: String) {
        assertEquals(input.getAttribute("value"), value)
    }
}