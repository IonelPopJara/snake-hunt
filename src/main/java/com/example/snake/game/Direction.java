package com.example.snake.game;

import com.example.snake.model.GridPoint;

public enum Direction {
  RIGHT(new GridPoint(1, 0)),
  LEFT(new GridPoint(-1, 0)),
  UP(new GridPoint(0, -1)),
  DOWN(new GridPoint(0, 1));

  private final GridPoint directionVector;

  Direction(GridPoint directionVector) {
    this.directionVector = directionVector;
  }

  /**
   * @return A direction vector for the Direction, with unit length
   */
  public GridPoint getDirectionVector() {
    return directionVector;
  }
}
