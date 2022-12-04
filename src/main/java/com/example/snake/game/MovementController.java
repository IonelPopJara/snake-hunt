package com.example.snake.game;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class MovementController implements EventHandler<KeyEvent> {

  private final List<Direction> inputBuffer = new ArrayList<>();

  public Direction getDirection() {
    if (inputBuffer.isEmpty()) {
      return null;
    }

    return inputBuffer.get(inputBuffer.size() - 1);
  }

  @Override
  public void handle(KeyEvent e) {
    // Everytime a key is pressed, it adds it to the inputBuffer
    // Everytime a key is released, it removes it from the buffer
    // Then, the Game class uses an instance of this buffer

    Direction direction = switch (e.getCode()) {
      case A, LEFT -> Direction.LEFT;
      case D, RIGHT -> Direction.RIGHT;
      case W, UP -> Direction.UP;
      case S, DOWN -> Direction.DOWN;
      default -> null;
    };

    if (direction != null) {
      // Only add the direction once. The event is fired multiple times if key is held down
      if (e.getEventType() == KeyEvent.KEY_PRESSED && !inputBuffer.contains(direction)) {
        inputBuffer.add(direction);
      } else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
        inputBuffer.remove(direction);
      }
    }
  }
}
