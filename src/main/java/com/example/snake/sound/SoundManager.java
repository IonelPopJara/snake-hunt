package com.example.snake.sound;

public class SoundManager {

  private static SoundManager instance = null;
  private final Sound menuMusic = new Sound("/BackgroundMusicMenu.wav", true);
  private final Sound eatingFood = new Sound("/EatingSound.wav");
  private final Sound eatingPrey = new Sound("/EatingPrey.wav");
  private final Sound inGameMusic = new Sound("/BackgroundMusic.wav");
  private final Sound gameOverSound = new Sound("/GameOver1.wav");
  private final Sound buttonSound = new Sound("/ButtonSound.wav");

  private SoundManager() {
    // Empty private constructor
    // This class cannot be instantiated
  }

  public static SoundManager getInstance() {
    if (instance == null) {
      instance = new SoundManager();
    }

    return instance;
  }

  public void playInGameMusic() {
    inGameMusic.playSound();
    menuMusic.stopSound();
  }

  public void stopInGameMusic() {
    inGameMusic.stopSound();
  }

  public void playMenuMusic() {
    menuMusic.playSound();
    inGameMusic.stopSound();
  }

  public void playEatingFoodSound() {
    eatingFood.playSound();
  }

  public void playEatingPreySound() {
    eatingPrey.playSound();
  }

  public void playGameOverSound() {
    gameOverSound.playSound();
  }
  public void playButtonSound() {
    buttonSound.playSound();
  }
}
