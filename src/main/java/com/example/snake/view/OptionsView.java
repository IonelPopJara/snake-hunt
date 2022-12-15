package com.example.snake.view;

import com.example.snake.utils.GameColors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class OptionsView {

  private final TilePane root;


  //(d)Main Menu
  public  final Button mainMenuButton;

  public OptionsView() {
    //(d)to create the Main Menu button
    HBox hbox = new HBox();
    mainMenuButton = new Button("Main Menu");


    // Create the layout for the options menu here
    root = new TilePane();
    root.setBackground(Background.fill(Color.valueOf(GameColors.ORANGE.getColorValue())));


    //(d)to show the Main Menu button
    hbox.getChildren().addAll(mainMenuButton);
    root.getChildren().add(hbox);

  }

  public Parent getRoot() {
    return root;
  }


  //(d)to go back to main menu
  public  void onMainMenuButtonPressed(EventHandler<ActionEvent> eventHandler) {
    mainMenuButton.setOnAction(eventHandler);
  }




}
