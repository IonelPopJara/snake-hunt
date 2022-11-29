package com.example.snake.game;

import com.example.snake.model.GridPoint;

public enum Direction {
  RIGHT,
  LEFT,
  UP,
  DOWN;

  public boolean isOppositeOf(Direction other) {
    return switch (this) {
      case RIGHT -> other == LEFT;
      case LEFT -> other == RIGHT;
      case UP -> other == DOWN;
      case DOWN -> other == UP;
    };
  }

  /**
   * @param directionVector A unit length direction vector aligned with any axis.
   * @return the direction enum the given direction vector points towards
   */
  public static Direction getByDirectionVector(GridPoint directionVector) {
    if (directionVector.x() == 1) {
      return RIGHT;
    } else if (directionVector.x() == -1) {
      return LEFT;
    } else if (directionVector.y() == -1) {
      return UP;
    } else if (directionVector.y() == 1) {
      return DOWN;
    }

    throw new IllegalArgumentException("Given argument is not a unit length direction vector");
  }
}
