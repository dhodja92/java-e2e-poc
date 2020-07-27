package com.github.dhodja92.javateam.e2e.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public final class SeleniumHandler {

    private static class InstanceHolder {
        private static final SeleniumHandler INSTANCE = new SeleniumHandler();
    }

    private final WebDriver driver;

    private SeleniumHandler() {
        WebDriverManager.chromedriver().setup();

        this.driver = new ChromeDriver();
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public static SeleniumHandler instance() {
        return InstanceHolder.INSTANCE;
    }
}
