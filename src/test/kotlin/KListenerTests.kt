import org.testng.Assert.assertTrue
import org.testng.annotations.Test

class KListenerTests {

    private val testTime: Long = 2000

    @Test(description = "Passed test")
    fun passed() {
        println("This is PASSED test")
        Thread.sleep(testTime)
    }

    @Test()
    fun failed() {
        println("This would be FAILED test")
        Thread.sleep(testTime)
        assertTrue(false)
    }

    @Test(enabled = false)
    fun skipped() {
        println("This would be SKIPPED test")
        Thread.sleep(testTime)
    }
}