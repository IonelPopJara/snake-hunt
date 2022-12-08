package com.example.snake.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import com.example.snake.model.Food;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Prey;
import com.example.snake.model.Snake;

public class FoodSpawner {

  private static final float FOOD_LIFETIME = 10.0f;
  private static final float PREY_LIFETIME = 10.0f;

  private static final int MAX_NUM_FOODS = 2;
  private static final int MAX_NUM_PREY = 1;

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

  public void update(float delta, Snake snake) {
    foods.forEach(food -> food.update(delta, snake, gameFieldWidth, gameFieldHeight));

    despawnFood();
    spawnFood(snake);
  }

  public void despawnFood() {
    foods.removeIf(Predicate.not(Food::isAlive));
  }

  public void spawnFood(Snake snake) {
    // TODO: refactor this... look at comment in Renderer class
    int spawnedPreyCount = (int) foods.stream()
      .filter(Prey.class::isInstance)
      .count();
    int spawnedFoodCount = foods.size() - spawnedPreyCount;

    if (spawnedFoodCount < MAX_NUM_FOODS) {
      foods.add(new Food(getRandomFreeGridPoint(snake), FOOD_LIFETIME));
    }

    if (spawnedPreyCount < MAX_NUM_PREY) {
      foods.add(new Prey(getRandomFreeGridPoint(snake), PREY_LIFETIME, 5));
    }
  }

  public void removeFood(Food food) {
    foods.remove(food);
  }

  /**
   * @return an random unoccupied square
   */
  public GridPoint getRandomFreeGridPoint(Snake snake) {
    // TODO: When the snake is implemented, this needs to be refactored. Whenever the snake gets long, this
    //  method of finding an empty grid point will be inefficient and will even result in a StackOverflowError
    int x = random.nextInt(gameFieldWidth);
    int y = random.nextInt(gameFieldHeight);
    for (int i = 0; i < snake.getSize(); i ++) {
      if (snake.getPoint(i).x() == x && snake.getPoint(i).y() == y) {
        return getRandomFreeGridPoint(snake);
      }
    }
      for (Food food : foods) {
        if (food.getPosition().x() == x && food.getPosition().y() == y) {
          return getRandomFreeGridPoint(snake);
        }
      }
    return new GridPoint(x, y);
  }
}