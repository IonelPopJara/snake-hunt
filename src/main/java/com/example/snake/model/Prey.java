package com.example.snake.model;

import com.example.snake.game.Direction;
import com.example.snake.game.GameEnvironment;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

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

  private Direction direction;

  /**
   * @param position      initial position of the prey
   * @param totalLifetime total lifetime of the pray, in seconds
   * @param movementSpeed movement speed of the prey, in moves per second
   */
  public Prey(GridPoint position, float totalLifetime, float movementSpeed) {
    super(position, totalLifetime, FoodType.PREY);
    this.moveInterval = 1.0f / movementSpeed;
    this.walkInterval = 1.0f / movementSpeed * 2;

    // Initial direction doesn't matter, assigning arbitrary value, so it's not null
    this.direction = Direction.LEFT;
  }

  @Override
  public void update(float delta, GameEnvironment gameEnvironment) {
    super.update(delta, gameEnvironment);
    movementTimer += delta;

    float distanceToSnake = gameEnvironment.getSnake().getHead().distance(getPosition());

    if (distanceToSnake <= RUN_PROXIMITY) {
      runAwayFromSnake(gameEnvironment);
    } else {
      walkInARandomDirection(gameEnvironment);
    }
  }

  private void walkInARandomDirection(GameEnvironment gameEnvironment) {
    if (movementTimer >= walkInterval) {
      movementTimer -= walkInterval;

      List<GridPoint> possibleMovePoints = getPossibleMoves(gameEnvironment);

      int randomIndex = RANDOM.nextInt(possibleMovePoints.size());
      move(possibleMovePoints.get(randomIndex));
    }
  }

  private void runAwayFromSnake(GameEnvironment gameEnvironment) {
    if (movementTimer >= moveInterval) {
      movementTimer -= moveInterval;

      getPossibleMoves(gameEnvironment).stream()
        .max(Comparator.comparing(gameEnvironment.getSnake().getHead()::distanceSquared))
        .ifPresent(this::move);
    }
  }

  /**
   * @return All possible move directions, mapped to their respective positions
   */
  private List<GridPoint> getPossibleMoves(GameEnvironment gameEnvironment) {
    return Arrays.stream(Direction.values())
      .map(Direction::getDirectionVector)
      .map(getPosition()::plus)
      .filter(e -> !e.isOutOfBounds(gameEnvironment.getGameFieldWidth(), gameEnvironment.getGameFieldHeight()))
      .filter(gameEnvironment::isSquareFree)
      .toList();
  }

  private void move(GridPoint position) {
    GridPoint previousPosition = getPosition();
    GridPoint directionVector = position.minus(previousPosition);
    direction = Direction.getByDirectionVector(directionVector);
    setPosition(position);
  }

  @Override
  public int getScoreValue() {
    // 4, 3 and 2 points when lifetime left is more or equal than 10, 5 and 0
    return 2 + (int) getRemainingLifetime() / 5;
  }
}
