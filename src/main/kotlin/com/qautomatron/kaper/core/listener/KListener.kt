package com.qautomatron.kaper.core.listener

import com.qautomatron.kaper.core.common.config
import com.qautomatron.kaper.core.driver.closeSession
import org.testng.*
import java.util.concurrent.TimeUnit

/**
 * Listener for TestNG framework
 */
open class KListener : ISuiteListener, IInvokedMethodListener {

    private val singleLine = "----------------------------------------"
    private val doubleLine = "========================================"

    // This belongs to ISuiteListener and will execute before the Suite start
    override fun onStart(suite: ISuite) {
        report(doubleLine)
        report("Test Suite STARTED - ${suite.name}")
    }

    // This belongs to ISuiteListener and will execute, once the Suite is finished
    override fun onFinish(suite: ISuite) {
        report("Test Suite FINISHED - ${suite.name}")
        report(doubleLine)
        closeSession()
    }

    // This is the method which will be executed in case of test pass or fail
    // This will provide the information on the test
    private fun printTestResults(result: ITestResult) {

//        Reporter.log("Test Method resides in " + result.testClass.name, true)

        if (result.parameters.isNotEmpty()) {
            var params = ""
            for (parameter in result.parameters) {
                params += parameter.toString() + ","
            }
            Reporter.log("Test Method had the following parameters : " + params, true)
        }

        val status = when (result.status) {
            ITestResult.SUCCESS -> "PASS"
            ITestResult.FAILURE -> "FAILED"
            ITestResult.SKIP -> "SKIPPED"
            else -> "UNKNOWN"
        }

        val timeInSecond = TimeUnit.MILLISECONDS.toSeconds(result.endMillis - result.startMillis)
        Reporter.log("Test method result: $status | Completed in $timeInSecond sec", true)
    }

    // This belongs to IInvokedMethodListener and will execute before every method including @Before @After @Test
    override fun beforeInvocation(method: IInvokedMethod, testResult: ITestResult) {
        if (method.isTestMethod) {
            val textMsg = "Test method STARTED - ${returnMethodName(method.testMethod)}"
            report(singleLine)
            report(textMsg)
        }
    }

    // This belongs to IInvokedMethodListener and will execute after every method including @Before @After @Test
    override fun afterInvocation(method: IInvokedMethod, testResult: ITestResult) {
        if (method.isTestMethod) {
            val textMsg = "Test method FINISHED - ${returnMethodName(method.testMethod)}"
            report(textMsg)
            printTestResults(testResult)
            report(singleLine)

            val testStatus = testResult.status
            if (testStatus == ITestResult.FAILURE) onTestFailure()
            if (testStatus == ITestResult.SUCCESS) onTestSuccess()

            if (config.uSession()) {
                closeSession()
            }
        }
    }

    open fun onTestFailure() {}

    open fun onTestSuccess() {}

    // This will return method names to the calling function
    private fun returnMethodName(method: ITestNGMethod): String {
        return method.realClass.simpleName + "." + method.methodName
    }

    private fun report(msg: String) {
        Reporter.log(msg, true)
    }
}