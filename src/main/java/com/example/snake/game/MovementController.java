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
      case A, LEFT -> game.setDirection(Direction.LEFT);
      case D, RIGHT -> game.setDirection(Direction.RIGHT);
      case W, UP -> game.setDirection(Direction.UP);
      case S, DOWN -> game.setDirection(Direction.DOWN);
    }
  }
}
