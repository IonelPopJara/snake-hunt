package com.example.snake.game;

import java.util.Collection;
import java.util.List;

import com.example.snake.graphics.Renderer;
import com.example.snake.model.Food;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;
import javafx.animation.AnimationTimer;

public class Game extends AnimationTimer {

  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 15;

  private final Renderer renderer;
  private final MovementController movementController;

  private final Snake snake;
  private final FoodSpawner foodSpawner;

  public Game(Renderer renderer, MovementController movementController) {
    this.renderer = renderer;
    this.movementController = movementController;
    this.foodSpawner = new FoodSpawner(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

    List<GridPoint> snakeBody = List.of(new GridPoint(10, 11), new GridPoint(11, 11));
    this.snake = new Snake(snakeBody, Direction.LEFT, 8.0f);
  }

  /**
   * The program needs to update the position of the snake and every element every second.
   * In order to do that we use a 'Game Loop'. This loop is called constantly, and it updates
   * all the elements in the screen.
   */
  @Override
  public void handle(long now) {
    // Divides nanoseconds into milliseconds
    long currentTime = now / 1_000_000;

    Direction direction = movementController.getDirection();
    if (direction != null) {
      snake.setDirection(direction, GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);
    }
    snake.update(currentTime, foodSpawner, GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

    foodSpawner.update(currentTime);

    renderer.draw(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, snake, foodSpawner.getFoods());
  }
}