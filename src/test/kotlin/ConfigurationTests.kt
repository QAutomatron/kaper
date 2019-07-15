
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.qautomatron.kaper.core.common.config
import org.testng.annotations.Test

class ConfigurationTests {

    @Test
    fun testDefaultIntValue() {
        assertThat(config.implicitlyWait()).isEqualTo(0)
    }

    @Test
    fun testNullStringValue() {
        assertThat(config.app()).isNull()
    }

    @Test
    fun testStringValue() {
        assertThat(config.browserName()).isEqualTo("chrome")
    }

    @Test
    fun testDefaultBooleanValue() {
        assertThat(config.uSession()).isEqualTo(true)
    }
}