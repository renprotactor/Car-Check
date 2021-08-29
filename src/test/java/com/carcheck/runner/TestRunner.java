package com.carcheck.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
    @CucumberOptions(features = {"classpath:features/FreeCarCheck.feature"},
            glue = {"com.carcheck.stepDefs"},
       plugin = {"pretty", "html:src/test/java/com/carcheck/reports/test-output.html", "json:src/test/java/com/carcheck/reports/cucumber.json"})
//            plugin = {"io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm"})

    public class TestRunner {

    }

