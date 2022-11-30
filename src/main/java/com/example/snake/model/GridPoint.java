package com.example.snake.model;

public record GridPoint(int x, int y) {

  public GridPoint plus(GridPoint other) {
    return new GridPoint(this.x + other.x, this.y + other.y);
  }

  public GridPoint plusAndMod(int x, int y) {
    return new GridPoint((this.x + x) % x, (this.y + y) % y);
  }
}
