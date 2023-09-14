package com.tus.bdd.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class TestSteps {
    @Given("Clear Steps")
    public void clear() {
        System.out.println("Clear Steps !!!!!!!");
    }

    @And("Executing Steps")
    public void execute() {
        System.out.println("Executing Steps !!!!!!!");
    }
}
