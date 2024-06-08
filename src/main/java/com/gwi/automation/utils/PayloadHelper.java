package com.gwi.automation.utils;

import static com.gwi.automation.ui.GlobalVars.SESSION_DATA;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PayloadHelper {

  public static void transformData(Map<String,String> data){
    for (Map.Entry<String, String> entry : data.entrySet()) {
      if(entry.getValue().equals("_")) {
        SESSION_DATA.get().getPayloadData().put(entry.getKey(), EMPTY);
      } else if (entry.getValue().equals("NULL")) {
        SESSION_DATA.get().getPayloadData().put(entry.getKey(), null);
      }else if (entry.getValue().equals("FIELD_MISSING")){
          //remove from parameters map
      }else{
        SESSION_DATA.get().getPayloadData().put(entry.getKey(), entry.getValue());
      }
    }
  }
}
