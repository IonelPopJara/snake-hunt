package com.example.snake.model;

import com.example.snake.game.Direction;

import java.util.List;

public class Snake {
  static final int GAME_FIELD_WIDTH = 20;
  static final int GAME_FIELD_HEIGHT = 15;

  int posX = 10, posY = 10;

  private List<GridPoint> snakePoints;

  public Snake() {
    //snakePoints = List.of(new GridPoint(4, 6), new GridPoint(4,7), new GridPoint(4, 8));
    snakePoints = List.of(new GridPoint(posX, posY));
  }

  public int getSize() {
    return snakePoints.size();
  }

  public GridPoint getHead() {
    return snakePoints.get(0);
  }

  public GridPoint getPoint(int index) {
    return snakePoints.get(index);
  }

  public void move(Direction direction) {

    // probably delete this

    switch (direction) {
      case LEFT -> posX = (getHead().x() - 1 + GAME_FIELD_WIDTH) % GAME_FIELD_WIDTH;
      case RIGHT -> posX = (getHead().x() + 1 + GAME_FIELD_WIDTH) % GAME_FIELD_WIDTH;
      case UP -> posY = (getHead().y() - 1 + GAME_FIELD_HEIGHT) % GAME_FIELD_HEIGHT;
      case DOWN -> posY = (getHead().y() + 1 + GAME_FIELD_HEIGHT) % GAME_FIELD_HEIGHT;
    }

    snakePoints = List.of(new GridPoint(posX, posY));

  }
}

