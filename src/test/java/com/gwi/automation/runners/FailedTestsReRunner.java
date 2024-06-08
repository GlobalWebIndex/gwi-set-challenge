package com.gwi.automation.runners;

import static com.gwi.automation.ui.GlobalVars.DRIVER;
import static com.gwi.automation.ui.GlobalVars.SESSION_DATA;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

@Slf4j
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json","html:target/report.html"},
    features = "@build/rerun.txt",
    glue = "com/gwi/automation",
    tags = "")
public class FailedTestsReRunner extends AbstractTestNGCucumberTests {

  public FailedTestsReRunner(){
    super();
  }

  @AfterMethod
  public void clean() {
    DRIVER.remove();
    SESSION_DATA.remove();
  }

  @Override
  @DataProvider(parallel = true)
  public Object[][] scenarios() {
    return super.scenarios();
  }
}
