package com.example.snake.game;


import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class MovementController implements EventHandler<KeyEvent> {
  private final Game game;

  public MovementController(Game game) {
    this.game = game;
  }

  @Override
  public void handle(KeyEvent e) {
    switch (e.getCode()) {
      case A, LEFT -> {
        if (game.currentDirection() != Direction.RIGHT) game.setDirection(Direction.LEFT);
      }
      case D, RIGHT -> {
        if (game.currentDirection() != Direction.LEFT) game.setDirection(Direction.RIGHT);
      }
      case W, UP -> {
        if (game.currentDirection() != Direction.DOWN) game.setDirection(Direction.UP);
      }
      case S, DOWN -> {
        if (game.currentDirection() != Direction.UP) game.setDirection(Direction.DOWN);
      }
    }
  }
}
