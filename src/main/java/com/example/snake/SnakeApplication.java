package com.example.snake;

import com.example.snake.game.Game;
import com.example.snake.graphics.Renderer;
import com.example.snake.menu.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SnakeApplication extends Application {

  // Arbitrary dimensions for now
  private static final int WINDOW_WIDTH = 640;
  private static final int WINDOW_HEIGHT = 480;

  @Override
  public void start(Stage stage) {
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TilePane root = new TilePane();
    MainMenu.initMMenu(root);
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Disabled resizing for now
    stage.setResizable(false);
    stage.setTitle("Snake Base");
    stage.setScene(scene);
    stage.show();

    Renderer renderer = new Renderer(canvas);
    
    Game currentGame = new Game(renderer);

    currentGame.start();
  }
  public static void main(String[] args) {
    launch();
  }
}