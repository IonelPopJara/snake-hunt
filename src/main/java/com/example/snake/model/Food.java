package com.example.snake.model;

public class Food {

  private final GridPoint position;
  private final float totalLifetime;

  private float currentLifetime;

  /**
   * @param totalLifetime Total lifetime of the food, in seconds
   */
  public Food(GridPoint position, float totalLifetime) {
    this.position = position;
    this.totalLifetime = totalLifetime;
    this.currentLifetime = 0.0f;
  }

  public void update(float delta) {
    currentLifetime += delta;
  }

  public boolean isAlive() {
    return currentLifetime <= totalLifetime;
  }

  public GridPoint getPosition() {
    return position;
  }
}
