package com.example.snake.game;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class MovementController implements EventHandler<KeyEvent> {

  private final List<Direction> inputBuffer = new ArrayList<>();

  /**
   * @return The direction enum of key pressed, or null if no key is pressed
   */
  public Direction getDirection() {
    if (inputBuffer.isEmpty()) {
      return null;
    }

    // Return the last element of the list, which is the key that was pressed last.
    // Results in a more responsive experience
    return inputBuffer.get(inputBuffer.size() - 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handle(KeyEvent e) {
    // Everytime a key is pressed, it adds it to the inputBuffer
    // Everytime a key is released, it removes it from the buffer

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
