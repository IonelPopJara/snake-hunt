package com.example.snake.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.example.snake.graphics.Renderer;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;
import javafx.animation.AnimationTimer;

public class Game extends AnimationTimer {


  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 15;

  private static final long BUFFER_CLEANING_TIME = 700;

  public static Queue<Direction> inputBuffer = new LinkedList<>();
  private long lastTimeCleaned = 0;

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

    long currentTime = now / 1_000_000; // Divides nanoseconds into milliseconds

    updateBuffer(currentTime);

    Direction direction = movementController.getDirection();
    if(direction!= null) {
      snake.setDirection(direction);
    }
    snake.update(currentTime);

    foodSpawner.update(currentTime);

    renderer.draw(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, snake, foodSpawner.getFoods());
  }

  public void setDirection(Direction direction) {
    snake.setDirection(direction);
  }

  public void addKeyBuffer(Direction direction) {
    inputBuffer.add(direction);
  }

  private void updateBuffer(long currentTime) {

    if(lastTimeCleaned + BUFFER_CLEANING_TIME <= currentTime)
    {
      inputBuffer.clear();
      lastTimeCleaned = currentTime;
    }
  }
}