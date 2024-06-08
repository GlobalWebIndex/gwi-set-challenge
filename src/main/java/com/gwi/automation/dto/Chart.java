package com.gwi.automation.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
@Data
public class Chart {

  private String name;
  @SerializedName("created_at")
  private long dateCreated;
  @SerializedName("modified_at")
  private long dateModified;
}
