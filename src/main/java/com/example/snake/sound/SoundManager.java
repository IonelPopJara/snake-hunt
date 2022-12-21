package com.example.snake.sound;

public class SoundManager {

  private static SoundManager instance = null;
  private final Sound backgroundMusicMenu = new Sound("/BackgroundMusicMenu.wav", true);
  private final Sound eatingFood = new Sound("/EatingSound.wav");
  private final Sound eatingPrey = new Sound("/EatingPrey.wav");
  private final Sound backgroundMusicInGame = new Sound("/BackgroundMusic.wav");
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
    backgroundMusicInGame.playSound();
  }

  public void stopInGameMusic() {
    backgroundMusicInGame.stopSound();
  }

  public void playMainMenuMusic() {
    backgroundMusicMenu.playSound();
    // And stop the game music
  }

  public void stopMainMenuMusic() {
    backgroundMusicMenu.stopSound();
    // And play the game music
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

  public void stopGameOverSound() {
    gameOverSound.stopSound();
  }

  public void playButtonSound() {
    buttonSound.playSound();
  }
}
