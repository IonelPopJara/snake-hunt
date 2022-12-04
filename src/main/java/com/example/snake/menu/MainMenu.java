package com.example.snake.menu;

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

import static javafx.application.Platform.exit;

public class MainMenu {

  private final Button startButton = new Button();
  private final Button leaderboardButton = new Button();
  private final Button optionsButton = new Button();

  private final VBox menuRoot;

  private static final int menuHeight = 480;
  private static final int buttonLayoutWidth = 640;

  public MainMenu() {
    menuRoot = new VBox();
    menuRoot.setBackground(Background.fill(Color.web("#181818")));
    menuRoot.setAlignment(Pos.CENTER);
    menuRoot.setPrefHeight(menuHeight);

    // Start Button
    ImageView startButtonView = new ImageView(IOUtils.loadImage("/start-button.png"));
    startButton.setGraphic(startButtonView);
    startButton.setPadding(Insets.EMPTY);

    // Options Button
    ImageView optionsButtonView = new ImageView(IOUtils.loadImage("/options-button.png"));
    optionsButton.setGraphic(optionsButtonView);
    optionsButton.setPadding(Insets.EMPTY);

    // Leaderboard Button
    ImageView leaderboardButtonView = new ImageView(IOUtils.loadImage("/leaderboard-button.png"));
    leaderboardButton.setGraphic(leaderboardButtonView);
    leaderboardButton.setPadding(Insets.EMPTY);

    /*
     * Since we are not going to call a event from another class,
     * the exit button can be instantiated as a local variable
     */
    Button exitButton = new Button();
    ImageView exitButtonView = new ImageView(IOUtils.loadImage("/exit-button.png"));
    exitButton.setGraphic(exitButtonView);
    exitButton.setPadding(Insets.EMPTY);

    exitButton.setOnAction(event -> exit());

    // Loading the title image
    ImageView titleScreenView = new ImageView(IOUtils.loadImage("/title.png"));

    StackPane imageContainer = new StackPane(titleScreenView);
    imageContainer.setPadding(new Insets(0, 0, 30, 0));

    HBox topHBox = new HBox(30, startButton, leaderboardButton);
    HBox botHBox = new HBox(30, optionsButton, exitButton);
    topHBox.setSpacing(20);
    botHBox.setSpacing(20);
    topHBox.setAlignment(Pos.CENTER);
    botHBox.setAlignment(Pos.CENTER);

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

  public Parent getMenuRoot() {
    return this.menuRoot;
  }

  public static void onMainMenu(EventHandler<ActionEvent> eventHandler){ mainMenu.setOnAction(eventHandler);}

  public void onStartPressed(EventHandler<ActionEvent> eventHandler) {
    startButton.setOnAction(eventHandler);
  }

  public void onOptionsPressed(EventHandler<ActionEvent> eventHandler) {
    optionsButton.setOnAction(eventHandler);
  }

  public void onLeaderboardPressed(EventHandler<ActionEvent> eventHandler) {
    leaderboardButton.setOnAction(eventHandler);
  }
}
