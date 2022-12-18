package com.example.snake.game;

public enum Difficulty {
  EASY(20, 15, false),
  MEDIUM(24, 18, true),
  HARD(28, 21, true);

  private final int gameFieldWidth;
  private final int gameFieldHeight;

  private final boolean hasEdgeWalls;

  Difficulty(int gameFieldWidth, int gameFieldHeight, boolean hasEdgeWalls) {
    this.hasEdgeWalls = hasEdgeWalls;
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

  public boolean hasEdgeWalls() {
    return hasEdgeWalls;
  }
}
