package com.example.snake.model;

import com.example.snake.game.Direction;
import com.example.snake.game.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Snake {

  /**
   * Direction that the snake will head towards in the next update
   */
  private Direction direction;

  private final List<GridPoint> snakeBody;

  private final int gameFieldWidth;
  private final int gameFieldHeight;
  private final long snakeSpeed;
  private long lastTimeMoved = 0;

  /***
   *  @param snakeBody A list of GridPoints
   * @param initialDirection Usually starts going to the Left
   * @param gameFieldWidth
   * @param gameFieldHeight
   * @param snakeSpeed The lower the number the faster it goes
   */
  public Snake(List<GridPoint> snakeBody, Direction initialDirection, int gameFieldWidth, int gameFieldHeight, long snakeSpeed) {
    if (snakeBody.size() < 2) {
      throw new IllegalArgumentException("Snake must have at least 2 body parts - a head and a tail");
    }

    this.snakeBody = new LinkedList<>(snakeBody);
    this.direction = initialDirection;
    this.gameFieldWidth = gameFieldWidth;
    this.gameFieldHeight = gameFieldHeight;
    this.snakeSpeed = snakeSpeed;
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

  public void update(long currentTime) {

    // TODO: This method of checking the time can be inconsistent (matter of milliseconds though)
    if (lastTimeMoved + snakeSpeed <= currentTime) {
      // UPDATE MOVEMENT
      // You can just use calculateNextPosition here
      GridPoint head = snakeBody.get(0);

      int posX = head.x();
      int posY = head.y();

      // ask for direction from the buffer here
      inputUpdate();

      switch (direction) {
        case LEFT -> posX = (getHead().x() - 1 + gameFieldWidth) % gameFieldWidth;
        case RIGHT -> posX = (getHead().x() + 1 + gameFieldWidth) % gameFieldWidth;
        case UP -> posY = (getHead().y() - 1 + gameFieldHeight) % gameFieldHeight;
        case DOWN -> posY = (getHead().y() + 1 + gameFieldHeight) % gameFieldHeight;
      }

      snakeBody.add(0, new GridPoint(posX, posY));
      snakeBody.remove(snakeBody.size() - 1);

      lastTimeMoved = currentTime;
    }
  }

  private void inputUpdate() {
    if(Game.inputBuffer.size() > 0) {
      setDirection(Game.inputBuffer.remove());
    }
//    System.out.println(Game.inputBuffer);
  }

  public void setDirection(Direction direction) {
    // Calculate what the next position would be, if we were to move in the given direction
    GridPoint nextHypotheticalPosition = calculateNextPosition(direction);

    // If that position is the same as the second part of the snake, then the snake would move
    // back into itself, which we do not want to happen
    GridPoint secondBodyPoint = snakeBody.get(1);
    if (!nextHypotheticalPosition.equals(secondBodyPoint)) {
      this.direction = direction;
    }
  }

  private GridPoint calculateNextPosition(Direction direction) {
    // same calculations as in the update method
    return getHead().plus(direction.getDirectionVector()).plusAndMod(gameFieldWidth, gameFieldHeight);
  }
}

