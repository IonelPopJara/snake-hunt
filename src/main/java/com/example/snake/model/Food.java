package com.example.snake.model;

public class Food {

  private GridPoint position;
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

  public void update(float delta, Snake snake, int gameFieldWidth, int gameFieldHeight) {
    currentLifetime += delta;
  }

  public int getScoreValue() { return 1; }
  public boolean isAlive() {
    return currentLifetime <= totalLifetime;
  }

  protected void setPosition(GridPoint position) {
    this.position = position;
  }

  public GridPoint getPosition() {
    return position;
  }

  public FoodType getFoodType() {
    return FoodType.FOOD;
  }

  public float getRemainingLifetime() {
    return totalLifetime - currentLifetime;
  }
}
