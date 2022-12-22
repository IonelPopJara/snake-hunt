package com.example.snake.model.level;

import java.util.Collections;
import java.util.List;

import com.example.snake.model.GridPoint;

public record Level(List<Wall> walls) {

  public static final Level EMPTY = new Level(Collections.emptyList());

  /**
   * @return An immutable list of all the grid points of the walls, in no particular order.
   */
  public List<GridPoint> getWallPoints() {
    return walls.stream()
      .flatMap(e -> e.getWallPoints().stream())
      .distinct()
      .toList();
  }
}
