package com.example.snake.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import com.example.snake.model.Food;
import com.example.snake.model.FoodType;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Prey;
import com.example.snake.model.Snake;

public class FoodSpawner {

  // Should be moved into corresponding classes, unless we want semi random values
  private static final float FOOD_LIFETIME = 10.0f;
  private static final float PREY_LIFETIME = 15.0f;

  /**
   * "Cooldown" on the prey spawner, in milliseconds. If this value
   * is 10000, then that means the prey can only spawn 10 seconds
   * after getting eaten or being despawned.
   */
  private static final long PREY_SPAWN_DELAY = 10_000;

  private static final long INITIAL_PREY_SPAWN_DELAY = 5_000;

  private static final float PREY_SPAWN_CHANCE = 0.25f;

  /**
   * The maximum number of foods that can be present on the game
   * field at once, whether it is static food or moving prey
   */
  private static final int MAX_NUM_FOODS = 2;

  private final List<Food> foods = new ArrayList<>();
  private final Random random = new Random();

  private long nextPreySpawnTime = System.currentTimeMillis() + INITIAL_PREY_SPAWN_DELAY;

  public void update(float delta, Snake snake, int gameFieldWidth, int gameFieldHeight) {
    foods.forEach(food -> food.update(delta, snake, gameFieldWidth, gameFieldHeight));

    despawnFood();
    spawnFood(snake, gameFieldWidth, gameFieldHeight);
  }

  private void despawnFood() {
    List<Food> foodsToDespawn = foods.stream()
      .filter(Predicate.not(Food::isAlive))
      .toList();

    foodsToDespawn.forEach(this::removeFood);
  }

  public void removeFood(Food food) {
    foods.remove(food);

    if(food.getFoodType() == FoodType.PREY) {
      nextPreySpawnTime = System.currentTimeMillis() + PREY_SPAWN_DELAY;
    }
  }

  private void spawnFood(Snake snake, int gameFieldWidth, int gameFieldHeight) {
    if (foods.size() < MAX_NUM_FOODS) {
      GridPoint freeGridPoint = getRandomFreeGridPoint(snake, gameFieldWidth, gameFieldHeight);

      if(shouldSpawnPrey()) {
        nextPreySpawnTime = System.currentTimeMillis();
        foods.add(new Prey(freeGridPoint, PREY_LIFETIME, 5.0f));
      } else {
        foods.add(new Food(freeGridPoint, FOOD_LIFETIME));
      }
    }
  }

  private boolean shouldSpawnPrey() {
    // Spawn only one prey at a time, periodically, with a random chance of it actually spawning
    return nextPreySpawnTime <= System.currentTimeMillis() && !isPreySpawned() && random.nextFloat() < PREY_SPAWN_CHANCE;
  }

  private boolean isPreySpawned() {
    return foods.stream().anyMatch(food -> food.getFoodType() == FoodType.PREY);
  }

  /**
   * @return an random unoccupied square
   */
  private GridPoint getRandomFreeGridPoint(Snake snake, int gameFieldWidth, int gameFieldHeight) {
    // TODO: When the snake is implemented, this needs to be refactored. Whenever the snake gets long, this
    //  method of finding an empty grid point will be inefficient and will even result in a StackOverflowError
    int x = random.nextInt(gameFieldWidth);
    int y = random.nextInt(gameFieldHeight);
    for (int i = 0; i < snake.getSize(); i ++) {
      if (snake.getPoint(i).x() == x && snake.getPoint(i).y() == y) {
        return getRandomFreeGridPoint(snake, gameFieldWidth, gameFieldHeight);
      }
    }
      for (Food food : foods) {
        if (food.getPosition().x() == x && food.getPosition().y() == y) {
          return getRandomFreeGridPoint(snake, gameFieldWidth, gameFieldHeight);
        }
      }
    return new GridPoint(x, y);
  }

  public Collection<Food> getFoods() {
    return Collections.unmodifiableCollection(foods);
  }
}