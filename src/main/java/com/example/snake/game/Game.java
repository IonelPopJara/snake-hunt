package com.example.snake.game;

import java.util.List;

import com.example.snake.graphics.Renderer;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;
import javafx.animation.AnimationTimer;

public class Game extends AnimationTimer {

  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 15;

  private final Renderer renderer;
  private final MovementController movementController;

  private final Snake snake = new Snake(List.of(new GridPoint(10, 10), new GridPoint(11, 10), new GridPoint(12, 11)),
                                        Direction.LEFT,
                                        GAME_FIELD_WIDTH,
                                        GAME_FIELD_HEIGHT, 125);

  private final FoodSpawner foodSpawner = new FoodSpawner(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

  public Game(Renderer renderer, MovementController movementController) {
    this.renderer = renderer;
    this.movementController = movementController;
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
      snake.setDirection(direction);
    }
    snake.update(currentTime);

    foodSpawner.update(currentTime);

    renderer.draw(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, snake, foodSpawner.getFoods());
  }
}