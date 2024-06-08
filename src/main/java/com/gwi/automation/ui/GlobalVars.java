package com.gwi.automation.ui;

import com.gwi.automation.dto.SessionData;
import lombok.Data;
import lombok.experimental.UtilityClass;

@UtilityClass
@Data
public class GlobalVars {

    public static final ThreadLocal<DriverFactory> DRIVER = new ThreadLocal<>();
    public static final ThreadLocal<Environment> ENVIRONMENT = new ThreadLocal<>();
    public static final ThreadLocal<SessionData> SESSION_DATA = new ThreadLocal<>();
}
