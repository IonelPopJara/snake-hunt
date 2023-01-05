package com.example.snake.game;

import java.util.List;

import com.example.snake.model.Snake;

public enum Difficulty {
  EASY(20, 15, false, 5.0f),
  MEDIUM(24, 18, true, 6.5f),
  HARD(28, 21, false, 8.0f);

  /**
   * This value is multiplied with the snakes movement speed to calculate the movement speed of the pray
   */
  private static final float PREY_MOVEMENT_SPEED_MULTIPLIER = 0.75f;

  private final int gameFieldWidth;
  private final int gameFieldHeight;

  private final boolean hasEdgeWalls;

  private final float snakeMovementSpeed;

  /**
   * @param gameFieldWidth     the width of the playing field, in the number of grid cells
   * @param gameFieldHeight    the height of the playing field, in the number of grid cells
   * @param hasEdgeWalls       whether the playing field has walls at the edges of the screen
   * @param snakeMovementSpeed the speed of the snake, see {@link Snake#Snake(List, Direction, float)}
   */
  Difficulty(int gameFieldWidth, int gameFieldHeight, boolean hasEdgeWalls, float snakeMovementSpeed) {
    this.hasEdgeWalls = hasEdgeWalls;
    this.snakeMovementSpeed = snakeMovementSpeed;
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

  public float getSnakeMovementSpeed() {
    return snakeMovementSpeed;
  }

  public float getPreyMovementSpeed() {
    return snakeMovementSpeed * PREY_MOVEMENT_SPEED_MULTIPLIER;
  }
}
