package com.example.snake.model.level;

import com.example.snake.model.GridPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a wall within a level. Uses two vectors as a representation,
 * instead of a list of all the points, in order to make it more concise.
 *
 * @param startPoint the inclusive starting point of the wall
 * @param endPoint   the inclusive ending point of the wall
 */
public record Wall(GridPoint startPoint, GridPoint endPoint) {

  public Wall {
    if (startPoint.x() != endPoint.x() && startPoint.y() != endPoint.y()) {
      throw new IllegalArgumentException("Start and end point need to share an axis");
    }
  }

  /**
   * @return All the points of the wall, in no particular order
   */
  public List<GridPoint> getWallPoints() {
    List<GridPoint> result = new ArrayList<>();

    int minX = Math.min(startPoint.x(), endPoint.x());
    int minY = Math.min(startPoint.y(), endPoint.y());
    int maxX = Math.max(startPoint.x(), endPoint.x());
    int maxY = Math.max(startPoint.y(), endPoint.y());

    int deltaX = Math.min(1, maxX - minX);
    int deltaY = Math.min(1, maxY - minY);

    for (int x = minX, y = minY; x <= maxX && y <= maxY; x += deltaX, y += deltaY) {
      result.add(new GridPoint(x, y));
    }

    return result;
  }
}
