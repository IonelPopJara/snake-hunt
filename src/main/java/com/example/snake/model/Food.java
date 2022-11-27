package com.example.snake.model;

public class Food {

  private static final long SECONDS_TO_MILLISECONDS = 1000L;

  private final GridPoint position;
  private final int lifetimeSeconds;
  private final long spawnTime;

  public Food(GridPoint position, int lifetimeSeconds, long spawnTime) {
    this.position = position;
    this.lifetimeSeconds = lifetimeSeconds;
    this.spawnTime = spawnTime;
  }

  public GridPoint getPosition() {
    return position;
  }

  public boolean isAlive(long currentTime) {
    return spawnTime + lifetimeSeconds * SECONDS_TO_MILLISECONDS >= currentTime;
  }
}
