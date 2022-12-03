package com.example.snake.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.application.Platform.exit;

public class MainMenu {

  private final Button startButton = new Button("Start Game");
  private final Button leaderboardButton = new Button("Leaderboard");
  private final Button optionsButton = new Button("Options");

  private final AnchorPane menuRoot;

  public MainMenu() throws FileNotFoundException {

    menuRoot = new AnchorPane();
    menuRoot.setBackground(Background.fill(Color.web("#000000")));

    //Passing FileInputStream object as a parameter
    Image image = new Image(new FileInputStream("src/main/resources/proto-title-screen.png"));
    ImageView imageView = new ImageView(image);

    AnchorPane.setTopAnchor(imageView, 50.0);
    AnchorPane.setLeftAnchor(imageView, 150.0);

    Font buttonsFont = Font.font(25);
    startButton.setFont(buttonsFont);
    leaderboardButton.setFont(buttonsFont);
    optionsButton.setFont(buttonsFont);

    /*
     * Since we are not going to call a event from another class,
     * the exit button can be instantiated as a local variable
     */
    Button exitButton = new Button("Exit Game");
    exitButton.setFont(buttonsFont);
    exitButton.setOnAction(event -> exit());

    HBox topHBox = new HBox(20, startButton, leaderboardButton);
    topHBox.setAlignment(Pos.CENTER);
    AnchorPane.setBottomAnchor(topHBox, 175.0);
    AnchorPane.setLeftAnchor(topHBox, 150.0);

    HBox botHBox = new HBox(20, optionsButton, exitButton);
    botHBox.setAlignment(Pos.CENTER);
    AnchorPane.setBottomAnchor(botHBox, 100.0);
    AnchorPane.setLeftAnchor(botHBox, 185.0);

    menuRoot.getChildren().addAll(imageView, topHBox, botHBox);
  }

  public AnchorPane getMenuRoot() {
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
