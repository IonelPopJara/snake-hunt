package com.example.snake.view;

import com.example.snake.sound.SoundManager;
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
    StackPane imageContainer = new StackPane(new ImageView(IOUtils.loadImage("/UI/game-over.png")));
    imageContainer.setPadding(new Insets(30));

    Font scoreFont = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 28);
    Font textFieldFont = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 18);

    scoreLabel = new Label("Your Score: 0");
    scoreLabel.setTextFill(Color.WHITE);
    scoreLabel.setFont(scoreFont);

    startButton = createButton("/UI/play-again-button.png");
    submitScoreButton = createButton("/UI/submit-highscore-button.png");
    mainMenuButton = createButton("/UI/main-menu-button.png");

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
    root.setVisible(false);
    root.setOpacity(0.0);

    root.setTop(imageContainer);
    root.setCenter(centerContainer);
    root.setBottom(buttonLayout);
  }

  /**
   * Creates a Button with an image instead of text
   *
   * @param path the path to the image
   */
  private Button createButton(String path) {
    ImageView buttonImageView = new ImageView(IOUtils.loadImage(path));
    Button button = new Button();
    button.setGraphic(buttonImageView);
    button.setPadding(Insets.EMPTY);
    button.setBackground(null);
    return button;
  }

  /**
   * Makes the view visible, using a transition
   */
  public void show() {
    root.setOpacity(0.0);
    root.setVisible(true);

    FadeTransition ft = new FadeTransition(Duration.millis(TRANSITION_DURATION_MS), getRoot());
    ft.setFromValue(0.0);
    ft.setToValue(0.8);
    ft.play();
  }

  /**
   * Hides the view completely
   */
  public void hide() {
    root.setVisible(false);
    root.setOpacity(0.0);
  }

  /**
   * @return the root element of the view
   */
  public Parent getRoot() {
    return root;
  }

  /**
   * Sets the event handler for click events on the submit score button
   */
  public void setOnSubmitScoreButtonPressed(EventHandler<ActionEvent> eventHandler) {
    submitScoreButton.setOnAction(new EventHandlerSoundDecorator(event -> {
      submitScoreButton.setDisable(true);
      scoreLabel.setText("Your score has been saved!");
      scoreLabel.setTextFill(Color.BLACK);
      centerContainer.getChildren().remove(usernameTextFieldContainer);
      eventHandler.handle(event);
    }));
  }

  /**
   * Sets the event handler for click events on the start button
   */
  public void onStartButtonPressed(EventHandler<ActionEvent> eventHandler) {
    startButton.setOnAction(new EventHandlerSoundDecorator(event -> {
      resetUiState();
      eventHandler.handle(event);
    }));
  }

  /**
   * Sets the event handler for click events on the main menu button
   */
  public void onMainMenuButtonPressed(EventHandler<ActionEvent> eventHandler) {
    mainMenuButton.setOnAction(new EventHandlerSoundDecorator(event -> {
      SoundManager.getInstance().playMenuMusic();
      resetUiState();
      eventHandler.handle(event);
    }));
  }

  /**
   * Resets UI state changes to default
   */
  private void resetUiState() {
    hide();

    submitScoreButton.setDisable(false);
    scoreLabel.setTextFill(Color.WHITE);
    usernameTextField.setText("");
    centerContainer.getChildren().setAll(scoreLabel, usernameTextFieldContainer);
  }

  /**
   * Sets the score label
   *
   * @param score the score value
   */
  public void setScoreLabel(int score) {
    scoreLabel.setText("Your Score: " + score);
  }

  /**
   * @return the name that the player submitted for highscore, may be empty string but not null
   */
  public String getSubmittedPlayerName() {
    return usernameTextField.getText();
  }
}
