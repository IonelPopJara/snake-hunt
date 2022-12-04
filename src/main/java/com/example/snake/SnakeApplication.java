package com.example.snake;

import com.example.snake.game.Game;
import com.example.snake.game.MovementController;
import com.example.snake.graphics.Renderer;
import com.example.snake.menu.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SnakeApplication extends Application {

  // Arbitrary dimensions for now
  private static final int WINDOW_WIDTH = 640;
  private static final int WINDOW_HEIGHT = 480;


  @Override
  public void start(Stage stage) {


    MainMenu mainMenu = new MainMenu();
    Scene scene = new Scene(mainMenu.getMenuRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);

    mainMenu.onStartPressed(event -> startGame(stage));
    mainMenu.onOptionsPressed(event -> showOptionsMenu(stage));
    mainMenu.onLeaderboardPressed(event -> showLeaderboardMenu(stage));

    // Disabled resizing for now
    stage.getIcons().add(loadImage("/icon.png"));
    stage.setResizable(false);
    stage.setTitle("Snake Hunt");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

  public static void startGame(Stage stage) {
    GridPane uiLayout = new GridPane();
    Label child = new Label("Score: 11");
    child.setBackground(Background.fill(Color.color(1.0f, 1.0f, 1.0f, 0.5f)));
    child.setTextFill(Color.WHITE);

    child.setFont(Font.font(42));
    uiLayout.add(child, 0, 0);
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    StackPane root = new StackPane(canvas, uiLayout);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    Renderer renderer = new Renderer(canvas);

    Game currentGame = new Game(renderer);

    stage.setScene(scene);

    currentGame.start();

    MovementController movementController = new MovementController(currentGame);
    scene.setOnKeyPressed(movementController);
  }

  public static void showOptionsMenu(Stage stage) {
    // Create the layout for the options menu here
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TilePane root = new TilePane(canvas);

    root.setBackground(Background.fill(Color.valueOf("f4a259")));

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
    root.setBackground(Background.fill(Color.valueOf("f4e285")));

    stage.setScene(scene);
  }

  public static Image loadImage(String path) {
    try (InputStream inputStream = SnakeApplication.class.getResourceAsStream(path)) {
      return new Image(Objects.requireNonNull(inputStream));
    } catch (IOException e) {
      throw new IllegalStateException("Could not load " + path, e);
    }
  }
}