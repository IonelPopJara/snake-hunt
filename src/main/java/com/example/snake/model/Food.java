package com.example.snake.model;

public class Food {

  private final GridPoint position;
  private final int lifetime;
  private final long spawnTime;

  public Food(GridPoint position, int lifetime, long spawnTime) {
    this.position = position;
    this.lifetime = lifetime * 1000;
    this.spawnTime = spawnTime;
  }

  public GridPoint getPosition() {
    return position;
  }

  public boolean isAlive(long currentTime) {
    return spawnTime + lifetime >= currentTime;
  }
}
