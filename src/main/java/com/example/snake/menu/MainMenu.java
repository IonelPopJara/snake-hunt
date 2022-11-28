package com.example.snake.menu;

import com.example.snake.SnakeApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class MainMenu extends SnakeApplication {
  public static void initMMenu(TilePane MenuRoot, Stage stage) {
    MenuRoot.setBackground(Background.fill(Color.web("#000000")));
    MenuRoot.setAlignment(Pos.CENTER);
    Rectangle bg = new Rectangle(64, 480);
    bg.setFill(Color.BLACK);
    Font fontSize = Font.font(25);

    Button startBtn = new Button("Start Game");
    startBtn.setFont((fontSize));
    startBtn.setOnAction(event -> startGame(stage));

    Button highScore = new Button("Highscore");
    highScore.setFont((fontSize));
    highScore.setOnAction(event -> highscore());

    Button Options = new Button("Options");
    Options.setFont(fontSize);
    Options.setOnAction(event -> options());

    Button exitBtn = new Button("Exit Game");
    exitBtn.setFont(fontSize);
    exitBtn.setOnAction(event -> exit());

    VBox vbox = new VBox(30, startBtn, highScore, Options, exitBtn);
    // vbox.setTranslateX(150);
    // vbox.setTranslateY(175);
    vbox.setAlignment(Pos.CENTER);

    MenuRoot.getChildren().addAll(vbox);
  }

  public static void startGame(Stage stage) {
    VBox startRoot = new VBox();
    Scene startScene = new Scene(startRoot, 640, 480);
    stage.setScene(startScene);
  }

  public static void options() {
  }

  public static void highscore() {
  }

}
