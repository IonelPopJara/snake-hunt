package com.example.snake.view;

import com.example.snake.utils.IOUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameOverView {

  private final Button mainMenuButton = new Button("Main Menu");
  private final Button  startButton = new Button();
  private final VBox gameOverRoot;

  private static final int menuHeight = 480;
  private static final int buttonLayoutWidth = 640;

  public GameOverView() {
    gameOverRoot = new VBox();
    gameOverRoot.setBackground(Background.fill(Color.web("#181818")));
    gameOverRoot.setOpacity(0.8);
    gameOverRoot.setAlignment(Pos.CENTER);
    gameOverRoot.setPrefHeight(menuHeight);

    ImageView startButtonView = new ImageView(IOUtils.loadImage("/start-button.png"));
    startButton.setGraphic(startButtonView);
    startButton.setPadding(Insets.EMPTY);

    ImageView gameOverText = new ImageView(IOUtils.loadImage("/gameOver.png"));
    StackPane imageContainer = new StackPane(gameOverText);
    imageContainer.setPadding(new Insets(30, 30, 30, 30));
    gameOverText.setFitWidth(400);
    gameOverText.setFitHeight(250);


    HBox cHBox = new HBox(20, startButton, mainMenuButton);
    cHBox.setSpacing(20);
    cHBox.setAlignment(Pos.CENTER);


    GridPane buttonLayout = new GridPane();
    buttonLayout.add(startButton, 0, 3);
    buttonLayout.add(mainMenuButton, 1, 3);


    buttonLayout.setPrefWidth(buttonLayoutWidth);
    buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
    buttonLayout.setPadding(new Insets(20));
    buttonLayout.setVgap(10);
    buttonLayout.setHgap(350);


    gameOverRoot.getChildren().addAll(imageContainer, buttonLayout);

  }

  public Parent getRoot() {
    return this.gameOverRoot;
  }

  public void onStartButtonPressed(EventHandler<ActionEvent> eventHandler) {
    startButton.setOnAction(eventHandler);
  }

  public void onMainMenuButtonPressed(EventHandler<ActionEvent> eventHandler) {
    mainMenuButton.setOnAction(eventHandler);
  }

}
