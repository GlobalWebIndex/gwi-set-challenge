package com.gwi.automation.pages;

import static com.gwi.automation.ui.GlobalVars.DRIVER;
import static com.gwi.automation.ui.GlobalVars.ENVIRONMENT;

import com.gwi.automation.ui.DriverActions;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;

@NoArgsConstructor
public abstract class BasePage {

  protected WebDriver driver;
  protected DriverActions driverActions;

  public void initialize() {
    driver = DRIVER.get().getDriver();
    this.driverActions = DRIVER.get().getDriverActions();
    driver.get(ENVIRONMENT.get().getBaseUrl());
  }

  public boolean urlContains(String text) {
    return driverActions.waitUntilUrlContains(text);
  }

  public abstract boolean isPageLoaded();
}
