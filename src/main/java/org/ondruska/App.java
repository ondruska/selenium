package org.ondruska;

import java.io.File;
import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import lombok.val;
import lombok.extern.java.Log;

@Log
public class App {

    private static final String REMOTE_DRIVER_URL = "http://localhost:4444";

    @NonNull
    private static final FirefoxOptions OPTIONS = new FirefoxOptions().addArguments("-headless");

    public static void main() {
        remote();
    }

    private static void remote() {
        val driver = RemoteWebDriver.builder()
                .address(REMOTE_DRIVER_URL)
                .build();
        driver.get("https://www.kaibo.cz");
        log.info(driver.getTitle());
        driver.quit();
    }

    @SuppressWarnings("unused")
    private static void local() {
        val driver = getFirefoxDriver();
        driver.get("https://www.kaibo.cz");
        log.info(driver.getTitle());
        driver.quit();
    }

    private static FirefoxDriver getFirefoxDriver() {
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
}
