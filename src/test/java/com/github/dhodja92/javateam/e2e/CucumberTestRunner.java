package com.github.dhodja92.javateam.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "com.github.dhodja92.javateam.e2e.reporting.plugins.StepsListenerPlugin", "html:build/cucumber"},
        glue = "com.github.dhodja92.javateam.e2e.cucumber.steps",
        features = "features/javateam"
)

public class CucumberTestRunner {
}
