package com.gwi.automation.pages;

import static com.gwi.automation.enums.SortByOption.CREATED;
import static com.gwi.automation.enums.SortByOption.MODIFIED;
import static com.gwi.automation.enums.SortByOption.NAME;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import com.gwi.automation.dto.Chart;
import com.gwi.automation.enums.SortByOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

@NoArgsConstructor
public class ChartsPage extends BasePage {

  @FindBy(css = ".createButton")
  private WebElement createButton;
  @FindBy(css = "[placeholder='Search charts']")
  private WebElement search;
  @FindBy(css = ".root  > div")
  private List<WebElement> boardCells;

  private static final String BOARD_FIELD_SELECTOR = ".root > div:nth-child(%s)>div:nth-child(%s) p";
  private static final String BACK_BUTTON = "Go back";
  private static final String CONTAINS_SELECTOR = "//*[contains(text(), '%s')]";
  private static final String ORDER_OPTION = ".root > div:nth-child(1) div:nth-child(%s) button";

  @Override
  public void initialize() {
    super.initialize();
    PageFactory.initElements(new DefaultElementLocatorFactory(driver), this);
  }

  // Gets the chart which is displayed in the corresponding row number
  public Chart getChartInRow(int row) {

    final var name = driverActions.waitAndFindElement(
        By.cssSelector(BOARD_FIELD_SELECTOR.formatted(row, NAME.getSelectorOrder()))).getText();
    final var dateCreated = convertToTimestamp(driverActions.waitAndFindElement(
        By.cssSelector(BOARD_FIELD_SELECTOR.formatted(row, CREATED.getSelectorOrder()))).getText());
    final var dateModified = convertToTimestamp(driverActions.waitAndFindElement(
        By.cssSelector(BOARD_FIELD_SELECTOR.formatted(row, MODIFIED.getSelectorOrder()))).getText());
    return Chart.builder().name(name).dateCreated(dateCreated).dateModified(dateModified).build();
  }

  private long convertToTimestamp(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
    LocalDate localDate = LocalDate.parse(date, formatter);
    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  public int getDisplayedChartsNumber() {
    assertThat(boardCells.size()).withFailMessage("The Board Header Should Be Displayed")
        .isGreaterThanOrEqualTo(1);
    return boardCells.size() - 1;
  }

  public void clickCreateChartBtn() {
    driverActions.clickElement(createButton);
  }

  public void clickSortingOption(SortByOption selectorOrder) {
    driverActions.clickElement(driverActions.waitAndFindElement(
        By.cssSelector(ORDER_OPTION.formatted(selectorOrder.getSelectorOrder()))));
    await().atMost(5, SECONDS).pollDelay(2, SECONDS).pollInterval(500, MILLISECONDS)
        .until(this::isPageLoaded);
  }

  public void clickBack() {
    driverActions.waitAndFindElement(By.xpath(CONTAINS_SELECTOR.formatted(BACK_BUTTON))).click();
  }

  public void typeToSearch(String text) {
    driverActions.sendKeysToField(search, text);
    await().atMost(5, SECONDS).pollDelay(2, SECONDS).pollInterval(500, MILLISECONDS)
        .until(() -> driverActions.waitUntilAttributeContains(search, "value", text));
  }

  public void clearSearch() {
    driverActions.deleteAll(search);
  }

  @Override
  public boolean isPageLoaded() {
    return driverActions.waitForElementClickable(createButton);
  }
}
