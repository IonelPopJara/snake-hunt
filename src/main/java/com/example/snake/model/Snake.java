package com.example.snake.model;

import java.util.LinkedList;
import java.util.List;

import com.example.snake.game.Direction;
import com.example.snake.game.GameEnvironment;
import com.example.snake.sound.SoundManager;

public class Snake {

  private final List<GridPoint> body;
  private final float moveInterval;

  private Direction direction;
  private float movementTimer = 0.0f;
  private boolean isAlive;

  /**
   * @param body             an ordered list of adjacent body points where the first one is the head, with a minimum size of 2
   * @param initialDirection the initial direction of the snake
   * @param movementSpeed    number of times the snake moves per second
   */
  public Snake(List<GridPoint> body, Direction initialDirection, float movementSpeed) {
    if (body.size() < 2) {
      throw new IllegalArgumentException("Snake must have at least 2 body parts - a head and a tail");
    }

    // using linked list for constant time insertion at the first index
    this.body = new LinkedList<>(body);
    this.direction = initialDirection;
    this.moveInterval = 1.0f / movementSpeed;
    this.isAlive = true;
  }

  /**
   * @return the size of the snake in the number of body units, including the head
   */
  public int getSize() {
    return body.size();
  }

  /**
   * @return the position of the head
   */
  public GridPoint getHead() {
    return body.get(0);
  }

  /**
   * @return an ordered list of all the body points including the head, where the first element is the head
   */
  public List<GridPoint> getBody() {
    return body;
  }

  /**
   * Update the state of the snake with a given time step
   *
   * @param delta           the time step between this and the previous frame, in seconds
   * @param gameEnvironment the current game environment
   */
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
    // Moves the snake to the new position by calculating the new head position and adding it to the front of
    // the body as the new head. Removing the tail is handled conditionally when checking if food was eaten
    GridPoint nextPosition = calculateNextPosition(direction, gameEnvironment);
    body.add(0, nextPosition);
  }

  private void handleFood(GameEnvironment gameEnvironment) {
    Food foodEaten = gameEnvironment.getFood(getHead());

    if (foodEaten == null) {
      // If we have not eaten any food, we move the snake by simply by adding a new position at the head
      body.remove(body.size() - 1);
    } else {
      gameEnvironment.removeFood(foodEaten);
      playEatingSound(foodEaten);

      // If we have eaten food, we conversely grow by not removing the last position. We also add new ones
      // if the food is worth more than one point
      for (int i = 0; i < foodEaten.getScoreValue() - 1; i++) {
        body.add(body.get(body.size() - 1));
      }
    }
  }

  private void playEatingSound(Food foodEaten) {
    // Play crunch sound for any food type, together with sound specific for preys and regular foods respectively
    SoundManager.getInstance().playCrunchSound();
    if (foodEaten.getFoodType() == FoodType.PREY) {
      SoundManager.getInstance().playEatingPreySound();
    } else {
      SoundManager.getInstance().playEatingFoodSound();
    }
  }

  /**
   * Sets the direction of the snake. Has no effect if the direction is invalid, i.e. in the case the snake
   * would turn 180 degrees back into itself, which is an invalid move.
   *
   * @param direction       the direction to attempt to set
   * @param gameEnvironment the current game environment
   */
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
    // In order to calculate the next position, we add the direction vector to the head position, then
    // add the dimensions of the game field and mod by them, in order for the snake to wrap around the edges.
    // The equation would look like: x = (x + dirVector.x + gameFieldWidth) % gameFieldWidth
    return getHead().plus(direction.getDirectionVector())
      .plusAndMod(gameEnvironment.getGameFieldWidth(), gameEnvironment.getGameFieldHeight());
  }

  private void checkCollisions(GameEnvironment gameEnvironment) {
    // Check if head collides with walls or body, set isAlive to false if that's the case
    GridPoint head = getHead();
    isAlive = !gameEnvironment.hasWallAt(head) && !body.subList(1, body.size()).contains(head);
  }

  /**
   * @return true if snake is dead, false if it's alive
   */
  public boolean isDead() {
    return !this.isAlive;
  }

  /**
   * @return the direction that the snake will head towards on the next move
   */
  public Direction getDirection() {
    return this.direction;
  }
}
