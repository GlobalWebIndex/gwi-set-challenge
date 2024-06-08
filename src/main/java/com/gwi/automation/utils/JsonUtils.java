package com.gwi.automation.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {

  private final Gson gson = new GsonBuilder().create();

 public static  <T> T loadFromFile(Class<T> type, String path) throws IOException {

   try (FileReader reader = new FileReader(path)) {
     return gson.fromJson(reader, type);
   }
 }
}
