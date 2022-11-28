package com.example.snake.game;

import com.example.snake.graphics.Renderer;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;
import javafx.animation.AnimationTimer;

import java.util.List;

public class Game extends AnimationTimer {

  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 15;

  private final Renderer renderer;

  long lastTimeMoved = 0;

  private Direction direction = Direction.LEFT;

  private Snake snake = new Snake(List.of(new GridPoint(10, 10), new GridPoint(11, 10), new GridPoint(12, 11)));

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
    // Update the movement
    // Render things

    long currentTime = now / 1_000_000; // Divides nanoseconds into milliseconds

    long moveInterval = 250;

    if (lastTimeMoved + moveInterval <= currentTime) {
      // UPDATE MOVEMENT
      snake.move(direction);

      lastTimeMoved = currentTime;
    }

    // Create some dummy data as an example, and a Renderer to draw it
    List<GridPoint> foods = List.of(new GridPoint(7, 5), new GridPoint(7, 4));

    //List<GridPoint> snake = List.of(new GridPoint(posX, posY));

    renderer.draw(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, snake, foods);
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Direction currentDirection() {
    return direction;
  }
}