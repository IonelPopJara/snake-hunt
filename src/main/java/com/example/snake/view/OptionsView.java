package com.example.snake.view;

import com.example.snake.utils.GameColor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class OptionsView {

  private final TilePane root;

  //(d)Main Menu
  public final Button mainMenuButton;

  //(sliderButton)
  public final Slider SliderButton;

  public OptionsView() {
    //(d)to create the Main Menu button
    HBox hbox = new HBox();
    mainMenuButton = new Button("Main Menu");

    //(sliderButton)
    SliderButton = new Slider(0, 1, 0.5);
    SliderButton.setPrefHeight(400.8);
    SliderButton.setShowTickMarks(true);
    SliderButton.setShowTickLabels(true);
    SliderButton.setMajorTickUnit(0.25f);
    SliderButton.setBlockIncrement(0.1f);
    Label SliderCaption = new Label("Volume:");
    SliderCaption.setPrefHeight(350.8);

    // Create the layout for the options menu here
    root = new TilePane();
    root.setBackground(Background.fill(Color.valueOf(GameColor.ORANGE.getHexValue())));

    //(d)to show the Main Menu button and sliderButton
    hbox.getChildren().addAll(mainMenuButton, SliderButton, SliderCaption);
    root.getChildren().add(hbox);
  }

  public Parent getRoot() {
    return root;
  }

  //(d)to go back to main menu
  public void onMainMenuButtonPressed(EventHandler<ActionEvent> eventHandler) {
    mainMenuButton.setOnAction(new EventHandlerSoundDecorator(eventHandler));
  }
}
