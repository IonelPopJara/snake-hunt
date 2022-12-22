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

  private boolean isGameOver;
  private Runnable onGameOverHandle;

  public Game(Renderer renderer, MovementController movementController, Difficulty difficulty, Level level) {
    List<GridPoint> snakeBody = List.of(new GridPoint(10, 11), new GridPoint(11, 11));
    this.snake = new Snake(snakeBody, Direction.LEFT, difficulty.getSnakeMovementSpeed());
    this.startingSnakeSize = snakeBody.size();
    this.renderer = renderer;
    this.movementController = movementController;
    this.foodSpawner = new FoodSpawner();
    this.gameEnvironment = new GameEnvironment(difficulty, snake, foodSpawner, level);
  }

  @Override
  public void update(float delta) {

    // If isGameOver == true, it stops updating the game
    if (snake.isDead()) {
      if (!isGameOver) {
        isGameOver = true;
        onGameOverHandle.run();
      }
      return;
    }

    foodSpawner.update(delta, gameEnvironment);

    Direction direction = movementController.getDirection();
    if (direction != null) {
      snake.setDirection(direction, gameEnvironment);
    }
    snake.update(delta, gameEnvironment);

    renderer.draw(gameEnvironment);
  }

  public void setOnGameOverHandle(Runnable onGameOverHandle) {
    this.onGameOverHandle = onGameOverHandle;
  }

  public FoodSpawner getFoodSpawner() {
    return foodSpawner;
  }

  public int getScore() {
    return snake.getSize() - startingSnakeSize;
  }

  public Difficulty getDifficulty() {
    return gameEnvironment.getDifficulty();
  }
}