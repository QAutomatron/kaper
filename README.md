# Kaper [![Build Status](https://travis-ci.org/QAutomatron/kaper.svg?branch=master)](https://travis-ci.org/QAutomatron/kaper) [ ![Download](https://api.bintray.com/packages/qautomatron/kaper/kaper/images/download.svg) ](https://bintray.com/qautomatron/kaper/kaper/_latestVersion)

Test automation framework for Selenium/Appium written in Kotlin

## Basics

Base structure is: Test - Steps - Elements

All step classes should be inherited from `Step()`

All steps in test should be init `by lazy {}`: `private val mySteps by lazy { MySteps() }`

First call to any step in test will invoke `WebDriver` or `AppiumDriver` creation. One Driver per thread

Every element in steps should be wrapped in `kEl(By)`

Could use TestNG listener `KListener`

Could use AspectJ step logger `com.qautomatron.kaper.core.listener.StepLogger`

### Installation (Gradle)

1. Add repository to your project:
```groovy
repositories {
    mavenCentral()
    jcenter()

    maven {
        url  "https://dl.bintray.com/qautomatron/kaper"
    }
}
```

2. Add dependency:
```groovy
dependencies {
    compile 'com.qautomatron:kaper:0.0.16'
}
``` 

### How to use

1. Create step classes inherited from `Steps()`
2. Add some elements to you new step class:
```kotlin
    private val btnYes = kEl(ai("Yes")) // ai is alias for AccessibilityId
    private val popup = kEl(ai("SomePopup"))
```
3. Add some methods to interact with elements:
```kotlin
    fun tapYes() {
        btnYes.click()
    }
```
4. Or Add asserts:
```kotlin
    fun popupShouldBePresent() {
        assertTrue("Popup should be visible",
            popup.waitForVisibility())
    }
```
5. Write test:
```kotlin
class SampleTest {

    private val mySteps by lazy { MySteps() }

    fun example_test() {

        // Tap Yes
        mySteps.tapYes()

        // Check Popup visible
        mySteps.popupShouldBePresent()
    }
}
```

## Contributing

Will add info

## Authors

* **Evgenii S** - *Initial work* - [QAutomatron](https://github.com/QAutomatron)

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Inspired by: 
