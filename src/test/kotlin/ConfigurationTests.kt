
import org.testng.annotations.Test
import assertk.assert
import assertk.assertions.*
import com.qautomatron.kaper.core.common.config

class ConfigurationTests {

    @Test
    fun testDefaultIntValue() {
        assert(config.implicitlyWait()).isEqualTo(0)
    }

    @Test
    fun testNullStringValue() {
        assert(config.app()).isNull()
    }

    @Test
    fun testStringValue() {
        assert(config.platformName()).isEqualTo("platformName")
    }

    @Test
    fun testDefaultBooleanValue() {
        assert(config.uSession()).isEqualTo(true)
    }
}