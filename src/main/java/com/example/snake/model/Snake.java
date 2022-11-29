package com.example.snake.model;

import com.example.snake.game.Direction;

import java.util.LinkedList;
import java.util.List;

public class Snake {

  /*
  * TODO:
  *  Fix the snake class input.
  *  When you switch directions quickly, it goes back to itself
   */
  static final int GAME_FIELD_WIDTH = 20;
  static final int GAME_FIELD_HEIGHT = 15;


  private final List<GridPoint> snakeBody;

  public Snake(List<GridPoint> snakeBody) {
    if (snakeBody.size() < 2) {
      throw new IllegalArgumentException("Snake must have at least 2 body parts - a head and a tail");
    }

    this.snakeBody = new LinkedList<>(snakeBody);
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

  public void move(Direction direction) {

    // probably delete this

    GridPoint head = snakeBody.get(0);

    int posX = head.x();
    int posY = head.y();

    switch (direction) {
      case LEFT -> posX = (getHead().x() - 1 + GAME_FIELD_WIDTH) % GAME_FIELD_WIDTH;
      case RIGHT -> posX = (getHead().x() + 1 + GAME_FIELD_WIDTH) % GAME_FIELD_WIDTH;
      case UP -> posY = (getHead().y() - 1 + GAME_FIELD_HEIGHT) % GAME_FIELD_HEIGHT;
      case DOWN -> posY = (getHead().y() + 1 + GAME_FIELD_HEIGHT) % GAME_FIELD_HEIGHT;
    }

    snakeBody.add(0, new GridPoint(posX, posY));
    snakeBody.remove(snakeBody.size() - 1);
  }
}

