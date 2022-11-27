package com.example.snake.game;

import java.util.List;

import com.example.snake.graphics.Renderer;
import com.example.snake.model.GridPoint;
import javafx.animation.AnimationTimer;

public class Game extends AnimationTimer {

  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 15;

  private final Renderer renderer;

  long lastTimeMoved = 0;
  int posX = 10;
  int posY = 10;
  private Direction direction = Direction.LEFT;

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

    if (lastTimeMoved + moveInterval <= currentTime) {
      // UPDATE MOVEMENT
      switch (direction) {
        case LEFT -> posX = (posX - 1 + GAME_FIELD_WIDTH) % GAME_FIELD_WIDTH;
        case RIGHT -> posX = (posX + 1 + GAME_FIELD_WIDTH) % GAME_FIELD_WIDTH;
        case UP -> posY = (posY - 1 + GAME_FIELD_HEIGHT) % GAME_FIELD_HEIGHT;
        case DOWN -> posY = (posY + 1 + GAME_FIELD_HEIGHT) % GAME_FIELD_HEIGHT;
      }

      lastTimeMoved = currentTime;
    }

    foodSpawner.update(currentTime);

    List<GridPoint> snake = List.of(new GridPoint(posX, posY));

    renderer.draw(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, snake, foodSpawner.getFoods());
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
