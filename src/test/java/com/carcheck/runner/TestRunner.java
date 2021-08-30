package com.carcheck.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/FreeCarCheck.feature"},
        glue = {"com.carcheck.stepDefs"},
        plugin = {"pretty", "html:target/reports/test-output.html"})

public class TestRunner {

}

