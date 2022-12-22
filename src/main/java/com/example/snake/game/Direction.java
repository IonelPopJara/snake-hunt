package com.example.snake.game;

import com.example.snake.model.GridPoint;

import java.util.stream.Stream;

public enum Direction {
  RIGHT(new GridPoint(1, 0)),
  LEFT(new GridPoint(-1, 0)),
  UP(new GridPoint(0, -1)),
  DOWN(new GridPoint(0, 1));

  private final GridPoint directionVector;

  Direction(GridPoint directionVector) {
    this.directionVector = directionVector;
  }

  public static Direction getByDirectionVector(GridPoint directionVector) {
    return Stream.of(Direction.values()).filter(x -> x.getDirectionVector().equals(directionVector)).findFirst().orElseThrow();
  }

  public GridPoint getDirectionVector() {
    return directionVector;
  }
}
