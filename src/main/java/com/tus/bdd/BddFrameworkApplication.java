package com.tus.bdd;

import com.tus.bdd.steps.CustomAbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.testng.TestNG;

@ComponentScan(basePackages = {"com.tus.bdd"})
@SpringBootTest(classes = BddFrameworkApplication.class)
@CucumberOptions(
        monochrome = true,
        features = "src/main/resources/features",
        extraGlue = "com.tus.bdd.steps",
        plugin = {"pretty","html:out/cucumber.report.html"})
@EnableAutoConfiguration
public class BddFrameworkApplication extends CustomAbstractTestNGCucumberTests {

    public static void main(String[] args) {
        TestNG testNgRuntime = new TestNG();
        testNgRuntime.setDefaultSuiteName("FPS_BDD");
        testNgRuntime.setTestClasses(new Class[]{BddFrameworkApplication.class});
        testNgRuntime.run();

    }
}
