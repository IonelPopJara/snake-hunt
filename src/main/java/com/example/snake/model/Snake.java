package com.example.snake.model;

import com.example.snake.game.Direction;
import com.example.snake.game.GameEnvironment;
import com.example.snake.sound.SoundManager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Snake {

  private final List<GridPoint> body;
  private final float moveInterval;

  private Direction direction;
  private float movementTimer = 0.0f;
  private boolean isAlive;
  private int delayedFood = 0;

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

  public void update(float delta, GameEnvironment gameEnvironment) {
    movementTimer += delta;
    if (movementTimer >= moveInterval) {
      movementTimer -= moveInterval;

      moveSnake(gameEnvironment);
      handleFood(gameEnvironment);
      checkCollisions(gameEnvironment);
    }
  }

  private void moveSnake(GameEnvironment gameEnvironment) {
    GridPoint nextPosition = calculateNextPosition(direction, gameEnvironment);
    body.add(0, nextPosition);
  }

  private void handleFood(GameEnvironment gameEnvironment) {
    Food foodEaten = checkFood(gameEnvironment.getFoods());

    if(foodEaten != null) {
      delayedFood += foodEaten.getScoreValue();
      foodSpawner.removeFood(foodEaten);
    }

    if (delayedFood == 0) {
      body.remove(body.size() - 1);
    }

    if (delayedFood > 0) {
      delayedFood--;
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

  public void setDirection(Direction direction, GameEnvironment gameEnvironment) {
    // Calculate what the next position would be, if we were to move in the given direction
    GridPoint nextHypotheticalPosition = calculateNextPosition(direction, gameEnvironment);

    // If that position is the same as the second part of the snake, then the snake would move
    // back into itself, which we do not want to happen
    GridPoint secondBodyPoint = body.get(1);
    if (!nextHypotheticalPosition.equals(secondBodyPoint)) {
      this.direction = direction;
    }
  }

  private GridPoint calculateNextPosition(Direction direction, GameEnvironment gameEnvironment) {
    // same calculations as in the update method
    return getHead().plus(direction.getDirectionVector())
      .plusAndMod(gameEnvironment.getGameFieldWidth(), gameEnvironment.getGameFieldHeight());
  }

  public void checkCollisions(GameEnvironment gameEnvironment) {
    if (gameEnvironment.hasWallAt(getHead())) {
      isAlive = false;
      return;
    }

    //check if head collides with body
    for (int i = (body.size() - 1); i > 0; i--) {
      if (body.get(0).equals(body.get(i))) {
        isAlive = false;
        break;
      }
    }
  }

  public boolean isDead() {
    return !this.isAlive;
  }
}
