package com.gwi.automation.ui;

import lombok.Data;


@Data
public class Environment {

    private final String baseUrl;
    private final String browserType;
    private final boolean incognito;
    private final String apiBaseUrl;
}
