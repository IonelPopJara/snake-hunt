package com.example.snake.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.example.snake.SnakeApplication;
import com.example.snake.model.level.Level;
import com.example.snake.player.PlayerScore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

public class IOUtils {

  private static final String SCORE_FILE_NAME = "scores.json";

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static Image loadImage(String path) {
    try (InputStream inputStream = SnakeApplication.class.getResourceAsStream(path)) {
      return new Image(Objects.requireNonNull(inputStream));
    } catch (IOException e) {
      throw new IllegalStateException("Could not load " + path, e);
    }
  }

  public static AudioClip loadAudioClip(String path) {
    URL resourceURL = SnakeApplication.class.getResource(path);

    if (resourceURL == null) {
      throw new IllegalStateException("Failed to load " + path);
    }

    return new AudioClip(resourceURL.toExternalForm());
  }

  public static Media loadAudioMedia(String path) {
    URL resourceURL = SnakeApplication.class.getResource(path);

    if (resourceURL == null) {
      throw new IllegalStateException("Failed to load " + path);
    }

    return new Media(resourceURL.toExternalForm());
  }

  public static List<PlayerScore> loadScores() {
    try {
      File sourceFile = new File(SCORE_FILE_NAME);
      if (!sourceFile.exists()) {
        return Collections.emptyList();
      }

      return OBJECT_MAPPER.readValue(sourceFile, new TypeReference<>() {});
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  public static void saveScores(List<PlayerScore> scoresToSave) {
    try {
      File resultFile = new File(SCORE_FILE_NAME);
      OBJECT_MAPPER.writeValue(resultFile, scoresToSave);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Level loadLevel(String path) {
    try {
      return OBJECT_MAPPER.readValue(new File(path), Level.class);
    } catch (IOException e) {
      throw new IllegalStateException("File could not be loaded: " + path, e);
    }
  }

  private IOUtils() {
  }
}
