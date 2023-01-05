package com.example.snake.sound;

import com.example.snake.utils.IOUtils;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Arrays;

public class SoundManager {
  private static final SoundManager INSTANCE = new SoundManager();

  private final MediaPlayer menuMusicPlayer = loadBackgroundMusic("/Sounds/menu-music.mp3");
  private final MediaPlayer inGameMusicPlayer = loadBackgroundMusic("/Sounds/gameplay-music.mp3");

  private final AudioClip eatingFood = IOUtils.loadAudioClip("/Sounds/food.wav");
  private final AudioClip eatingPrey = IOUtils.loadAudioClip("/Sounds/prey.wav");
  private final AudioClip gameOverSound = IOUtils.loadAudioClip("/Sounds/death.wav");
  private final AudioClip crunchSound = IOUtils.loadAudioClip("/Sounds/crunch.wav");
  private final AudioClip buttonSound = IOUtils.loadAudioClip("/Sounds/button-click.wav");
  private final ArrayList<AudioClip> soundFX = new ArrayList<AudioClip>(Arrays.asList(eatingFood, eatingPrey, gameOverSound, crunchSound, buttonSound));


  private SoundManager() {
    // Private constructor
    // This class cannot be instantiated elsewhere, using singleton pattern

    // Was way too loud imo
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
    this.eatingFood.play();
  }

  public void playEatingPreySound() {
    this.eatingPrey.play();
  }

  public void playGameOverSound() {
    this.gameOverSound.play();
  }

  public void playButtonSound() {
    this.buttonSound.play();
  }

  public void playCrunchSound() {
    this.crunchSound.play();
  }

  public MediaPlayer getMenuMusicPlayer() {
    return menuMusicPlayer;
  }

  public MediaPlayer getInGameMusicPlayer() {
    return inGameMusicPlayer;
  }

  public ArrayList getSoundFX() {
    return soundFX;
  }
}
