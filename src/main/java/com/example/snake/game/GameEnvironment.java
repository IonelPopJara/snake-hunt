package com.example.snake.game;

import java.util.Collection;
import java.util.Random;
import java.util.function.Predicate;

import com.example.snake.model.Food;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;

/**
 * The {@link GameEnvironment} object hold information about the currently played game, such as difficulty, the size
 * of the playing field, methods to help find free squares, or to tell if a square is occupied, etc.
 */
public class GameEnvironment {

  private final Random random = new Random();

  private final Difficulty difficulty;
  private final Snake snake;
  private final FoodSpawner foodSpawner;

  public GameEnvironment(Difficulty difficulty, Snake snake, FoodSpawner foodSpawner) {
    this.difficulty = difficulty;
    this.snake = snake;
    this.foodSpawner = foodSpawner;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public int getGameFieldWidth() {
    return difficulty.getGameFieldWidth();
  }

  public int getGameFieldHeight() {
    return difficulty.getGameFieldHeight();
  }

  public Collection<Food> getFoods() {
    return foodSpawner.getFoods();
  }

  public void removeFood(Food food) {
    foodSpawner.removeFood(food);
  }

  public Snake getSnake() {
    return snake;
  }

  /**
   * Checks if a position is free
   *
   * @param gridPoint The position to check
   *
   * @return true if the given position is not occupied and free for anything to move into or be spawned at, false otherwise
   */
  public boolean isSquareFree(GridPoint gridPoint) {
    // Check if position is on snakes body
    if (snake.getBody().contains(gridPoint)) {
      return false;
    }

    // return true if all the positions of the foods do not match the given argument
    return foodSpawner.getFoods().stream()
      .map(Food::getPosition)
      .allMatch(Predicate.not(gridPoint::equals));
  }

  /**
   * @return an random unoccupied square
   */
  public GridPoint getRandomFreeGridPoint() {
    // TODO: When the snake is implemented, this needs to be refactored. Whenever the snake gets long, this
    //  method of finding an empty grid point will be inefficient and will even result in a StackOverflowError
    int gameFieldWidth = difficulty.getGameFieldWidth();
    int gameFieldHeight = difficulty.getGameFieldHeight();

    int x = random.nextInt(gameFieldWidth);
    int y = random.nextInt(gameFieldHeight);

    for (int i = 0; i < snake.getSize(); i++) {
      if (snake.getPoint(i).x() == x && snake.getPoint(i).y() == y) {
        return getRandomFreeGridPoint();
      }
    }

    for (Food food : foodSpawner.getFoods()) {
      if (food.getPosition().x() == x && food.getPosition().y() == y) {
        return getRandomFreeGridPoint();
      }
    }

    return new GridPoint(x, y);
  }
}
