package com.example.snake.model;

import com.example.snake.game.Direction;
import com.example.snake.game.FoodSpawner;
import com.example.snake.utils.IOUtils;

import javax.sound.sampled.Clip;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Snake {

  private final List<GridPoint> body;
  private final float moveInterval;

  private Direction direction;
  private float movementTimer = 0.0f;
  private boolean isAlive;

  /**
   * @param movementSpeed Number of times the snake moves per second
   */
  public Snake(List<GridPoint> body, Direction initialDirection, float movementSpeed) {
    if (body.size() < 2) {
      throw new IllegalArgumentException("Snake must have at least 2 body parts - a head and a tail");
    }

    this.body = new LinkedList<>(body);
    this.direction = initialDirection;
    this.moveInterval = 1.0f / movementSpeed;
    this.isAlive = true;
  }

  public int getSize() {
    return body.size();
  }

  public GridPoint getHead() {
    return body.get(0);
  }

  public GridPoint getPoint(int index) {
    return body.get(index);
  }

  public Collection<GridPoint> getBody() {
    return body;
  }

  public void update(float delta, FoodSpawner foodSpawner, int gameFieldWidth, int gameFieldHeight) {
    movementTimer += delta;
    if (movementTimer >= moveInterval) {
      movementTimer -= moveInterval;

      moveSnake(gameFieldWidth, gameFieldHeight);
      handleFood(foodSpawner);
      checkCollisions();
    }
  }

  private void moveSnake(int gameFieldWidth, int gameFieldHeight) {
    GridPoint nextPosition = calculateNextPosition(direction, gameFieldWidth, gameFieldHeight);
    body.add(0, nextPosition);
  }

  private void handleFood(FoodSpawner foodSpawner) {
    Food foodEaten = checkFood(foodSpawner.getFoods());

    if (foodEaten == null) {
      body.remove(body.size() - 1);
    } else {
      switch (foodEaten.getFoodType()) {
        case FOOD -> playEatingSound();
        case PREY -> playEatingPreySound();
      }
      foodSpawner.removeFood(foodEaten);
    }
  }

  private Food checkFood(Collection<Food> foods) {
    for (Food food : foods) {
      if (food.getPosition().equals(getHead())) {
        return food;
      }
    }
    return null;
  }

  public void setDirection(Direction direction, int gameFieldWidth, int gameFieldHeight) {
    // Calculate what the next position would be, if we were to move in the given direction
    GridPoint nextHypotheticalPosition = calculateNextPosition(direction, gameFieldWidth, gameFieldHeight);

    // If that position is the same as the second part of the snake, then the snake would move
    // back into itself, which we do not want to happen
    GridPoint secondBodyPoint = body.get(1);
    if (!nextHypotheticalPosition.equals(secondBodyPoint)) {
      this.direction = direction;
    }
  }

  private GridPoint calculateNextPosition(Direction direction, int gameFieldWidth, int gameFieldHeight) {
    // same calculations as in the update method
    return getHead().plus(direction.getDirectionVector()).plusAndMod(gameFieldWidth, gameFieldHeight);
  }

  public void checkCollisions() {
    //check if head collides with body
    for (int i = (body.size() - 1); i > 0; i--) {
      if (body.get(0).equals(body.get(i))) {
        gameOver();
      }
    }
  }

  private void gameOver() {
    this.isAlive = false;
  }

  public boolean isDead() {
    return !this.isAlive;
  }

  public void playEatingSound() {
    try {
      Clip clip = IOUtils.loadAudioClip("/EatingSound.wav");
      clip.start();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public void playEatingPreySound() {
    try {
      Clip clip = IOUtils.loadAudioClip("/EatingPrey.wav");
      clip.start();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}
