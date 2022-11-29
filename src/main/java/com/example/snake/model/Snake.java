package com.example.snake.model;

import com.example.snake.game.Direction;

import java.util.LinkedList;
import java.util.List;

public class Snake {

  /**
   * Direction that the snake will head towards in the next update
   */
  private Direction direction;

  private final int gameFieldWidth;
  private final int gameFieldHeight;

  private final List<GridPoint> snakeBody;

  public Snake(List<GridPoint> snakeBody, Direction initialDirection, int gameFieldWidth, int gameFieldHeight) {
    if (snakeBody.size() < 2) {
      throw new IllegalArgumentException("Snake must have at least 2 body parts - a head and a tail");
    }

    this.snakeBody = new LinkedList<>(snakeBody);
    this.direction = initialDirection;
    this.gameFieldWidth = gameFieldWidth;
    this.gameFieldHeight = gameFieldHeight;
  }

  public int getSize() {
    return snakeBody.size();
  }

  public GridPoint getHead() {
    return snakeBody.get(0);
  }

  public GridPoint getPoint(int index) {
    return snakeBody.get(index);
  }

  public void update() {
    GridPoint head = snakeBody.get(0);

    int posX = head.x();
    int posY = head.y();

    switch (direction) {
      case LEFT -> posX = (getHead().x() - 1 + gameFieldWidth) % gameFieldWidth;
      case RIGHT -> posX = (getHead().x() + 1 + gameFieldWidth) % gameFieldWidth;
      case UP -> posY = (getHead().y() - 1 + gameFieldHeight) % gameFieldHeight;
      case DOWN -> posY = (getHead().y() + 1 + gameFieldHeight) % gameFieldHeight;
    }

    snakeBody.add(0, new GridPoint(posX, posY));
    snakeBody.remove(snakeBody.size() - 1);
  }

  public void setDirection(Direction direction) {
    // Derive the direction the first two body parts are "pointing" towards. Then make sure that the asked direction
    // is not the opposite of that (in which case the snake would go back into itself)
    Direction bodyDirection = deriveBodyDirection();
    if (!bodyDirection.isOppositeOf(direction)) {
      this.direction = direction;
    }
  }

  private Direction deriveBodyDirection() {
    // TODO: handle edge cases, meaning: head on one end, second body part on the other. If the field dimensions
    //  are not needed here, then remove the fields. Pass them as arguments to the update method imo
    GridPoint head = getHead();
    GridPoint secondBodyPoint = snakeBody.get(1);

    GridPoint directionVector = head.minus(secondBodyPoint);

    return Direction.getByDirectionVector(directionVector);
  }
}

