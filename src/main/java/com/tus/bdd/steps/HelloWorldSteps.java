package com.tus.bdd.steps;

import io.cucumber.java.en.Given;

public class HelloWorldSteps  {
    @Given("Say Hello")
    public void sayHello() {
        System.out.println("Hello Tushar...!!!!!!!" );
    }


}
