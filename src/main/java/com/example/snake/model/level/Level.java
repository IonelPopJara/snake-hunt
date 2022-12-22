package com.example.snake.model.level;

import java.util.Collections;
import java.util.List;

import com.example.snake.model.GridPoint;

public record Level(List<Wall> walls) {

  public static final Level EMPTY = new Level(Collections.emptyList());

  public List<GridPoint> getWallPoints() {
    return walls.stream()
      .flatMap(e -> e.getWallPoints().stream())
      .distinct()
      .toList();
  }
}
