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
  private long lastTimeMoved = 0;

  private final Snake snake = new Snake(List.of(new GridPoint(10, 10), new GridPoint(11, 10), new GridPoint(12, 11), new GridPoint(13, 11), new GridPoint(14, 11)),
                                        Direction.LEFT,
                                        GAME_FIELD_WIDTH,
                                        GAME_FIELD_HEIGHT);

  private final FoodSpawner foodSpawner = new FoodSpawner(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

  public Game(Renderer renderer) {
    this.renderer = renderer;
  }

  /**
   * The program needs to update the position of the snake and every element every second.
   * In order to do that we use a 'Game Loop'. This loop is called constantly, and it updates
   * all the elements in the screen.
   */
  @Override
  public void handle(long now) {

    long currentTime = now / 1_000_000; // Divides nanoseconds into milliseconds

    long moveInterval = 250;

    // TODO: move this if check into the snake class, as the speed of the snake is a property
    //  of that class. Also, this method of checking the time can be inconsistent (matter of milliseconds though)
    if (lastTimeMoved + moveInterval <= currentTime) {
      // UPDATE MOVEMENT
      snake.update();
      snake.checkCollisions();
      lastTimeMoved = currentTime;
    }

    foodSpawner.update(currentTime);

    renderer.draw(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, snake, foodSpawner.getFoods());
  }

  public void setDirection(Direction direction) {
    snake.setDirection(direction);
  }
}