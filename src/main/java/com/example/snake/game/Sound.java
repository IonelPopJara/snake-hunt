package com.example.snake.game;

import com.example.snake.utils.IOUtils;

import javax.sound.sampled.Clip;

public class Sound {
  private final Clip clip;

  public Sound(String path) {
    try {
      this.clip = IOUtils.loadAudioClip(path);

    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public Sound(String path, boolean shouldLoop) {
    try {
      this.clip = IOUtils.loadAudioClip(path);

      if (shouldLoop)
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);

    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public void playSound() {
    clip.flush();
    clip.setFramePosition(0);
    clip.start();
  }

  public void stopSound() {
    clip.stop();
    clip.flush();
    clip.setFramePosition(0);
  }
}
