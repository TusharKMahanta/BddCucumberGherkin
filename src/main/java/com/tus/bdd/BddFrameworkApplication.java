package com.tus.bdd;

import io.cucumber.core.cli.Main;

public class BddFrameworkApplication  {

    public static void main(String[] args) {

        String[] cucumberOptions= new String[]{
                "--glue","com.tus.bdd",
                "classpath:features",
                "--plugin","pretty",
                "--plugin","html:out/cucumber.report.html"
        };
        Main.run(cucumberOptions,Thread.currentThread().getContextClassLoader());
    }
}
