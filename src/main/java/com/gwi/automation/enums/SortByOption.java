package com.gwi.automation.enums;

import lombok.Getter;


public enum SortByOption {
  NAME(1), CREATED(2), MODIFIED(3);

  @Getter
  private final int selectorOrder;

  SortByOption(int selectorOrder){
    this.selectorOrder = selectorOrder;
  }
  public enum OrderByOption{
    ASC,DESC
  }
}
