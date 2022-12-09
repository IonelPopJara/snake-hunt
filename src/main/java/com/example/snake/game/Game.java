package com.example.snake.game;

import java.util.List;

import com.example.snake.graphics.Renderer;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;

public class Game {

  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 15;

  private final Renderer renderer;
  private final MovementController movementController;

  private final Snake snake;
  private final FoodSpawner foodSpawner;

  private boolean isGameOver;

  public Game(Renderer renderer, MovementController movementController) {
    this.renderer = renderer;
    this.movementController = movementController;
    this.foodSpawner = new FoodSpawner(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

    List<GridPoint> snakeBody = List.of(new GridPoint(10, 11), new GridPoint(11, 11));
    this.snake = new Snake(this, snakeBody, Direction.LEFT, 8.0f);

    isGameOver = false;
  }

  /**
   * @param delta The time difference between the last and the current frame, in seconds.
   */
  public void update(float delta) {

    // If isGameOver == true, it stops updating the game
    if(isGameOver) return;

    foodSpawner.update(delta, snake);

    Direction direction = movementController.getDirection();
    if (direction != null) {
      snake.setDirection(direction, GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);
    }
    snake.update(delta, foodSpawner, GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

    renderer.draw(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, snake, foodSpawner.getFoods());
  }

  public void gameOver() {
    if(isGameOver) return;

    isGameOver = true;
    System.out.println("Game Over");

    // If isGameOver == true, it stops updating the game and we can summon the gameOverScreen here
  }
}