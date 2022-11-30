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
    // Everytime a key is pressed, add it to a buffer in the game class
    // Then use that direction for game.setDirection();

    switch (e.getCode()) {
      case A, LEFT -> {
        game.addKeyBuffer(Direction.LEFT);
//        game.setDirection(Direction.LEFT);
      }
      case D, RIGHT -> {
        game.addKeyBuffer(Direction.RIGHT);
//        game.setDirection(Direction.RIGHT);
      }
      case W, UP -> {
        game.addKeyBuffer(Direction.UP);
//        game.setDirection(Direction.UP);
      }
      case S, DOWN -> {
        game.addKeyBuffer(Direction.DOWN);
//        game.setDirection(Direction.DOWN);
      }
    }
  }
}
