package com.example.snake.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.VarHandle;
import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static javafx.application.Platform.exit;

public class MainMenu {

  private final Button startButton = new Button("Start Game");
  private final Button leaderboardButton = new Button("Leaderboard");
  private final Button optionsButton = new Button("Options");

  private final VBox menuRoot;

  public MainMenu() {
    menuRoot = new VBox();
    menuRoot.setBackground(Background.fill(Color.web("#000000")));
    menuRoot.setAlignment(Pos.CENTER);
    menuRoot.setPrefHeight(480);

    Font buttonFont = Font.font(25);

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

    Image image = loadImage("/title.png");

    ImageView imageView = new ImageView(image);
    StackPane imageContainer = new StackPane(imageView);
    imageContainer.setPadding(new Insets(0, 0, 30, 0));

    menuRoot.setOnMouseMoved(event -> {
      imageContainer.setPadding(new Insets(0, 0, event.getY() * 0.2, 0));
    });


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

    buttonLayout.setPrefWidth(640);
    buttonLayout.setAlignment(Pos.CENTER);
    buttonLayout.setPadding(new Insets(20));
    buttonLayout.setVgap(20);
    buttonLayout.setHgap(20);


    menuRoot.getChildren().addAll(imageContainer, buttonLayout);

    //menuRoot.getChildren().addAll(imageView, topHBox, botHBox);
  }


  private static Image loadImage(String path) {
    try (InputStream inputStream = MainMenu.class.getResourceAsStream(path)) {
      return new Image(Objects.requireNonNull(inputStream));
    } catch (IOException e) {
      throw new IllegalStateException("Could not load " + path, e);
    }
  }

  public Parent getMenuRoot() {
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
