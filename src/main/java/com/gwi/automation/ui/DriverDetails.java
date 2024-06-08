package com.gwi.automation.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DriverDetails {

  private String browserType;
  private boolean incognito;
}
