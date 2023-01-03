package com.example.snake.sound;

import com.example.snake.utils.IOUtils;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

  private static final SoundManager INSTANCE = new SoundManager();

  private final MediaPlayer menuMusicPlayer = loadBackgroundMusic("/Sounds/BackgroundMusicMenu.wav");
  private final MediaPlayer inGameMusicPlayer = loadBackgroundMusic("/Sounds/BackgroundMusic.wav");

  private final AudioClip eatingFood = IOUtils.loadAudioClip("/Sounds/EatingSound.wav");
  private final AudioClip eatingPrey = IOUtils.loadAudioClip("/Sounds/EatingPrey.wav");
  private final AudioClip gameOverSound = IOUtils.loadAudioClip("/Sounds/GameOver1.wav");
  private final AudioClip buttonSound = IOUtils.loadAudioClip("/Sounds/ButtonSound.wav");

  private SoundManager() {
    // Private constructor
    // This class cannot be instantiated elsewhere, using singleton pattern

    // Was way too loud imo
    eatingFood.setVolume(0.3);
  }

  public static SoundManager getInstance() {
    return INSTANCE;
  }

  private static MediaPlayer loadBackgroundMusic(String path) {
    Media media = IOUtils.loadAudioMedia(path);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setCycleCount(Integer.MAX_VALUE);
    return mediaPlayer;
  }

  public void playInGameMusic() {
    inGameMusicPlayer.play();
    menuMusicPlayer.stop();
  }

  public void stopInGameMusic() {
    inGameMusicPlayer.stop();
  }

  public void playMenuMusic() {
    menuMusicPlayer.play();
    inGameMusicPlayer.stop();
  }

  public void playEatingFoodSound() {
    eatingFood.play();
  }

  public void playEatingPreySound() {
    eatingPrey.play();
  }

  public void playGameOverSound() {
    gameOverSound.play();
  }

  public void playButtonSound() {
    buttonSound.play();
  }
}
