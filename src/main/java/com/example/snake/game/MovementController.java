package com.example.snake.game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MovementController implements EventHandler<KeyEvent> {

  private final List<Direction> inputBuffer = new ArrayList<>();

  public Direction getDirection() {
    if(inputBuffer.isEmpty())
      return null;
    return inputBuffer.get(inputBuffer.size() - 1);
  }

  @Override
  public void handle(KeyEvent e) {
    // Everytime a key is pressed, add it to a buffer in the game class
    // Then use that direction for game.setDirection();

    //"KEY_PRESSED"
    //"KEY_RELEASED"

//    if(e.getEventType() == KeyEvent.KEY_PRESSED) {
//      switch (e.getCode()) {
//        case A, LEFT -> inputBuffer.add(Direction.LEFT);
//      }
//    } else {
//
//    }

    switch (e.getCode()) {
      case A, LEFT -> {
        if(e.getEventType() == KeyEvent.KEY_PRESSED)
          inputBuffer.add(Direction.LEFT);
        else if(e.getEventType() == KeyEvent.KEY_RELEASED)
          inputBuffer.remove(Direction.LEFT);
      }
      case D, RIGHT -> {
        if(e.getEventType() == KeyEvent.KEY_PRESSED)
          inputBuffer.add(Direction.RIGHT);
        else if(e.getEventType() == KeyEvent.KEY_RELEASED)
          inputBuffer.remove(Direction.RIGHT);
      }
      case W, UP -> {
        if(e.getEventType() == KeyEvent.KEY_PRESSED)
          inputBuffer.add(Direction.UP);
        else if(e.getEventType() == KeyEvent.KEY_RELEASED)
          inputBuffer.remove(Direction.UP);
      }
      case S, DOWN -> {
        if(e.getEventType() == KeyEvent.KEY_PRESSED)
          inputBuffer.add(Direction.DOWN);
        else if(e.getEventType() == KeyEvent.KEY_RELEASED)
          inputBuffer.remove(Direction.DOWN);
      }
    }

    System.out.println("Buffer size: " + inputBuffer.size());
  }

//  public Direction removeLast() {}
}
