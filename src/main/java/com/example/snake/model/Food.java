package com.example.snake.model;

import com.example.snake.game.GameEnvironment;

public class Food {

  private GridPoint position;
  private final float totalLifetime;
  private float currentLifetime;
  private final FoodType foodType;

  /**
   * @param totalLifetime Total lifetime of the food, in seconds
   */
  public Food(GridPoint position, float totalLifetime, FoodType foodType) {
    this.position = position;
    this.totalLifetime = totalLifetime;
    this.foodType = foodType;
    this.currentLifetime = 0.0f;
  }

  /**
   * Update the state of the food with a given time step
   *
   * @param delta           the time step between this and the previous frame, in seconds
   * @param gameEnvironment the current game environment
   */
  public void update(float delta, GameEnvironment gameEnvironment) {
    currentLifetime += delta;
  }

  /**
   * @return the number of points the food contributes towards the score, as well as the length of the snake
   */
  public int getScoreValue() {
    return 1;
  }

  /**
   * @return true if the food is still "alive", meaning it has remaining lifetime
   */
  public boolean isAlive() {
    return currentLifetime <= totalLifetime;
  }

  /**
   * Set the position of the food
   */
  protected void setPosition(GridPoint position) {
    this.position = position;
  }

  /**
   * Get the position of the food
   */
  public GridPoint getPosition() {
    return position;
  }

  /**
   * Get the type of the food
   */
  public FoodType getFoodType() {
    return foodType;
  }

  /**
   * Get the remaining lifetime of the food
   */
  public float getRemainingLifetime() {
    return totalLifetime - currentLifetime;
  }
}
