package com.miro.landrouter.util.stream;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Collector {

  public static <T> java.util.stream.Collector<T, ?, List<T>> reversed() {
    return Collectors.collectingAndThen(Collectors.toList(), list -> {
      Collections.reverse(list);
      return list;
    });
  }
}