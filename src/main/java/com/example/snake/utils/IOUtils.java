package com.example.snake.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.example.snake.SnakeApplication;
import javafx.scene.image.Image;

public class IOUtils {

  public static Image loadImage(String path) {
    try (InputStream inputStream = SnakeApplication.class.getResourceAsStream(path)) {
      return new Image(Objects.requireNonNull(inputStream));
    } catch (IOException e) {
      throw new IllegalStateException("Could not load " + path, e);
    }
  }

  public static Clip loadAudioClip(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    InputStream inputStream = SnakeApplication.class.getResourceAsStream(path);

    if (inputStream == null) {
      throw new IOException("Failed to load " + path);
    }

    try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream))) {
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      return clip;
    }
  }

  private IOUtils() {
  }
}
