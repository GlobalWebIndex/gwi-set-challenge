package com.gwi.automation.api.endpoint;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

import com.google.gson.Gson;
import com.gwi.automation.api.response.GetChartResponse;
import com.gwi.automation.dto.Chart;
import com.gwi.automation.ui.GlobalVars;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChartsEndpoint {

  private static final String CHARTS_ENDPOINT = "/charts";
  private final Gson gson;
  private static final String APP_JSON = "application/json";
  private  Response response;

  public ChartsEndpoint (){
    this.gson = new Gson();
  }

  public void getChart(Map<String,String> parameters){

     response = given()
        .log().all()
        .header(CONTENT_TYPE,APP_JSON)
        .params(parameters)
        .when()
        .get(GlobalVars.ENVIRONMENT.get().getApiBaseUrl().concat(CHARTS_ENDPOINT))
        .then().log().all()
        .extract().response();

  }

  public  List<Chart> getResponseBody(){
    return gson.fromJson(response.asString(), GetChartResponse.class).getCharts();
  }

  public int getResponseStatusCode(){
    return response.statusCode();
  }

  public String getResponseError(){
    return Optional.ofNullable(JsonPath.from(response.asString()).getString("error")).orElse(EMPTY);
  }

}
