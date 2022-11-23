package com.example.snake;

import com.example.snake.game.Game;
import com.example.snake.graphics.Renderer;
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
    TilePane root = new TilePane(canvas);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Disabled resizing for now
    stage.setResizable(false);
    stage.setTitle("Snake Base");
    stage.setScene(scene);
    stage.show();

    Renderer renderer = new Renderer(canvas);

    /*
    * The program needs to update the position of the snake and every element every second.
    * In order to do that we use a 'Game Loop'. This loop is called constantly and it updates
    * all the elements in the screen.
    *
    * The Game Loop is defined on the Game Class
     */
    Game currentGame = new Game(renderer);

    currentGame.start();
  }

  public static void main(String[] args) {
    launch();
  }
}