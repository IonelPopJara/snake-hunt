package com.example.snake;

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

  private static final int MIN_WINDOW_WIDTH = 690;
  private static final int MIN_WINDOW_HEIGHT = 520;

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

  private void showLeaderboardView(Scene scene) {
    leaderboardView.reloadScores();
    scene.setRoot(leaderboardView.getRoot());
  }

  private void saveScore() {
    List<PlayerScore> playerScores = new ArrayList<>(IOUtils.loadScores());

    String playerName = gameView.getGameOverView().getSubmittedPlayerName();
    int score = currentGame.getScore();
    PlayerScore currentPlayerScore = new PlayerScore(playerName, score, currentGame.getDifficulty());
    playerScores.add(currentPlayerScore);

    List<PlayerScore> newPlayerScores = playerScores.stream()
      .sorted(Comparator.comparing(PlayerScore::getScore).reversed())
      .limit(10)
      .toList();

    IOUtils.saveScores(newPlayerScores);
  }

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
        gameView.getGameOverView().show();
        gameView.getGameOverView().setScoreLabel(currentGame.getScore());
        currentGameLoopRunner.stop();
      }
    });

    scene.setRoot(gameView.getRoot());
    currentGameLoopRunner.start();
  }

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