package com.example.snake.model;

import java.util.Arrays;
import java.util.Comparator;

import com.example.snake.game.Direction;
import com.example.snake.game.GameEnvironment;

public class Prey extends Food {

  private final float moveInterval;

  private float movementTimer;

  public Prey(GridPoint position, float totalLifetime, float movementSpeed) {
    super(position, totalLifetime);
    this.moveInterval = 1.0f / movementSpeed;
  }

  @Override
  public void update(float delta, GameEnvironment gameEnvironment) {
    super.update(delta, gameEnvironment);
    movementTimer += delta;

    if (movementTimer >= moveInterval) {
      movementTimer -= moveInterval;

      Arrays.stream(Direction.values())
        .map(Direction::getDirectionVector)
        .map(getPosition()::plus)
        .filter(e -> !e.isOutOfBounds(gameEnvironment.getGameFieldWidth(), gameEnvironment.getGameFieldHeight()))
        .filter(gameEnvironment::isSquareFree)
        .max(Comparator.comparing(gameEnvironment.getSnake().getHead()::distanceSquared))
        .ifPresent(this::setPosition);
    }
  }

  @Override
  public FoodType getFoodType() {
    return FoodType.PREY;
  }
}
