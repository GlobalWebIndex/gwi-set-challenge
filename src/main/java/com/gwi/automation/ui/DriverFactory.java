package com.gwi.automation.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Slf4j
@Getter
@Setter
public class DriverFactory {

    private final DriverDetails driverDetails;
    private final WebDriver driver;
    private final DriverActions driverActions;

    public DriverFactory(){
        this.driverDetails =
            (DriverDetails.builder().browserType(GlobalVars.ENVIRONMENT.get().getBrowserType())
                .incognito(GlobalVars.ENVIRONMENT.get().isIncognito()).build());
        this.driver = creatDriver();
        this.driverActions = new DriverActions(driver) ;
    }

    public WebDriver creatDriver (){
        switch(driverDetails.getBrowserType()) {
            case "CHROME" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-infobars");
                options.addArguments("--allow-insecure-localhost");
                options.addArguments("--remote-allow-origins=*");
                if (driverDetails.isIncognito()) options.addArguments("--incognito");

                WebDriver driver = new ChromeDriver(options);
                driver.manage().deleteAllCookies();
                return driver;
            }
            default -> throw new UnsupportedOperationException("More browsers to be supported soon");
        }
    }

}
