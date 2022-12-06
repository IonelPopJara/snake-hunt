package com.example.snake.view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class OptionsView {

  private final TilePane root;

  public OptionsView() {
    // Create the layout for the options menu here
    root = new TilePane(new Label("Placeholder"));

    root.setBackground(Background.fill(Color.valueOf("f4a259")));
  }

  public Parent getRoot() {
    return root;
  }
}
