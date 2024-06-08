package com.gwi.automation.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class SessionData {

  private List<Chart> charts;
  private Map<String,String> payloadData;

  public SessionData (){
    payloadData = new HashMap<>();
  }
}
