package com.example.snake.game;

public interface GameLoop {

  /**
   * @param delta The time difference between the last and the current frame, in seconds.
   */
  void update(float delta);
}
