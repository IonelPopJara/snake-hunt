package com.example.snake.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static javafx.application.Platform.exit;

public class MainMenu {

  private final Button startButton = new Button("Start Game");
  private final Button leaderboardButton = new Button("Leaderboard");
  private final Button optionsButton = new Button("Options");

  private final TilePane menuRoot;

  public MainMenu() {

    menuRoot = new TilePane();
    menuRoot.setBackground(Background.fill(Color.web("#000000")));
    menuRoot.setAlignment(Pos.CENTER);

    Font buttonFont = Font.font(25);
    Font titleFont = Font.font(30);

    startButton.setFont(buttonFont);
    leaderboardButton.setFont(buttonFont);
    optionsButton.setFont(buttonFont);

    /*
     * Since we are not going to call a event from another class,
     * the exit button can be instantiated as a local variable
     */
    Button exitButton = new Button("Exit Game");
    exitButton.setFont(buttonFont);
    exitButton.setOnAction(event -> exit());

    Label message = new Label("snake game");
    message.setStyle("-fx-border-color: blue; -fx-border-width: 5px; " +
      "-fx-background-color: white; -fx-padding: 7px");
    message.setFont(titleFont);

    HBox topHBox = new HBox(30, startButton, leaderboardButton);
    HBox botHBox = new HBox(30, optionsButton, exitButton);
    topHBox.setSpacing( 20 );
    botHBox.setSpacing( 20 );
    topHBox.setAlignment(Pos.CENTER);
    botHBox.setAlignment(Pos.CENTER);

    menuRoot.getChildren().addAll(message,topHBox,botHBox);
  }

  public TilePane getMenuRoot() {
    return this.menuRoot;
  }

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
