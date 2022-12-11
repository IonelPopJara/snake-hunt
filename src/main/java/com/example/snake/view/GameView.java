package com.example.snake.view;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {

  private final StackPane root;
  private final Canvas canvas;
  private final Label scoreLabel;


  public GameView(double windowWidth, double windowHeight) {
    this.canvas = new Canvas(windowWidth, windowHeight);

    Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 28);

    this.scoreLabel = new Label("Score:0");
    this.scoreLabel.setBackground(Background.fill(Color.color(1.0f, 1.0f, 1.0f, 0.5f)));
    this.scoreLabel.setTextFill(Color.WHITE);
    this.scoreLabel.setFont(font);

    GridPane uiLayout = new GridPane();
    uiLayout.add(scoreLabel, 0, 0);

//    root = new StackPane(canvas, uiLayout, gameOverView.getRoot());
    root = new StackPane(canvas, uiLayout);
  }

  public Parent getRoot() {
    return root;
  }

  public void setScoreLabel(int value) {
    scoreLabel.setText(String.format("Score: %d", value));
  }

  public Canvas getCanvas() {
    return canvas;
  }
}
