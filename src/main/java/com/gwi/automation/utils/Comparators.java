package com.gwi.automation.utils;

import static com.gwi.automation.ui.GlobalVars.SESSION_DATA;

import com.gwi.automation.dto.Chart;
import com.gwi.automation.enums.SortByOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.function.Function;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@Getter
@UtilityClass
public class Comparators {

  public static <T> Comparator<T> getDateTimeComparator(
      Function<T, String> getComparingKey,
      Comparator<T> secondaryComparator,
      boolean ascending) {

    Comparator<T> primaryComparator = Comparator.comparing(getComparingKey,
        Comparator.comparing(x -> LocalDate.parse(x,
            DateTimeFormatter.ofPattern("d MMM yyyy"))));

    if (!ascending) {
      primaryComparator = primaryComparator.reversed();
    }

    return primaryComparator.thenComparing(secondaryComparator);
  }

  public static void sortBy(SortByOption sortingOption) {
    switch (sortingOption) {
      case NAME -> SESSION_DATA.get()
          .setCharts(SESSION_DATA.get().getCharts().stream().sorted(Comparator.comparing(
                  Chart::getName))
              .toList());
      case CREATED -> SESSION_DATA.get().setCharts(SESSION_DATA.get().getCharts().stream().sorted(
          Comparator.comparing(Chart::getDateCreated).thenComparing(Chart::getName)).toList());
      case MODIFIED -> SESSION_DATA.get().setCharts(SESSION_DATA.get().getCharts().stream().sorted(
          Comparator.comparing(Chart::getDateModified).thenComparing(Chart::getName)).toList());
    }
  }
}