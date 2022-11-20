package com.example.snake;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SnakeApplication extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    StackPane root = new StackPane(new Label("Hello World"));

    // Arbitrary dimensions for now
    Scene scene = new Scene(root, 640, 480);

    stage.setTitle("Hello World!");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}