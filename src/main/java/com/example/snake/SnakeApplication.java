package com.example.snake;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SnakeApplication extends Application {

  int pressedCounter = 0;

  @Override
  public void start(Stage stage) throws IOException {

    // Set window title
    stage.setTitle("Snake Base");

    // Create an Input Stream
    FileInputStream input = new FileInputStream("src/main/resources/button-image.jpg");

    // Create an Image
    Image buttonImage = new Image(input);

    // Create an Image View
    ImageView imageView = new ImageView(buttonImage);

    // Create a button
    Button newButton = new Button("", imageView);

    // Create a stack pane
    TilePane root = new TilePane();

    // Create a Label
    Label label = new Label("This button was pressed 0 times");

    // Action event
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        pressedCounter += 1;
        label.setText("This button was pressed  " + pressedCounter + " times");
      }
    };

    // When button is pressed
    newButton.setOnAction(event);

    // Add button
    root.getChildren().add(newButton);
    root.getChildren().add(label);

    // Create a scene
    // Arbitrary dimensions for now
    Scene scene = new Scene(root, 640, 480);

    // Set the scene and show
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}