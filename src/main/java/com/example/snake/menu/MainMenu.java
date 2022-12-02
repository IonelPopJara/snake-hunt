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

    Font font = Font.font(25);
    Font nfont = Font.font(30);

    startButton.setFont(font);
    leaderboardButton.setFont(font);
    optionsButton.setFont(font);

    /*
     * Since we are not going to call a event from another class,
     * the exit button can be instantiated as a local variable
     */
    Button exitButton = new Button("Exit Game");
    exitButton.setFont(font);
    exitButton.setOnAction(event -> exit());

    //HBox vbox = new HBox(30, startButton, leaderboardButton, optionsButton, exitButton);
    //HBox.setAlignment(Pos.CENTER);
    Label message = new Label("snake game");
    message.setStyle("-fx-border-color: blue; -fx-border-width: 5px; " +
      "-fx-background-color: white; -fx-padding: 7px");
    message.setFont(nfont);

    HBox vbox = new HBox(30, startButton, leaderboardButton);
    HBox dbox = new HBox(30, optionsButton, exitButton);
    vbox.setSpacing( 20 );
    dbox.setSpacing( 20 );
    vbox.setAlignment(Pos.CENTER);
    dbox.setAlignment(Pos.CENTER);

//    HBox vbox = new HBox(30, startButton, leaderboardButton, optionsButton, exitButton);
//    vbox.setAlignment(Pos.CENTER);
//    BorderPane root = new BorderPane();
//    root.setCenter(startButton);
//    root.setCenter(leaderboardButton);
//    root.setCenter(optionsButton);
//    root.setCenter(exitButton);
   // root.setBottom(vbox);


    menuRoot.getChildren().addAll(message,vbox,dbox);
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
