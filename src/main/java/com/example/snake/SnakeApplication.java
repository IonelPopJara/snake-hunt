package com.example.snake;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.example.snake.game.Difficulty;
import com.example.snake.game.Game;
import com.example.snake.game.GameLoopRunner;
import com.example.snake.game.MovementController;
import com.example.snake.graphics.Renderer;
import com.example.snake.model.level.Level;
import com.example.snake.player.PlayerScore;
import com.example.snake.sound.SoundManager;
import com.example.snake.utils.IOUtils;
import com.example.snake.view.GameView;
import com.example.snake.view.LeaderboardView;
import com.example.snake.view.MainMenuView;
import com.example.snake.view.OptionsView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeApplication extends Application {

  private static final int MIN_WINDOW_WIDTH = 640;
  private static final int MIN_WINDOW_HEIGHT = 480;

  private final MainMenuView mainMenu = new MainMenuView();
  private final LeaderboardView leaderboardView = new LeaderboardView();
  private final OptionsView optionsView = new OptionsView();
  private final GameView gameView = new GameView();

  private final Renderer renderer = new Renderer(gameView.getCanvas());
  private final MovementController movementController = new MovementController();

  private final Level defaultLevel = IOUtils.loadLevel("levels/hard_level.json");

  private Game currentGame;
  private GameLoopRunner currentGameLoopRunner;

  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(mainMenu.getRoot(), MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);

    setUpEventHandlers(scene);

    stage.getIcons().add(IOUtils.loadImage("/UI/icon.png"));
    stage.setTitle("Snake Hunt");
    stage.setScene(scene);
    stage.setMinWidth(MIN_WINDOW_WIDTH);
    stage.setMinHeight(MIN_WINDOW_HEIGHT);
    stage.show();

    SoundManager.getInstance().playMenuMusic();
  }

  /**
   * Set up event handlers
   */
  private void setUpEventHandlers(Scene scene) {
    scene.setOnKeyPressed(movementController);
    scene.setOnKeyReleased(movementController);

    mainMenu.onStartButtonPressed(difficulty -> startGame(scene, difficulty));
    mainMenu.onOptionsButtonPressed(event -> scene.setRoot(optionsView.getRoot()));
    mainMenu.onLeaderboardButtonPressed(event -> showLeaderboardView(scene));

    leaderboardView.onMainMenuButtonPressed(event -> scene.setRoot(mainMenu.getRoot()));

    optionsView.onMainMenuButtonPressed(event -> scene.setRoot(mainMenu.getRoot()));

    gameView.getGameOverView().onMainMenuButtonPressed(event -> scene.setRoot(mainMenu.getRoot()));
    gameView.getGameOverView().onStartButtonPressed(event -> startGame(scene, currentGame.getDifficulty()));
    gameView.getGameOverView().setOnSubmitScoreButtonPressed(event -> saveScore());
  }

  /**
   * Show the leaderboard, ensuring the scores are up to date
   */
  private void showLeaderboardView(Scene scene) {
    leaderboardView.reloadScores();
    scene.setRoot(leaderboardView.getRoot());
  }

  /**
   * Saves the new score upon submitting
   */
  private void saveScore() {
    // Not really the correct level of abstraction for this method to be here, but it's a short one, so it's alright i guess
    // Anyway, get the current list of top scores first
    List<PlayerScore> playerScores = new ArrayList<>(IOUtils.loadScores());

    // Add the new score to the list
    String playerName = gameView.getGameOverView().getSubmittedPlayerName();
    int score = currentGame.getScore();
    playerScores.add(new PlayerScore(playerName, score, currentGame.getDifficulty()));

    // Sort by score and limit the size of the list to 10
    List<PlayerScore> newPlayerScores = playerScores.stream()
      .sorted(Comparator.comparing(PlayerScore::getScore).reversed())
      .limit(10)
      .toList();

    // And save to file
    IOUtils.saveScores(newPlayerScores);
  }

  /**
   * Starts a new game, changing the contents of the scene to the game view
   */
  public void startGame(Scene scene, Difficulty difficulty) {
    SoundManager.getInstance().playInGameMusic();

    currentGame = new Game(renderer, movementController, difficulty, getLevel(difficulty));
    currentGameLoopRunner = new GameLoopRunner(delta -> {
      // Update the game on each iteration
      currentGame.update(delta);

      // Set the ui elements
      gameView.setPreyLifetime(currentGame.getPreyLifetime());
      gameView.setScoreLabel(currentGame.getScore());

      // Stop if game is over
      if (currentGame.isGameOver()) {
        SoundManager.getInstance().playGameOverSound();
        SoundManager.getInstance().stopInGameMusic();
        gameView.getGameOverView().show();
        gameView.getGameOverView().setScoreLabel(currentGame.getScore());
        currentGameLoopRunner.stop();
      }
    });

    scene.setRoot(gameView.getRoot());
    currentGameLoopRunner.start();
  }

  /**
   * @return the level based on the difficulty
   */
  private Level getLevel(Difficulty difficulty) {
    if (difficulty == Difficulty.HARD) {
      return defaultLevel;
    }

    return Level.EMPTY;
  }

  public static void main(String[] args) {
    launch();
  }
}