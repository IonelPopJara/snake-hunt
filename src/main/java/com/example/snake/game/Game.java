package com.example.snake.game;

import java.util.List;

import com.example.snake.graphics.Renderer;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;
import com.example.snake.model.level.Level;

public class Game implements GameLoop {

  private final GameEnvironment gameEnvironment;

  private final Renderer renderer;
  private final MovementController movementController;

  private final Snake snake;
  private final FoodSpawner foodSpawner;

  private final int startingSnakeSize;

  public Game(Renderer renderer, MovementController movementController, Difficulty difficulty, Level level) {
    List<GridPoint> snakeBody = List.of(new GridPoint(10, 11), new GridPoint(11, 11));
    this.snake = new Snake(snakeBody, Direction.LEFT, difficulty.getSnakeMovementSpeed());
    this.startingSnakeSize = snakeBody.size();
    this.renderer = renderer;
    this.movementController = movementController;
    this.foodSpawner = new FoodSpawner();
    this.gameEnvironment = new GameEnvironment(difficulty, snake, foodSpawner, level);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(float delta) {
    foodSpawner.update(delta, gameEnvironment);

    Direction direction = movementController.getDirection();
    if (direction != null) {
      snake.setDirection(direction, gameEnvironment);
    }
    snake.update(delta, gameEnvironment);

    renderer.draw(gameEnvironment);
  }

  /**
   * see {@link FoodSpawner#getPreyLifetime()}
   */
  public float getPreyLifetime() {
    return foodSpawner.getPreyLifetime();
  }

  /**
   * @return the current score in the game
   */
  public int getScore() {
    return snake.getSize() - startingSnakeSize;
  }

  /**
   * @return the difficulty of the game
   */
  public Difficulty getDifficulty() {
    return gameEnvironment.getDifficulty();
  }

  /**
   * @return true if the game is over, false otherwise
   */
  public boolean isGameOver() {
    return snake.isDead();
  }
}