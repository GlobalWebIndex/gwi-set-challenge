package com.gwi.automation.runners;

import static com.gwi.automation.ui.GlobalVars.DRIVER;
import static com.gwi.automation.ui.GlobalVars.SESSION_DATA;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterMethod;

@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json","html:target/report.html",
        "rerun:build/rerun.txt"},
    features = "src/test/resources/features/charts.feature",
    glue = "com/gwi/automation",
    tags = "@UI")
public class ChartsManagementRunner extends AbstractTestNGCucumberTests {

  public ChartsManagementRunner(){super();}

  @AfterMethod
  public void clean() {
    DRIVER.remove();
    SESSION_DATA.remove();
  }
}
