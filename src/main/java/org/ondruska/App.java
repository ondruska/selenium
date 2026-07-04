package org.ondruska;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import lombok.extern.java.Log;

@Log
public class App {

    public static void main() {
        WebDriver driver = getFirefoxDriver();
        driver.get("https://www.kaibo.cz");
        log.info(driver.getTitle());
        driver.quit();
    }

    private static FirefoxDriver getFirefoxDriver() {
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
}
