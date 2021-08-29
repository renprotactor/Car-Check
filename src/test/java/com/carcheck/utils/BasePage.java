package com.carcheck.utils;


import org.openqa.selenium.WebDriver;

public class BasePage {


    protected WebDriver driver;

    public BasePage() {
    this.driver=BrowserUtil.getDriver();
    }


}

