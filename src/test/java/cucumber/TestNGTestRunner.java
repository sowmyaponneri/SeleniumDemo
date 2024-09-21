package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/cucumber",glue="automation.stepdefinitions",
monochrome=true,plugin= {"html:target/cucumber.html"})

//monochrome - report in the readable format, plugin - generate report in which format and location to save
public class TestNGTestRunner extends AbstractTestNGCucumberTests{
	
}