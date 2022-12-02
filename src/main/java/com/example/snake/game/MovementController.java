package com.example.snake.game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class MovementController implements EventHandler<KeyEvent> {

  private boolean isLeftPressed;
  private boolean isRightPressed;
  private boolean isUpPressed;
  private boolean isDownPressed;


  private final List<Direction> inputBuffer = new ArrayList<>();

  public Direction getDirection() {
    if(inputBuffer.isEmpty())
      return null;
    return inputBuffer.get(inputBuffer.size() - 1);
  }

  @Override
  public void handle(KeyEvent e) {

    /*
     * Everytime a key is pressed, it adds it to the inputBuffer
     * Everytime a key is released, it removes it from the buffer
     * Then, the Game class uses an instance of this buffer
     */

    switch (e.getCode()) {
      case A, LEFT -> {
        if(e.getEventType() == KeyEvent.KEY_PRESSED && !isLeftPressed)
        {
          inputBuffer.add(Direction.LEFT);
          isLeftPressed = true;
        }
        else if(e.getEventType() == KeyEvent.KEY_RELEASED && isLeftPressed)
        {
          isLeftPressed = false;
          inputBuffer.remove(Direction.LEFT);
        }
      }
      case D, RIGHT -> {
        if(e.getEventType() == KeyEvent.KEY_PRESSED && !isRightPressed)
        {
          isRightPressed = true;
          inputBuffer.add(Direction.RIGHT);
        }
        else if(e.getEventType() == KeyEvent.KEY_RELEASED && isRightPressed)
        {
          isRightPressed = false;
          inputBuffer.remove(Direction.RIGHT);
        }
      }
      case W, UP -> {
        if(e.getEventType() == KeyEvent.KEY_PRESSED && !isUpPressed)
        {
          isUpPressed = true;
          inputBuffer.add(Direction.UP);
        }
        else if(e.getEventType() == KeyEvent.KEY_RELEASED && isUpPressed)
        {
          isUpPressed = false;
          inputBuffer.remove(Direction.UP);
        }
      }
      case S, DOWN -> {
        if(e.getEventType() == KeyEvent.KEY_PRESSED && !isDownPressed)
        {
          isDownPressed = true;
          inputBuffer.add(Direction.DOWN);
        }
        else if(e.getEventType() == KeyEvent.KEY_RELEASED && isDownPressed)
        {
          isDownPressed = false;
          inputBuffer.remove(Direction.DOWN);
        }
      }
    }
  }
}
