package web

import com.qautomatron.kaper.core.driver.closeSession
import org.testng.annotations.AfterTest
import org.testng.annotations.Test
import web.steps.IndexPageSteps

class WebDriverTests {

    @Test
    fun elementVisibleTest() {
        val indexSteps by lazy { IndexPageSteps() }

        indexSteps.open()
        indexSteps.inputShouldBePresent()
    }

    @Test
    fun sendKeysTest() {
        val indexSteps by lazy { IndexPageSteps() }
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