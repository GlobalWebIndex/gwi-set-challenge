package com.gwi.automation.steps;

import static com.gwi.automation.ui.GlobalVars.SESSION_DATA;
import static com.gwi.automation.utils.Comparators.sortBy;
import static com.gwi.automation.utils.PayloadHelper.transformData;
import static org.assertj.core.api.Assertions.assertThat;

import com.gwi.automation.api.endpoint.ChartsEndpoint;
import com.gwi.automation.dto.Chart;
import com.gwi.automation.enums.SortByOption;
import com.gwi.automation.enums.SortByOption.OrderByOption;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;

@Slf4j
public class ChartsApiSteps {

  private final SoftAssertions softly;
  private ChartsEndpoint chartsEndpoint;

  public ChartsApiSteps(SoftAssertions softly) {
    this.softly = softly;
    this.chartsEndpoint = new ChartsEndpoint();
  }

  @When("^I create a get charts request$")
  public void clearSearch(Map<String, String> field) {
    transformData(field);
    chartsEndpoint.getChart(SESSION_DATA.get().getPayloadData());
  }

  @When("^I validate that request fails with status (.*)$")
  public void validateStatus(int status) {
    softly.assertThat(chartsEndpoint.getResponseStatusCode()).isEqualTo(status);
  }

  @When("^I validate that error (.*) is displayed$")
  public void validateErrorMessage(String message) {
    softly.assertThat(message).withFailMessage("The error message is not the expected one")
        .isEqualTo(chartsEndpoint.getResponseError());

  }

  @When("^I validate that the data retrieved for sorting options (.*),(.*) are correctly sorted$")
  public void validateRetrievedData(SortByOption sortingOption, OrderByOption orderOption) {

    assertThat(chartsEndpoint.getResponseStatusCode()).withFailMessage(
        "Request failed with status:%s, \"%s\"", chartsEndpoint.getResponseStatusCode(),chartsEndpoint.getResponseError(),
        chartsEndpoint.getResponseError()).isEqualTo(200);

    sortBy(sortingOption);
    List<Chart> chartsList = new ArrayList<>(SESSION_DATA.get().getCharts());
    List<Chart> responseChartList = chartsEndpoint.getResponseBody();
    switch (sortingOption) {
      case NAME, CREATED -> {
        switch (orderOption) {
          case DESC -> Collections.reverse(chartsList);
        }
      }
      case MODIFIED -> {
        switch (orderOption) {
          case ASC -> Collections.reverse(chartsList);
        }
      }
    }

    softly.assertThat(chartsList).usingRecursiveComparison()
        .withFailMessage("The return values are not correct.\nExpected: %s\nActual: %s", chartsList,
            responseChartList).isEqualTo(responseChartList);
  }
}
