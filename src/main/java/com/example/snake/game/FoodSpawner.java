package com.example.snake.game;

import java.util.*;
import java.util.function.Predicate;

import com.example.snake.model.Food;
import com.example.snake.model.FoodType;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Prey;

import static com.example.snake.model.FoodType.*;

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

  /**
   * Update the state of the food with a given time step
   *
   * @param delta           the time step between this and the previous frame, in seconds
   * @param gameEnvironment the current game environment
   */
  public void update(float delta, GameEnvironment gameEnvironment) {
    foods.forEach(food -> food.update(delta, gameEnvironment));

    despawnFood();
    spawnFood(gameEnvironment);
  }

  private void despawnFood() {
    List<Food> foodsToDespawn = foods.stream()
      .filter(Predicate.not(Food::isAlive))
      .toList();

    foodsToDespawn.forEach(this::removeFood);
  }

  /**
   * Removes the given food from the game
   */
  public void removeFood(Food food) {
    foods.remove(food);

    if (food.getFoodType() == PREY) {
      nextPreySpawnTime = System.currentTimeMillis() + PREY_SPAWN_DELAY;
    }
  }

  /**
   * Spawns food when necessary conditions are met
   */
  private void spawnFood(GameEnvironment gameEnvironment) {
    if (foods.size() >= MAX_NUM_FOODS) {
      return;
    }

    GridPoint freeGridPoint = gameEnvironment.getRandomFreeGridPoint();

    if (freeGridPoint == null) {
      return;
    }

    if (shouldSpawnPrey()) {
      nextPreySpawnTime = System.currentTimeMillis();
      foods.add(new Prey(freeGridPoint, PREY_LIFETIME, gameEnvironment.getPreyMovementSpeed()));
    } else {
      FoodType[] foodTypes = {FOOD_BOX, FOOD_CAKE, FOOD_CANDY, FOOD_MUSHROOM};
      Food newFood = new Food(freeGridPoint, FOOD_LIFETIME, foodTypes[random.nextInt(foodTypes.length)]);
      foods.add(newFood);
    }
  }

  private boolean shouldSpawnPrey() {
    // Spawn only one prey at a time, periodically, with a random chance of it actually spawning
    return nextPreySpawnTime <= System.currentTimeMillis() && !isPreySpawned() && random.nextFloat() < PREY_SPAWN_CHANCE;
  }

  private boolean isPreySpawned() {
    return foods.stream().anyMatch(food -> food.getFoodType() == PREY);
  }

  /**
   * @return A collection of Food objects that are currently on the playing field
   */
  public Collection<Food> getFoods() {
    return Collections.unmodifiableCollection(foods);
  }

  /**
   * @return The remaining lifetime of the prey that is currently in the game, or 0.0f if no prey is present
   */
  public float getPreyLifetime() {
    return foods.stream()
      .filter(e -> e.getFoodType() == PREY)
      .map(Food::getRemainingLifetime)
      .findFirst()
      .orElse(0.0f);
  }
}