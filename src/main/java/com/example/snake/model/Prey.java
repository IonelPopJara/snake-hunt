package com.example.snake.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.example.snake.game.Direction;

public class Prey extends Food {

  /**
   * The maximum distance from the snakes head for the prey to run. If distance
   * is greater than this value, the prey will "walk" in random directions
   */
  private static final float RUN_PROXIMITY = 7.0f;

  private static final Random RANDOM = new Random();

  private final float moveInterval;
  private final float walkInterval;

  private float movementTimer;

  /**
   * @param position      initial position of the prey
   * @param totalLifetime total lifetime of the pray, in seconds
   * @param movementSpeed movement speed of the prey, in moves per second
   */
  public Prey(GridPoint position, float totalLifetime, float movementSpeed) {
    super(position, totalLifetime);
    this.moveInterval = 1.0f / movementSpeed;
    this.walkInterval = 1.0f / movementSpeed * 2;
  }

  @Override
  public void update(float delta, Snake snake, int gameFieldWidth, int gameFieldHeight) {
    super.update(delta, snake, gameFieldWidth, gameFieldHeight);
    movementTimer += delta;

    float distanceToSnake = snake.getHead().distance(getPosition());

    if (distanceToSnake <= RUN_PROXIMITY) {
      runAwayFromSnake(snake, gameFieldWidth, gameFieldHeight);
    } else {
      walkInARandomDirection(snake, gameFieldWidth, gameFieldHeight);
    }
  }

  private void walkInARandomDirection(Snake snake, int gameFieldWidth, int gameFieldHeight) {
    if (movementTimer >= walkInterval) {
      movementTimer -= walkInterval;

      List<GridPoint> possibleMovePoints = getPossibleMoves(snake, gameFieldWidth, gameFieldHeight);

      int randomIndex = RANDOM.nextInt(possibleMovePoints.size());
      setPosition(possibleMovePoints.get(randomIndex));
    }
  }

  private void runAwayFromSnake(Snake snake, int gameFieldWidth, int gameFieldHeight) {
    if (movementTimer >= moveInterval) {
      movementTimer -= moveInterval;

      getPossibleMoves(snake, gameFieldWidth, gameFieldHeight).stream()
        .max(Comparator.comparing(snake.getHead()::distanceSquared))
        .ifPresent(this::setPosition);
    }
  }

  /**
   * @return All possible move directions, mapped to their respective positions
   */
  private List<GridPoint> getPossibleMoves(Snake snake, int gameFieldWidth, int gameFieldHeight) {
    return Arrays.stream(Direction.values())
      .map(Direction::getDirectionVector)
      .map(getPosition()::plus)
      .filter(e -> !e.isOutOfBounds(gameFieldWidth, gameFieldHeight))
      .filter(e -> !snake.getBody().contains(e))
      .toList();
  }

  @Override
  public FoodType getFoodType() {
    return FoodType.PREY;
  }
}
