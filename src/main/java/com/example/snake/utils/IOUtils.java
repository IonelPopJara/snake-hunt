package com.example.snake.utils;

import com.example.snake.SnakeApplication;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class IOUtils {
  public static Image loadImage(String path) {
    try (InputStream inputStream = SnakeApplication.class.getResourceAsStream(path)) {
      return new Image(Objects.requireNonNull(inputStream));
    } catch (IOException e) {
      throw new IllegalStateException("Could not load " + path, e);
    }
  }
}
