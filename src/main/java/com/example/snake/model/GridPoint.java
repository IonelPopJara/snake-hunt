package com.example.snake.model;

public record GridPoint(int x, int y) {

  public GridPoint plus(GridPoint other) {
    return new GridPoint(this.x + other.x, this.y + other.y);
  }

  public GridPoint minus(GridPoint other) {
    return new GridPoint(this.x - other.x, this.y - other.y);
  }

  public GridPoint plusAndMod(int x, int y) {
    return new GridPoint((this.x + x) % x, (this.y + y) % y);
  }

  /**
   * @return the distance, squared, between this and the other grid point
   */
  public int distanceSquared(GridPoint other) {
    int deltaX = this.x - other.x;
    int deltaY = this.y - other.y;
    return deltaX * deltaX + deltaY * deltaY;
  }

  /**
   * @return the distance between this and the other grid point
   */
  public float distance(GridPoint other) {
    return (float) Math.sqrt(distanceSquared(other));
  }

  public boolean isOutOfBounds(int upperWidthBound, int upperHeightBound) {
    return x < 0 || y < 0 || x >= upperWidthBound || y >= upperHeightBound;
  }
}
