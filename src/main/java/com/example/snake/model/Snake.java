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
    // You can just use calculateNextPosition here
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
  public void checkCollisions(){
    //check if head collides with body
    for(int i = (snakeBody.size() - 1); i>0; i--){
      if(snakeBody.get(0).x() == snakeBody.get(i).x() && snakeBody.get(0).y() == snakeBody.get(i).y()){
        System.out.println("The head touched body");
      }
    }
  }
}

