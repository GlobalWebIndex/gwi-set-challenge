package com.gwi.automation.steps;

import static com.gwi.automation.enums.SortByOption.NAME;
import static com.gwi.automation.ui.GlobalVars.SESSION_DATA;
import static com.gwi.automation.utils.Comparators.sortBy;
import static org.assertj.core.api.Assertions.assertThat;

import com.gwi.automation.enums.SortByOption;
import com.gwi.automation.pages.ChartsPage;
import io.cucumber.java.en.When;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;

@Slf4j
public class ChartsManagementSteps {

  private final SoftAssertions softly;
  private ChartsPage chartsPage;

  public ChartsManagementSteps(SoftAssertions softly) {
    this.softly = softly;
    this.chartsPage = new ChartsPage();
  }

  @When("I am in the landing page of the application")
  public void navigateAndLoad()   {
    chartsPage.initialize();
  }

  @When("^I validate that all charts in the dashboard are displayed by (.*) order$")
  public void validateSorting(SortByOption sortingOption)   {
      sortBy(sortingOption);
      final var chartsList = SESSION_DATA.get().getCharts();
      for (int i =0; i < chartsList.size() ; i++){
        final var displayedChart = chartsPage.getChartInRow(i+2);

        softly.assertThat(chartsList.get(i).getName())
            .withFailMessage("SORTING BY %s. The displayed chart NAME of the %s row should be:\nExpected: %s\nActual: \n%s"
                .formatted(sortingOption.name(),i,chartsList.get(i).getName(),displayedChart.getName())).isEqualTo(displayedChart.getName());
        softly.assertThat(timestampToLocalDate(chartsList.get(i).getDateCreated()))
            .withFailMessage("SORTING BY %s. The displayed chart CREATION DATE of the %s row should be:\nExpected: %s\nActual: \n%s"
                .formatted(sortingOption.name(),i,timestampToLocalDate(chartsList.get(i).getDateCreated()),timestampToLocalDate(displayedChart.getDateCreated()))).isEqualTo(timestampToLocalDate(displayedChart.getDateCreated()));
        softly.assertThat(timestampToLocalDate(chartsList.get(i).getDateModified()))
            .withFailMessage("SORTING BY %s. The displayed chart MODIFICATION DATE of the %s row should be:\nExpected: %s\nActual: \n%s"
                .formatted(sortingOption.name(),i,timestampToLocalDate(chartsList.get(i).getDateModified()),timestampToLocalDate(displayedChart.getDateModified()))).isEqualTo(timestampToLocalDate(displayedChart.getDateModified()));

      }
  }

  @When("I select create chart button")
  public void selectCreateChart()   {
      chartsPage.clickCreateChartBtn();
  }

  @When("I validate that user is redirected to create chart page")
  public void validateRedirection()   {
      softly.assertThat(chartsPage.urlContains("Page2")).isTrue();
  }

  @When("I click the back button")
  public void clickBack()   {
      chartsPage.clickBack();
  }

  @When("^I select the (.*) sorting option$")
  public void clickSortingOption(SortByOption sortingOrder)   {
      chartsPage.clickSortingOption(sortingOrder);
  }

  @When("^I clear the field$")
  public void clearSearch( )   {
    chartsPage.clearSearch();
  }

  @When("^I type \"(.*)\" at the search option and validate that (?:all the|no) charts(?: that match this pattern|) are displayed$")
  public void validateSearchFiltering(String text ) {
    // case-insensitive
    chartsPage.typeToSearch(text);
    sortBy(NAME);
    Pattern pattern = Pattern.compile("(?i)".concat(text));
    final var filteredCharts = SESSION_DATA.get().getCharts().stream().filter(x-> {
      Matcher matcher = pattern.matcher(x.getName());
      return matcher.find();
    }).toList();
    assertThat(filteredCharts.size()).withFailMessage("The displayed chart number is incorrect")
        .isEqualTo(chartsPage.getDisplayedChartsNumber());
    for (int i =0; i < filteredCharts.size() ; i++){
      final var displayedChart = chartsPage.getChartInRow(i+2);
      softly.assertThat(filteredCharts.get(i).getName()).isEqualTo(displayedChart.getName());
    }
  }

  private LocalDate timestampToLocalDate(long timestamp){
    return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
