package com.tus.bdd;

import io.cucumber.core.cli.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BddFrameworkApplication  {

    public static void main(String[] args) {
        String tag = "all";

        for (int i = 0; i < args.length; i++) {
            if ("--tags".equals(args[i]) && i + 1 < args.length) {
                tag = args[i + 1].replaceAll("[^a-zA-Z0-9]", "");
            }
        }

        String reportPath = "out/cucumber-" + tag + ".html";

        List<String> options = new ArrayList<>(List.of(
                "--glue", "com.tus.bdd",
                "--plugin", "pretty",
                "--plugin", "html:" + reportPath
        ));

        // Forward tags / feature paths from Gradle
        options.addAll(Arrays.asList(args));

        // Default features only if none provided
        if (args.length == 0 || Arrays.stream(args).noneMatch(a -> a.contains("features"))) {
            options.add("classpath:features");
        }

        Main.run(options.toArray(String[]::new),
                Thread.currentThread().getContextClassLoader());
    }
}
