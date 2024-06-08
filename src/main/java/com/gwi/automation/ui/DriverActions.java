package com.gwi.automation.ui;


import static java.time.Duration.ofMillis;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

@Data
@Getter
@Slf4j
public class DriverActions {

    private WebDriver driver;
    private FluentWait<WebDriver> fluentWait ;
    private static final int TIMEOUT = 10000;
    private static final int POLL_DELAY = 500;

    public DriverActions(WebDriver driver){
    this.driver = driver;
        this.fluentWait = new FluentWait<>(driver)
            .ignoring(Exception.class);
    }

    public void clickElement(WebElement element) {
        waitForElementClickable(element);
        element.click();
    }

    public void sendKeysToField(WebElement element, boolean clearExistingText, String text){
        if (clearExistingText) element.clear();
        waitForElementClickable(element);
        element.sendKeys(text);
    }

    public void sendKeysToField(WebElement element , String text){
        sendKeysToField( element,false ,  text);
    }

    public void deleteAll(WebElement element){
        String os = System.getProperty("os.name").toLowerCase();
        Keys commandKey = os.contains("mac") ? Keys.COMMAND : Keys.CONTROL;

        new Actions(driver).click(element)
            .keyDown(commandKey)
            .sendKeys("a")
            .keyUp(commandKey)
            .sendKeys(Keys.BACK_SPACE)
            .perform();
    }

// region Waits

    public boolean waitForElementClickable(WebElement element){
        return waitForExpectedCondition(elementToBeClickable(element));
    }

    public boolean waitForElementClickable(By locator){
        return waitForElementClickable(waitAndFindElement(locator));
    }

    public boolean waitForElementVisible(WebElement element){
        return waitForExpectedCondition(visibilityOf(element));
    }

    public boolean waitForElementsVisible(List<WebElement> element){
        return waitForExpectedCondition(visibilityOfAllElements(element));
    }

    public WebElement waitAndFindElement(By locator){
         waitForExpectedCondition(presenceOfElementLocated(locator));
         return driver.findElement(locator);
    }

    public List<WebElement> waitAndFindElements(By locator){
         waitForExpectedCondition(presenceOfAllElementsLocatedBy(locator));
         return driver.findElements(locator);
    }

    public boolean waitUntilAttributeContains( WebElement element,  String attribute,  String value){
        return waitForExpectedCondition(attributeContains(element, attribute,value));
    }

    public boolean waitUntilUrlContains( String urlText){
        return waitForExpectedCondition(urlContains(urlText));
    }

    private boolean waitForExpectedCondition(ExpectedCondition<?> expectedCondition) {
        return waitForExpectedCondition(expectedCondition, TIMEOUT, POLL_DELAY);
    }

    private boolean waitForExpectedCondition(ExpectedCondition<?> expectedCondition, int timeOut, int pollInterval) {
        try {
            fluentWait.withTimeout(ofMillis(timeOut)).pollingEvery(ofMillis(pollInterval))
                .until(expectedCondition);
            return true;
        } catch ( TimeoutException ex) {
            log.error(ex.getMessage());
            return false;
        }
    }
}
