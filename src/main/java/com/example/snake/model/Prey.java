package com.example.snake.model;

import java.util.Arrays;
import java.util.Comparator;

import com.example.snake.game.Direction;

public class Prey extends Food {

  private final float moveInterval;

  private float movementTimer;

  public Prey(GridPoint position, float totalLifetime, float movementSpeed) {
    super(position, totalLifetime);
    this.moveInterval = 1.0f / movementSpeed;
  }

  @Override
  public void update(float delta, Snake snake, int gameFieldWidth, int gameFieldHeight) {
    super.update(delta, snake, gameFieldWidth, gameFieldHeight);
    movementTimer += delta;

    if (movementTimer >= moveInterval) {
      movementTimer -= moveInterval;

      Arrays.stream(Direction.values())
        .map(Direction::getDirectionVector)
        .map(getPosition()::plus)
        .filter(e -> !e.isOutOfBounds(gameFieldWidth, gameFieldHeight))
        .filter(e -> !snake.getBody().contains(e))
        .max(Comparator.comparing(snake.getHead()::distanceSquared))
        .ifPresent(this::setPosition);
    }
  }
}
