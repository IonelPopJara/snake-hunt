package com.example.snake.model;

public record GridPoint(int x, int y) {

  /**
   * Returns a new GridPoint which is the result of addition between this and the provided value
   *
   * @param other the other value to add
   *
   * @return a new GridPoint instance with the result
   */
  public GridPoint plus(GridPoint other) {
    return new GridPoint(this.x + other.x, this.y + other.y);
  }

  /**
   * Adds the two given components to this position, followed by the modulo for each respective component
   *
   * @param x the x component
   * @param y the y component
   *
   * @return a new GridPoint instance with the result
   */
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

  /**
   * Checks whether the position is out of bounds, i.e. having any component
   * less than zero, or greater than the given bounds
   *
   * @param upperWidthBound  the exclusive upper bound for the x-axis
   * @param upperHeightBound the exclusive upper bound for the y-axis
   *
   * @return true if position is out of bounds, false otherwise
   */
  public boolean isOutOfBounds(int upperWidthBound, int upperHeightBound) {
    return x < 0 || y < 0 || x >= upperWidthBound || y >= upperHeightBound;
  }
}
