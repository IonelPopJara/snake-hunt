package com.example.snake;

import com.example.snake.game.Game;
import com.example.snake.game.MovementController;
import com.example.snake.graphics.Renderer;
import com.example.snake.menu.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SnakeApplication extends Application {

  // Arbitrary dimensions for now
  private static final int WINDOW_WIDTH = 640;
  private static final int WINDOW_HEIGHT = 480;

  @Override
  public void start(Stage stage) {

    MainMenu mainMenu = new MainMenu();
    Scene scene = new Scene(mainMenu.getMenuRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);
//    Label message = new Label("snake game");
//    message.setStyle("-fx-border-color: blue; -fx-border-width: 2px; " +
//      "-fx-background-color: white; -fx-padding: 6px");

    mainMenu.onStartPressed(event -> startGame(stage));
    mainMenu.onOptionsPressed(event -> showOptionsMenu(stage));
    mainMenu.onLeaderboardPressed(event -> showLeaderboardMenu(stage));

    // Disabled resizing for now
    stage.setResizable(false);
    stage.setTitle("Snake Base");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

  public static void startGame(Stage stage) {
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TilePane root = new TilePane(canvas);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    Renderer renderer = new Renderer(canvas);

    MovementController movementController = new MovementController();
    scene.setOnKeyPressed(movementController);
    scene.setOnKeyReleased(movementController);
    Game currentGame = new Game(renderer, movementController);

    stage.setScene(scene);

    currentGame.start();

  }

  public static void showOptionsMenu(Stage stage) {
    // Create the layout for the options menu here
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TilePane root = new TilePane(canvas);

    root.setBackground(Background.fill(Color.valueOf("3FA7D6")));

    // Set the background to a placeholder color for now
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    stage.setScene(scene);
  }

  public static void showLeaderboardMenu(Stage stage) {
    // Create the layout for the leaderboard(high scores) menu here
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TilePane root = new TilePane(canvas);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Set the background to a placeholder color for now
    root.setBackground(Background.fill(Color.valueOf("59CD90")));

    stage.setScene(scene);
  }
}