package com.gwi.automation.api.response;

import com.gwi.automation.dto.Chart;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class GetChartResponse {

  private List<Chart> charts;

}
