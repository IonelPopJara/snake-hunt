package com.example.snake.sound;

import java.util.List;

import com.example.snake.utils.IOUtils;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

  private static final SoundManager INSTANCE = new SoundManager();

  private final MediaPlayer menuMusicPlayer = loadBackgroundMusic("/Sounds/menu-music.mp3");
  private final MediaPlayer inGameMusicPlayer = loadBackgroundMusic("/Sounds/gameplay-music.mp3");

  private final AudioClip eatingFood = IOUtils.loadAudioClip("/Sounds/food.wav");
  private final AudioClip eatingPrey = IOUtils.loadAudioClip("/Sounds/prey.wav");
  private final AudioClip gameOverSound = IOUtils.loadAudioClip("/Sounds/death.wav");
  private final AudioClip crunchSound = IOUtils.loadAudioClip("/Sounds/crunch.wav");
  private final AudioClip buttonSound = IOUtils.loadAudioClip("/Sounds/button-click.wav");

  private final List<AudioClip> soundFX = List.of(eatingFood, eatingPrey, gameOverSound, crunchSound, buttonSound);

  private SoundManager() {
    // Private constructor
    // This class cannot be instantiated elsewhere, using singleton pattern
  }

  /**
   * @return the singleton instance of the SoundManager
   */
  public static SoundManager getInstance() {
    return INSTANCE;
  }

  /**
   * Utility method to load background music into a media player
   */
  private static MediaPlayer loadBackgroundMusic(String path) {
    Media media = IOUtils.loadAudioMedia(path);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setCycleCount(Integer.MAX_VALUE);
    return mediaPlayer;
  }

  /**
   * Sets the volume of the background music
   */
  public void setBackgroundMusicVolume(double volume) {
    menuMusicPlayer.setVolume(volume);
    inGameMusicPlayer.setVolume(volume);
  }

  /**
   * Sets the volume of all sound effects
   */
  public void setSoundFxVolume(double volume) {
    for (AudioClip audioClip : soundFX) {
      audioClip.setVolume(volume);
    }
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

  public void playCrunchSound() {
    crunchSound.play();
  }
}
