package com.example.snake.utils;

import com.example.snake.SnakeApplication;
import com.example.snake.view.GameView;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

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
