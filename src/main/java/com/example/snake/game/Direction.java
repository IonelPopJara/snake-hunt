package com.example.snake.game;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

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
   * Finds the Direction enum by a unit vector
   *
   * @param directionVector a GridPoint value representing a direction vector, must have length 1
   *
   * @return the corresponding Direction enum value
   *
   * @throws NoSuchElementException if the given argument is not a vector of length 1
   */
  public static Direction getByDirectionVector(GridPoint directionVector) {
    return Stream.of(Direction.values()).filter(x -> x.getDirectionVector().equals(directionVector)).findFirst().orElseThrow();
  }

  /**
   * @return A direction vector for the Direction, with unit length
   */
  public GridPoint getDirectionVector() {
    return directionVector;
  }
}
