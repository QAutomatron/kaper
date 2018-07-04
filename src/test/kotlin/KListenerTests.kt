import org.testng.annotations.Test

class KListenerTests {

    private val testTime: Long = 2000

    @Test(description = "Passed test")
    fun passed() {
        println("This is PASSED test")
        Thread.sleep(testTime)
    }

    @Test(enabled = false)
    fun skipped() {
        println("This would be SKIPPED test")
        Thread.sleep(testTime)
    }
}