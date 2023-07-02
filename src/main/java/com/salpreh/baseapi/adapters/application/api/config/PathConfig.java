package com.salpreh.baseapi.adapters.application.api.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathConfig {
  public static final String BASE_PATH = "api/";
  public static final String FACTION_PATH = BASE_PATH + "faction";
  public static final String PERSON_PATH = BASE_PATH + "person";
  public static final String PLANET_PATH = BASE_PATH + "planet";
  public static final String SPACESHIP_PATH = BASE_PATH + "spaceship";
}
