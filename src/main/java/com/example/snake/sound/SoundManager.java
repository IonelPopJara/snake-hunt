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

  /**
   * Plays in game music, and stops menu music if it's playing
   */
  public void playInGameMusic() {
    inGameMusicPlayer.play();
    menuMusicPlayer.stop();
  }

  /**
   * Stops in game music
   */
  public void stopInGameMusic() {
    inGameMusicPlayer.stop();
  }

  /**
   * Plays menu music, and stops in game music if it's playing
   */
  public void playMenuMusic() {
    menuMusicPlayer.play();
    inGameMusicPlayer.stop();
  }

  /**
   * Plays the sound for eating regular food
   */
  public void playEatingFoodSound() {
    eatingFood.play();
  }

  /**
   * Plays the sound for eating prey
   */
  public void playEatingPreySound() {
    eatingPrey.play();
  }

  /**
   * Plays the game over sound
   */
  public void playGameOverSound() {
    gameOverSound.play();
  }

  /**
   * Plays the sound for button press
   */
  public void playButtonSound() {
    buttonSound.play();
  }

  /**
   * Plays the crunching sound for eating food
   */
  public void playCrunchSound() {
    crunchSound.play();
  }
}
