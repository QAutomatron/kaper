package web

import com.qautomatron.kaper.core.driver.closeSession
import org.testng.annotations.AfterTest
import org.testng.annotations.Test
import web.steps.IndexPageSteps

class WebDriverTests {

    private val indexSteps by lazy { IndexPageSteps() }

    @Test
    fun elementVisibleTest() {
        indexSteps.open()
        indexSteps.inputShouldBePresent()
    }

    @Test
    fun sendKeysTest() {
        val value = "Search Value"
        indexSteps.open()
        indexSteps.setSearch(value)
        indexSteps.inputValueShouldBeSameAs(value)
    }

    @AfterTest
    fun after() {
        closeSession()
    }
}