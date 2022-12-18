package com.example.snake.view;

import com.example.snake.utils.GameColor;
import com.example.snake.utils.IOUtils;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GameOverView {

  private static final int TRANSITION_DURATION_MS = 2500;

  private final Button mainMenuButton;
  private final Button submitScoreButton;
  private final Button startButton;

  private final Label scoreLabel;

  private final TextField usernameTextField;
  private final Group usernameTextFieldContainer;

  private final VBox centerContainer;

  private final BorderPane root = new BorderPane();

  public GameOverView() {
    // Loading the game over image
    StackPane imageContainer = new StackPane(new ImageView(IOUtils.loadImage("/game-over.png")));
    imageContainer.setPadding(new Insets(30));

    Font scoreFont = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 28);
    Font textFieldFont = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 18);

    scoreLabel = new Label("Your Score: 0");
    scoreLabel.setTextFill(Color.WHITE);
    scoreLabel.setFont(scoreFont);

    startButton = createButton("/play-again-button.png");
    submitScoreButton = createButton("/submit-highscore-button.png");
    mainMenuButton = createButton("/main-menu-button.png");

    GridPane buttonLayout = new GridPane();
    buttonLayout.add(startButton, 0, 0);
    buttonLayout.add(submitScoreButton, 1, 0);
    buttonLayout.add(mainMenuButton, 2, 0);
    buttonLayout.setAlignment(Pos.CENTER);
    buttonLayout.setPadding(new Insets(20));
    buttonLayout.setHgap(30);

    usernameTextField = new TextField();
    usernameTextField.setPrefWidth(350);
    usernameTextField.setFont(textFieldFont);
    usernameTextField.setFocusTraversable(false);
    usernameTextField.setAlignment(Pos.CENTER);
    usernameTextField.setPromptText("Enter your username...");
    usernameTextFieldContainer = new Group(usernameTextField);

    centerContainer = new VBox();
    centerContainer.setAlignment(Pos.CENTER);
    centerContainer.getChildren().addAll(scoreLabel, usernameTextFieldContainer);

    root.setBackground(Background.fill(Color.web(GameColor.RED.getHexValue())));
    root.setTop(imageContainer);
    root.setCenter(centerContainer);
    root.setBottom(buttonLayout);
  }

  private Button createButton(String path) {
    ImageView buttonImageView = new ImageView(IOUtils.loadImage(path));
    Button button = new Button();
    button.setGraphic(buttonImageView);
    button.setPadding(Insets.EMPTY);
    button.setBackground(null);
    return button;
  }

  public void show() {
    this.root.setOpacity(0.0);
    this.root.setVisible(true);
    FadeTransition ft = new FadeTransition(Duration.millis(TRANSITION_DURATION_MS), getRoot());
    ft.setFromValue(0.0);
    ft.setToValue(0.8);
    ft.play();
  }

  public void hide() {
    this.root.setVisible(false);
    this.root.setOpacity(0.0);
  }

  public Parent getRoot() {
    return this.root;
  }

  public void setOnSubmitScoreButtonPressed(EventHandler<ActionEvent> eventHandler) {
    submitScoreButton.setOnAction(event -> {
      submitScoreButton.setDisable(true);
      scoreLabel.setText("Your score has been saved!");
      scoreLabel.setTextFill(Color.BLACK);
      centerContainer.getChildren().remove(usernameTextFieldContainer);
      eventHandler.handle(event);
    });
  }

  public void onStartButtonPressed(EventHandler<ActionEvent> eventHandler) {
    startButton.setOnAction(event -> {
      resetUiState();
      eventHandler.handle(event);
    });
  }

  public void onMainMenuButtonPressed(EventHandler<ActionEvent> eventHandler) {
    mainMenuButton.setOnAction(event -> {
      resetUiState();
      eventHandler.handle(event);
    });
  }

  private void resetUiState() {
    submitScoreButton.setDisable(false);
    scoreLabel.setTextFill(Color.WHITE);
    usernameTextField.setText("");
    centerContainer.getChildren().add(usernameTextFieldContainer);
  }

  public void setScoreLabel(int score) {
    this.scoreLabel.setText("Your Score: " + score);
  }

  public String getSubmittedPlayerName() {
    return usernameTextField.getText();
  }
}
