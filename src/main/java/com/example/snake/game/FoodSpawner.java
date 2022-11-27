package com.example.snake.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.example.snake.model.Food;
import com.example.snake.model.GridPoint;

public class FoodSpawner {

  private static final int FOOD_LIFETIME = 2;

  private final int gameFieldWidth;
  private final int gameFieldHeight;

  private final List<Food> foods = new ArrayList<>();
  private final Random random = new Random();

  public FoodSpawner(int gameFieldWidth, int gameFieldHeight) {
    this.gameFieldWidth = gameFieldWidth;
    this.gameFieldHeight = gameFieldHeight;
  }

  public Collection<Food> getFoods() {
    return Collections.unmodifiableCollection(foods);
  }

  public void update(long currentTime) {
    despawnFood(currentTime);
    spawnFood(currentTime);
  }

  public void despawnFood(long currentTime) {
    foods.removeIf(food -> !food.isAlive(currentTime));
  }

  public void spawnFood(long currentTime) {
    if (foods.size() < 2) {
      foods.add(new Food(getRandomFreeGridPoint(), FOOD_LIFETIME, currentTime));
    }
  }

  /**
   * @return an random unoccupied square
   */
  public GridPoint getRandomFreeGridPoint() {
    // TODO: When the snake is implemented, this needs to be refactored. Whenever the snake gets long, this
    //  method of finding an empty grid point will be inefficient and will even result in a StackOverflowError
    int x = random.nextInt(gameFieldWidth);
    int y = random.nextInt(gameFieldHeight);

    for (Food food : foods) {
      if (food.getPosition().x() == x && food.getPosition().y() == y) {
        return getRandomFreeGridPoint();
      }
    }
    return new GridPoint(x, y);
  }
}