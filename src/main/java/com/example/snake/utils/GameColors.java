package com.example.snake.utils;

public enum GameColors {

  RED("#BC4B51"),
  YELLOW("#F4E285"),
  ORANGE("#F4A259"),
  LIGHT_GREEN("#8CB369"),
  DARK_GREEN("#5B8E7D"),
  DARK_GREY("#181818");

  private final String colorValue;

  GameColors(String colorValue) {
    this.colorValue = colorValue;
  }

  public String getColorValue() {
    return this.colorValue;
  }
}
