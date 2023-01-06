package com.example.snake.view;

import java.util.Locale;

import com.example.snake.utils.GameColor;
import com.example.snake.utils.IOUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {

  private static final double ASPECT_RATIO = 4.0 / 3.0;

  private final GameOverView gameOverView;

  private final StackPane root;
  private final Canvas canvas;

  private final Label scoreLabel;
  private final Label preyLifetimeLabel;
  private final Node preyLifetimeContainer;

  public GameView() {
    this.gameOverView = new GameOverView();
    this.canvas = new Canvas();

    Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 28);
    Color labelBackgroundColor = Color.color(1.0f, 1.0f, 1.0f, 0.25f);

    scoreLabel = new Label("Score:0");
    scoreLabel.setBackground(Background.fill(labelBackgroundColor));
    scoreLabel.setTextFill(Color.WHITE);
    scoreLabel.setFont(font);
    scoreLabel.setPadding(new Insets(5));

    preyLifetimeLabel = new Label("0.0s");
    preyLifetimeLabel.setTextFill(Color.WHITE);
    preyLifetimeLabel.setFont(font);
    preyLifetimeLabel.setWrapText(false);
    preyLifetimeLabel.setMinWidth(100);

    ImageView preyGraphic = new ImageView(IOUtils.loadImage("/Sprites/Food/prey-1.png"));
    preyGraphic.setFitWidth(24);
    preyGraphic.setFitHeight(24);

    HBox preyLifetimeHBox = new HBox(preyGraphic, preyLifetimeLabel);
    preyLifetimeHBox.setAlignment(Pos.CENTER);
    preyLifetimeHBox.setSpacing(10);
    preyLifetimeHBox.setPadding(new Insets(5));
    preyLifetimeHBox.setBackground(Background.fill(labelBackgroundColor));
    preyLifetimeHBox.setPrefSize(0, 0);

    preyLifetimeContainer = new Group(preyLifetimeHBox);

    StackPane uiLayout = new StackPane(scoreLabel, preyLifetimeContainer);
    StackPane.setAlignment(scoreLabel, Pos.TOP_LEFT);
    StackPane.setAlignment(preyLifetimeContainer, Pos.TOP_RIGHT);

    root = new StackPane(canvas, uiLayout, gameOverView.getRoot());
    root.setBackground(Background.fill(Color.valueOf(GameColor.DARK_GREEN_2.getHexValue())));

    root.widthProperty().addListener((observable, oldValue, newValue) -> updateCanvasSize(newValue.doubleValue(), root.getHeight()));
    root.heightProperty().addListener((observable, oldValue, newValue) -> updateCanvasSize(root.getWidth(), newValue.doubleValue()));
  }

  /**
   * Updates the canvas size while maintaining the aspect ratio of 4:3
   *
   * @param width  the upper bound for the width of the canvas
   * @param height the upper bound for the height of the canvas
   */
  private void updateCanvasSize(double width, double height) {
    double actualAspectRatio = width / height;

    if (ASPECT_RATIO > actualAspectRatio) {
      canvas.setWidth(width);
      canvas.setHeight(width * (1 / ASPECT_RATIO));
    } else {
      canvas.setWidth(height * ASPECT_RATIO);
      canvas.setHeight(height);
    }
  }

  /**
   * @return the root element of the view
   */
  public Parent getRoot() {
    return root;
  }

  /**
   * Sets the score label
   *
   * @param value the score value
   */
  public void setScoreLabel(int value) {
    scoreLabel.setText(String.format(Locale.US, "Score:%d", value));
  }

  /**
   * Sets the pray lifetime left. If less or equal to 0, the label will be hidden
   *
   * @param lifetime the lifetime left value
   */
  public void setPreyLifetime(float lifetime) {
    preyLifetimeLabel.setText(String.format(Locale.US, "%.1f", lifetime));
    preyLifetimeContainer.setVisible(lifetime > 0.0f);
  }

  /**
   * @return the Canvas element
   */
  public Canvas getCanvas() {
    return canvas;
  }

  /**
   * @return the GameOverView
   */
  public GameOverView getGameOverView() {
    return gameOverView;
  }
}
