package com.example.snake.view;

import com.example.snake.utils.GameColor;
import com.example.snake.utils.IOUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainMenuView {

  private final Button startButton = new Button();
  private final Button leaderboardButton = new Button();
  private final Button optionsButton = new Button();

  private final VBox menuRoot;

  private static final int menuHeight = 480;
  private static final int buttonLayoutWidth = 640;

  public MainMenuView() {
    menuRoot = new VBox();
    menuRoot.setBackground(Background.fill(Color.web(GameColor.DARK_GREY.getHexValue())));
    menuRoot.setAlignment(Pos.CENTER);
    menuRoot.setPrefHeight(menuHeight);

    ImageView startButtonView = new ImageView(IOUtils.loadImage("/start-button.png"));
    startButton.setGraphic(startButtonView);
    startButton.setPadding(Insets.EMPTY);

    ImageView optionsButtonView = new ImageView(IOUtils.loadImage("/options-button.png"));
    optionsButton.setGraphic(optionsButtonView);
    optionsButton.setPadding(Insets.EMPTY);

    ImageView leaderboardButtonView = new ImageView(IOUtils.loadImage("/leaderboard-button.png"));
    leaderboardButton.setGraphic(leaderboardButtonView);
    leaderboardButton.setPadding(Insets.EMPTY);

    ImageView exitButtonView = new ImageView(IOUtils.loadImage("/exit-button.png"));
    Button exitButton = new Button();
    exitButton.setGraphic(exitButtonView);
    exitButton.setPadding(Insets.EMPTY);
    exitButton.setOnAction(event -> Platform.exit());

    // Loading the title image
    ImageView titleScreenView = new ImageView(IOUtils.loadImage("/title.png"));
    StackPane imageContainer = new StackPane(titleScreenView);
    imageContainer.setPadding(new Insets(0, 0, 30, 0));

    GridPane buttonLayout = new GridPane();
    buttonLayout.add(startButton, 0, 0);
    buttonLayout.add(leaderboardButton, 1, 0);
    buttonLayout.add(optionsButton, 0, 1);
    buttonLayout.add(exitButton, 1, 1);

    buttonLayout.setPrefWidth(buttonLayoutWidth);
    buttonLayout.setAlignment(Pos.CENTER);
    buttonLayout.setPadding(new Insets(20));
    buttonLayout.setVgap(20);
    buttonLayout.setHgap(20);

    menuRoot.getChildren().addAll(imageContainer, buttonLayout);
  }

  public Parent getRoot() {
    return this.menuRoot;
  }

  public void onStartButtonPressed(EventHandler<ActionEvent> eventHandler) {
    startButton.setOnAction(eventHandler);
  }

  public void onOptionsButtonPressed(EventHandler<ActionEvent> eventHandler) {
    optionsButton.setOnAction(eventHandler);
  }

  public void onLeaderboardButtonPressed(EventHandler<ActionEvent> eventHandler) {
    leaderboardButton.setOnAction(eventHandler);
  }
}
