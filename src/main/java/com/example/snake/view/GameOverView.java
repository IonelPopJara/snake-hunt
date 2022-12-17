package com.example.snake.view;

import com.example.snake.utils.IOUtils;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GameOverView {

  private final int TRANSITION_DURATION_MS = 2500;
  private final Button mainMenuButton = new Button();
  private final Button  startButton = new Button();
  private final VBox gameOverRoot;
  private static final int menuHeight = 480;
  private static final int buttonLayoutWidth = 640;

  private final Label scoreLabel;

  public GameOverView() {
    // TODO: Vilmer's idea was to put this panel as an overlay when the player dies
    //  I think that it's important to add some delay to the game over screen

    // Loading  the game over image
    ImageView gameOverView = new ImageView(IOUtils.loadImage("/game-over.png"));
    StackPane imageContainer = new StackPane(gameOverView);
    imageContainer.setPadding(new Insets(0, 0, 30, 0));

    // Score Label
    Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 28);
    this.scoreLabel = new Label("Your Score: 0");
    this.scoreLabel.setTextFill(Color.WHITE);
    this.scoreLabel.setFont(font);

    gameOverRoot = new VBox();
    gameOverRoot.setBackground(Background.fill(Color.web("#BC4B51")));
    gameOverRoot.setAlignment(Pos.CENTER);
    gameOverRoot.setPrefHeight(menuHeight);

    // Start Button
    ImageView startButtonView = new ImageView(IOUtils.loadImage("/play-again-button.png"));
    startButton.setGraphic(startButtonView);
    startButton.setPadding(Insets.EMPTY);

    // Main Menu Button
    ImageView mainMenuButtonView = new ImageView(IOUtils.loadImage("/main-menu-button.png"));
    mainMenuButton.setGraphic(mainMenuButtonView);
    mainMenuButton.setPadding(Insets.EMPTY);

    GridPane buttonLayout = new GridPane();
    buttonLayout.add(startButton, 0, 3);
    buttonLayout.add(mainMenuButton, 1, 3);

    buttonLayout.setPrefWidth(buttonLayoutWidth);
    buttonLayout.setAlignment(Pos.CENTER);
    buttonLayout.setPadding(new Insets(20));
    buttonLayout.setVgap(20);
    buttonLayout.setHgap(200);

    gameOverRoot.getChildren().addAll(imageContainer, scoreLabel, buttonLayout);
  }

  public void show() {
    this.gameOverRoot.setOpacity(0.0);
    this.gameOverRoot.setVisible(true);
    FadeTransition ft = new FadeTransition(Duration.millis(TRANSITION_DURATION_MS), getRoot());
    ft.setFromValue(0.0);
    ft.setToValue(0.8);
    ft.play();
  }

  public void hide() {
    this.gameOverRoot.setVisible(false);
    this.gameOverRoot.setOpacity(0.0);
  }

  public Parent getRoot() {
    return this.gameOverRoot;
  }

  public void onStartButtonPressed(EventHandler<ActionEvent> eventHandler1) {
    startButton.setOnAction(eventHandler1);
  }

  public void onMainMenuButtonPressed(EventHandler<ActionEvent> eventHandler) {
    mainMenuButton.setOnAction(eventHandler);
  }

}
