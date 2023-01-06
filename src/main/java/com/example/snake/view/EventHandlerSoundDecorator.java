package com.example.snake.view;

import com.example.snake.sound.SoundManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Event handler decorator that adds button press sound when invoked
 */
public class EventHandlerSoundDecorator implements EventHandler<ActionEvent> {

  private final EventHandler<ActionEvent> delegate;

  public EventHandlerSoundDecorator(EventHandler<ActionEvent> delegate) {
    this.delegate = delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handle(ActionEvent event) {
    SoundManager.getInstance().playButtonSound();
    delegate.handle(event);
  }
}
