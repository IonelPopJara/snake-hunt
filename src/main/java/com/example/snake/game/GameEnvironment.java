package com.example.snake.game;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import com.example.snake.model.Food;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;
import com.example.snake.model.level.Level;

/**
 * The {@link GameEnvironment} object holds information about the currently played game, such as difficulty, the size
 * of the playing field, methods to help find free squares, to tell if a square is occupied, etc.
 */
public class GameEnvironment {

  private final Random random = new Random();

  private final Difficulty difficulty;
  private final Snake snake;
  private final FoodSpawner foodSpawner;

  private final List<GridPoint> walls;

  public GameEnvironment(Difficulty difficulty, Snake snake, FoodSpawner foodSpawner, Level level) {
    this.difficulty = difficulty;
    this.snake = snake;
    this.foodSpawner = foodSpawner;
    this.walls = level.getWallPoints();
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

  public boolean hasEdgeWalls() {
    return difficulty.hasEdgeWalls();
  }

  public float getPreyMovementSpeed() {
    return difficulty.getPreyMovementSpeed();
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
   * Checks if a position is free. A square is considered free if
   * there is nothing in it, including a snake body part, walls, or food
   *
   * @param gridPoint The position to check
   *
   * @return true if the given position is not occupied and free for
   * anything to move into or be spawned at, false otherwise
   */
  public boolean isSquareFree(GridPoint gridPoint) {
    if (hasWallAt(gridPoint)) {
      return false;
    }

    // Check if position is on snakes body
    if (snake.getBody().contains(gridPoint)) {
      return false;
    }

    // return true if for all the positions of the foods there is no match the given argument
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
    GridPoint gridPoint = new GridPoint(x, y);

    if (hasWallAt(gridPoint)) {
      return getRandomFreeGridPoint();
    }

    if (snake.getBody().contains(gridPoint)) {
      return getRandomFreeGridPoint();
    }

    for (Food food : foodSpawner.getFoods()) {
      if (food.getPosition().equals(gridPoint)) {
        return getRandomFreeGridPoint();
      }
    }

    return gridPoint;
  }

  /**
   * Checks if there is a wall at the given position
   *
   * @param position the position to check
   *
   * @return true if there is a wall at the position, false otherwise
   */
  public boolean hasWallAt(GridPoint position) {
    // Check level walls
    if (walls.contains(position)) {
      return true;
    }

    // Check edge walls
    if (!hasEdgeWalls()) {
      return false;
    }

    int x = position.x();
    int y = position.y();
    return x == 0 || y == 0 || x == getGameFieldWidth() - 1 || y == getGameFieldHeight() - 1;
  }

  public List<GridPoint> getWalls() {
    return walls;
  }
}
