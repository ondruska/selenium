package org.ondruska;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

class AppTest {

    WebDriver driver;

    private FirefoxDriver getFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        GeckoDriverService service = null;
        File snapGeckoDriver = new File("/snap/bin/geckodriver") {
            // https://github.com/SeleniumHQ/selenium/issues/7788
            @Override
            public String getCanonicalPath() throws IOException {
                return getAbsolutePath();
            }
        };
        if (snapGeckoDriver.exists()) {
            service = new GeckoDriverService.Builder().usingDriverExecutable(snapGeckoDriver).build();
        }
        return service != null ? new FirefoxDriver(service, options) : new FirefoxDriver(options);
    }

    @BeforeEach
    void setup() {
        driver = getFirefoxDriver();
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
