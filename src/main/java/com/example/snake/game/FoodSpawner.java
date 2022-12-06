package com.example.snake.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.example.snake.model.Food;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;

public class FoodSpawner {

  private static final int FOOD_LIFETIME = 10;

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

  public void update(long currentTime, Snake snake) {
    despawnFood(currentTime);
    spawnFood(currentTime, snake);
  }

  public void despawnFood(long currentTime) {
    foods.removeIf(food -> !food.isAlive(currentTime));
  }
  public void spawnFood(long currentTime, Snake snake) {

        if (foods.size() < 2) {
          foods.add(new Food(getRandomFreeGridPoint(snake), FOOD_LIFETIME, currentTime));
        }
      }

 public void foodEaten(Food food){
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