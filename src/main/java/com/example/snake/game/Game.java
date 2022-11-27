package com.example.snake.game;

import com.example.snake.graphics.Renderer;
import com.example.snake.model.Food;
import com.example.snake.model.GridPoint;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends AnimationTimer {

  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 15;

  private static final int FOOD_LIFETIME = 2;

  private final Renderer renderer;

  long lastTimeMoved = 0;
  int posX = 10;
  int posY = 10;
  private Direction direction = Direction.LEFT;

  Random random = new Random();

  private final List<Food> foods = new ArrayList<>();

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

      spawnFood(currentTime);
    }
    despawnFood(currentTime);

    //List<GridPoint> snake = List.of(new GridPoint(posX, 10), new GridPoint((posX + 1) % GAME_FIELD_WIDTH, 10), new GridPoint((posX + 2) % GAME_FIELD_WIDTH, 10));

    // Create some dummy data as an example, and a Renderer to draw it
    List<GridPoint> snake = List.of(new GridPoint(posX, posY));

    // RENDER
    renderer.draw(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, snake, foods);
  }

  private void despawnFood(long currentTime) {
    foods.removeIf(food -> !food.isAlive(currentTime));
  }

  private void spawnFood(long spawnTime) {
    if (foods.size() < 2) {
      foods.add(new Food(getRandomFreeGridPoint(), FOOD_LIFETIME, spawnTime));
    }
  }

  /**
   * @return an random unoccupied square
   */
  public GridPoint getRandomFreeGridPoint() {
    int x = random.nextInt(GAME_FIELD_WIDTH);
    int y = random.nextInt(GAME_FIELD_HEIGHT);

    for (Food food : foods) {
      if (food.getPosition().x() == x && food.getPosition().y() == y) {
        getRandomFreeGridPoint();
      }
    }
    return new GridPoint(x, y);
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
