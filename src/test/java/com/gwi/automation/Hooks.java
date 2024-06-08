package com.gwi.automation;

import static com.gwi.automation.constants.ScenarioTags.UI;
import static com.gwi.automation.ui.GlobalVars.DRIVER;
import static com.gwi.automation.ui.GlobalVars.ENVIRONMENT;
import static com.gwi.automation.ui.GlobalVars.SESSION_DATA;
import static com.gwi.automation.utils.JsonUtils.loadFromFile;

import com.gwi.automation.dto.SessionData;
import com.gwi.automation.ui.DriverFactory;
import com.gwi.automation.ui.Environment;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;

@Slf4j
public class Hooks {

  private final SoftAssertions softly;

  public Hooks ( SoftAssertions softly) throws IOException {
    this.softly = softly;
    ENVIRONMENT.set(loadFromFile(Environment.class,"src/main/resources/environment.json"));
  }

  @Before( order = 0 ,value = UI)
  public void initializeDriver() {
    DriverFactory driverFactory = new DriverFactory();
    DRIVER.set(driverFactory);
    log.info("initializing driver: {}","CHROME DRIVER");
  }

  @Before( order = 1)
  public void initializeSessionData() {
    SESSION_DATA.set(new SessionData());
    log.info("initializing session data");
  }

  @After(order = 4, value =UI)
  public void tearDownActions(){
    DRIVER.get().getDriver().quit();
  }

  @After(order = 5)
  public void assertAll() {
    softly.assertAll();
  }

}
