package com.piyush.blockchain.votemachine.controller;



import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/steps" , format = {"pretty"} )
public class TestBDDController extends ControllerTest  {




}
