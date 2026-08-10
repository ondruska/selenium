package org.ondruska;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import lombok.val;

class AppTest {

    private static final String REMOTE_DRIVER_URL = "http://localhost:4444";

    @NonNull
    private static final FirefoxOptions OPTIONS = new FirefoxOptions().addArguments("-headless");

    WebDriver driver;

    @SuppressWarnings("unused")
    private WebDriver getLocalFirefoxDriver() {

        GeckoDriverService service = null;
        val snapGeckoDriver = new File("/snap/bin/geckodriver") {
            // https://github.com/SeleniumHQ/selenium/issues/7788
            @Override
            public String getCanonicalPath() throws IOException {
                return getAbsolutePath();
            }
        };
        if (snapGeckoDriver.exists()) {
            service = new GeckoDriverService.Builder().usingDriverExecutable(snapGeckoDriver).build();
        }
        return service != null ? new FirefoxDriver(service, OPTIONS) : new FirefoxDriver(OPTIONS);
    }

    private WebDriver getDriver() {
        return RemoteWebDriver.builder()
                .address(REMOTE_DRIVER_URL)
                .addAlternative(OPTIONS)
                .build();
    }

    @BeforeEach
    void setup() {
        driver = getDriver();
    }

    @AfterEach
    void finish() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void title() {
        driver.get("https://www.kaibo.cz");
        assertEquals("kaibo --- pomáhá organizacím s problémy a rozvojem informačních technologií", driver.getTitle());
    }
}
