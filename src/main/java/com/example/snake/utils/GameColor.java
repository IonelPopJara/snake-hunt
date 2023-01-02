package com.example.snake.utils;

@SuppressWarnings("unused")
public enum GameColor {

  RED("#BC4B51"),
  YELLOW("#F4E285"),
  ORANGE("#F4A259"),
  LIGHT_GREEN("#8CB369"),
  DARK_GREEN("#5B8E7D"),
  LIGHT_GREY("#727272"),
  DARK_GREY("#181818"),
  DARK_GREEN_2("#406458");

  private final String hexValue;

  GameColor(String hexValue) {
    this.hexValue = hexValue;
  }

  public String getHexValue() {
    return this.hexValue;
  }
}
