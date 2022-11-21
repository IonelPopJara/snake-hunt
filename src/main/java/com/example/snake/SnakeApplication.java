package com.example.snake;

import java.util.List;

import com.example.snake.graphics.Renderer;
import com.example.snake.model.GridPoint;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SnakeApplication extends Application {

  // Arbitrary dimensions for now
  private static final int WINDOW_WIDTH = 640;
  private static final int WINDOW_HEIGHT = 480;

  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 15;

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

    drawExampleSnake(canvas);
  }

  private static void drawExampleSnake(Canvas canvas) {
    // Create some dummy data as an example, and a Renderer to draw it
    List<GridPoint> snake = List.of(new GridPoint(10, 10), new GridPoint(11, 10), new GridPoint(12, 10));
    List<GridPoint> foods = List.of(new GridPoint(5, 5), new GridPoint(22, 7));

    Renderer renderer = new Renderer(canvas);
    renderer.draw(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, snake, foods);
  }

  public static void main(String[] args) {
    launch();
  }
}