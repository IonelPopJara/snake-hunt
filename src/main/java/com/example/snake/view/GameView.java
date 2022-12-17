package com.example.snake.view;

import java.util.Locale;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class GameView {

  private final GameOverView gameOverView;

  private final StackPane root;
  private final Canvas canvas;

  private final Label scoreLabel;
  private final Label preyLifetimeLabel;
  private final Node preyLifetimeContainer;

  public GameView(double windowWidth, double windowHeight) {
    this.gameOverView = new GameOverView();
    this.canvas = new Canvas(windowWidth, windowHeight);

    Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 28);
    Color labelBackgroundColor = Color.color(1.0f, 1.0f, 1.0f, 0.25f);

    scoreLabel = new Label("Score:0");
    scoreLabel.setBackground(Background.fill(labelBackgroundColor));
    scoreLabel.setTextFill(Color.WHITE);
    scoreLabel.setFont(font);
    scoreLabel.setPadding(new Insets(5));

    preyLifetimeLabel = new Label("0.0s");
    preyLifetimeLabel.setTextFill(Color.WHITE);
    preyLifetimeLabel.setFont(font);
    preyLifetimeLabel.setWrapText(false);
    preyLifetimeLabel.setMinWidth(100);

    Rectangle preyGraphic = new Rectangle(24, 24, Color.ANTIQUEWHITE);
    preyGraphic.setArcWidth(12);
    preyGraphic.setArcHeight(12);

    HBox preyLifetimeHBox = new HBox(preyGraphic, preyLifetimeLabel);
    preyLifetimeHBox.setAlignment(Pos.CENTER);
    preyLifetimeHBox.setSpacing(10);
    preyLifetimeHBox.setPadding(new Insets(5));
    preyLifetimeHBox.setBackground(Background.fill(labelBackgroundColor));
    preyLifetimeHBox.setPrefSize(0, 0);

    preyLifetimeContainer = new Group(preyLifetimeHBox);

    StackPane uiLayout = new StackPane(scoreLabel, preyLifetimeContainer);
    StackPane.setAlignment(scoreLabel, Pos.TOP_LEFT);
    StackPane.setAlignment(preyLifetimeContainer, Pos.TOP_RIGHT);

    root = new StackPane(canvas, uiLayout, gameOverView.getRoot());
  }

  public Parent getRoot() {
    return root;
  }

  public void setScoreLabel(int value) {
    scoreLabel.setText(String.format(Locale.US, "Score:%d", value));
  }

  public void setPreyLifetime(float lifetime) {
    preyLifetimeLabel.setText(String.format(Locale.US, "%.1f", lifetime));
    preyLifetimeContainer.setVisible(lifetime > 0.0f);
  }

  public Canvas getCanvas() {
    return canvas;
  }

  public GameOverView getGameOverView() {
    return gameOverView;
  }
}
