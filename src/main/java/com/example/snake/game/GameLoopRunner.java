package com.example.snake.game;

import javafx.animation.AnimationTimer;

/**
 * The program needs to update the position of the snake and every element multiple times per second.
 * In order to do that we use a "Game Loop". This loop is running constantly, calling {@link Game#update(float)}
 * in order to update the game logic and render the graphics.
 */
public class GameLoopRunner extends AnimationTimer {

  private final GameLoop gameLoop;

  private long lastTickTime = 0;

  public GameLoopRunner(GameLoop gameLoop) {
    this.gameLoop = gameLoop;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handle(long now) {
    if (lastTickTime == 0) {
      lastTickTime = now;
    }

    // calculate delta and convert to seconds
    long nanoTimeDelta = now - lastTickTime;
    float delta = nanoTimeDelta / 1e9f;

    lastTickTime = now;

    gameLoop.update(delta);
  }
}
