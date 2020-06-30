package com.piyush.blockchain.votemachine.controller;


import com.piyush.blockchain.votemachine.controller.ControllerTest;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/steps/machine.feature")
public class TestBDDController extends ControllerTest  {




}
