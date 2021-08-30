package com.carcheck.utils;


import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;

public class BasePage {
    public static WebDriver driver;

    public BasePage() {
        this.driver = BrowserUtil.getDriver();
    }
}

