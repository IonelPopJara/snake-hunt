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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SnakeApplication extends Application {

  // Arbitrary dimensions for now
  private static final int WINDOW_WIDTH = 640;
  private static final int WINDOW_HEIGHT = 480;

  private final MainMenuView mainMenu = new MainMenuView();
  private final LeaderboardView leaderboardView = new LeaderboardView();
  private final OptionsView optionsView = new OptionsView();
  private final GameView gameView = new GameView(WINDOW_WIDTH, WINDOW_HEIGHT);

  private Game currentGame;
  private GameLoopRunner currentGameLoopRunner;

  private final Level defaultLevel = IOUtils.loadLevel("levels/hard_level.json");

  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(mainMenu.getRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);

    setUpEventHandlers(scene);

    // Disabled resizing for now
    stage.getIcons().add(IOUtils.loadImage("/icon.png"));
    stage.setResizable(false);
    stage.setTitle("Snake Hunt");
    stage.setScene(scene);
    stage.show();

    SoundManager.getInstance().playMenuMusic();
  }

  private void setUpEventHandlers(Scene scene) {
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

  // TODO: refactor more
  public void startGame(Scene scene, Difficulty difficulty) {
    if (currentGameLoopRunner != null) {
      currentGameLoopRunner.stop();
    }

    SoundManager.getInstance().playInGameMusic();

    Renderer renderer = new Renderer(gameView.getCanvas());

    MovementController movementController = new MovementController();
    scene.setOnKeyPressed(movementController);
    scene.setOnKeyReleased(movementController);

    currentGame = new Game(renderer, movementController, difficulty, getLevel(difficulty));
    currentGameLoopRunner = new GameLoopRunner(delta -> {
      currentGame.update(delta);
      gameView.setPreyLifetime(currentGame.getFoodSpawner().getPreyLifetime());
      gameView.setScoreLabel(currentGame.getScore());
    });

    currentGame.setOnGameOverHandle(() -> {
      gameView.getGameOverView().show();
      gameView.getGameOverView().setScoreLabel(currentGame.getScore());
    });

    scene.setRoot(gameView.getRoot());
    currentGameLoopRunner.start();
  }

  private Level getLevel(Difficulty difficulty) {
    return switch (difficulty) {
      case HARD -> defaultLevel;
      default -> Level.EMPTY;
    };
  }

  public static void main(String[] args) {
    launch();
  }
}