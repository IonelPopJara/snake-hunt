package com.example.snake.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.example.snake.SnakeApplication;
import com.example.snake.player.PlayerScore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;

public class IOUtils {

  private static final String SCORE_FILE_NAME = "scores.json";

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

  public static List<PlayerScore> loadScores() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      File sourceFile = new File(SCORE_FILE_NAME);
      if (!sourceFile.exists()) {
        return Collections.emptyList();
      }

      return objectMapper.readValue(sourceFile, new TypeReference<>() {});
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  public static void saveScores(List<PlayerScore> scoresToSave) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      File resultFile = new File(SCORE_FILE_NAME);
      objectMapper.writeValue(resultFile, scoresToSave);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private IOUtils() {
  }
}
