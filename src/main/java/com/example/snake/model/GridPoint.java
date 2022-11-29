package com.example.snake.model;

public record GridPoint(int x, int y) {

  public GridPoint minus(GridPoint other) {
    return new GridPoint(this.x - other.x, this.y - other.y);
  }
}
