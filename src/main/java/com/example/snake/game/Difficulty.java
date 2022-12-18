package com.example.snake.game;

public enum Difficulty {
  EASY(20, 15),
  MEDIUM(24, 18),
  HARD(28, 21);

  private final int gameFieldWidth;
  private final int gameFieldHeight;

  Difficulty(int gameFieldWidth, int gameFieldHeight) {
    if (gameFieldHeight / 3.0f * 4.0f != gameFieldWidth) {
      throw new IllegalArgumentException("The aspect ratio of the playing field must be 4:3!");
    }

    this.gameFieldWidth = gameFieldWidth;
    this.gameFieldHeight = gameFieldHeight;
  }

  public int getGameFieldWidth() {
    return gameFieldWidth;
  }

  public int getGameFieldHeight() {
    return gameFieldHeight;
  }
}
