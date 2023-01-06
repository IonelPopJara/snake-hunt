package com.example.snake.view;

import java.util.function.Consumer;

import com.example.snake.game.Difficulty;
import com.example.snake.utils.GameColor;
import com.example.snake.utils.IOUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MainMenuView {

  private final BorderPane root = new BorderPane();
  private final VBox mainContainer = new VBox();

  private final GridPane mainButtonLayout;
  private final GridPane difficultyButtonLayout;

  private final Button startButton;
  private final Button leaderboardButton;
  private final Button optionsButton;
  private final Button exitButton;

  private final Button easyDifficultyButton;
  private final Button mediumDifficultyButton;
  private final Button hardDifficultyButton;
  private final Button backButton;

  private Consumer<Difficulty> onStartGame = e -> {
  };

  public MainMenuView() {
    // Loading the title image
    ImageView titleImageView = new ImageView(IOUtils.loadImage("/UI/title.png"));
    StackPane titleImageContainer = new StackPane(titleImageView);
    titleImageContainer.setPadding(new Insets(0, 0, 30, 0));

    startButton = createButton("/UI/start-button.png");
    optionsButton = createButton("/UI/options-button.png");
    leaderboardButton = createButton("/UI/leaderboard-button.png");
    exitButton = createButton("/UI/exit-button.png");

    easyDifficultyButton = createButton("/UI/easy-button.png");
    mediumDifficultyButton = createButton("/UI/medium-button.png");
    hardDifficultyButton = createButton("/UI/hard-button.png");
    backButton = createButton("/UI/back-button.png");

    mainButtonLayout = createButtonContainer();
    mainButtonLayout.add(startButton, 0, 0);
    mainButtonLayout.add(leaderboardButton, 1, 0);
    mainButtonLayout.add(optionsButton, 0, 1);
    mainButtonLayout.add(exitButton, 1, 1);

    Label difficultyLabel = createLabel("Select difficulty");
    difficultyButtonLayout = createButtonContainer();
    difficultyButtonLayout.add(difficultyLabel, 0, 0, 3, 1);
    difficultyButtonLayout.add(easyDifficultyButton, 0, 1);
    difficultyButtonLayout.add(mediumDifficultyButton, 1, 1);
    difficultyButtonLayout.add(hardDifficultyButton, 2, 1);
    difficultyButtonLayout.add(backButton, 1, 2);
    GridPane.setHalignment(backButton, HPos.CENTER);
    GridPane.setHalignment(difficultyLabel, HPos.CENTER);

    mainContainer.setBackground(Background.fill(Color.web(GameColor.DARK_GREY.getHexValue())));
    mainContainer.setAlignment(Pos.CENTER);
    mainContainer.getChildren().addAll(titleImageContainer, mainButtonLayout);

    root.setCenter(mainContainer);

    setupEventHandlers();
  }

  /**
   * Creates a new label with the given text
   */
  private Label createLabel(String text) {
    Label label = new Label(text);

    label.setTextFill(Color.WHITE);
    label.setAlignment(Pos.CENTER);

    Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 20);
    label.setFont(font);

    return label;
  }

  /**
   * Creates a new button with an image as a background
   *
   * @param path the path to the image
   *
   * @return a new button
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
   * @return a new GridPane container for the buttons
   */
  private GridPane createButtonContainer() {
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setPadding(new Insets(20));
    gridPane.setVgap(20);
    gridPane.setHgap(20);

    return gridPane;
  }

  /**
   * Sets up event handlers
   */
  private void setupEventHandlers() {
    startButton.setOnAction(new EventHandlerSoundDecorator(e -> showDifficultyButtons()));
    exitButton.setOnAction(new EventHandlerSoundDecorator(e -> Platform.exit()));

    easyDifficultyButton.setOnAction(new EventHandlerSoundDecorator(getDifficultyEventHandler(Difficulty.EASY)));
    mediumDifficultyButton.setOnAction(new EventHandlerSoundDecorator(getDifficultyEventHandler(Difficulty.MEDIUM)));
    hardDifficultyButton.setOnAction(new EventHandlerSoundDecorator(getDifficultyEventHandler(Difficulty.HARD)));

    backButton.setOnAction(new EventHandlerSoundDecorator(e -> showMainButtons()));
  }

  /**
   * @return a new event handler that will start the game with the given difficulty when invoked
   */
  private EventHandler<ActionEvent> getDifficultyEventHandler(Difficulty difficulty) {
    return e -> {
      showMainButtons();
      onStartGame.accept(difficulty);
    };
  }

  /**
   * Shows the main menu buttons, and hides the difficulty buttons
   */
  private void showMainButtons() {
    mainContainer.getChildren().remove(difficultyButtonLayout);
    mainContainer.getChildren().add(mainButtonLayout);
  }

  /**
   * Shows the difficulty buttons, and hides the main menu buttons
   */
  private void showDifficultyButtons() {
    mainContainer.getChildren().remove(mainButtonLayout);
    mainContainer.getChildren().add(difficultyButtonLayout);
  }

  /**
   * @return the root element of the view
   */
  public Parent getRoot() {
    return this.root;
  }

  /**
   * Sets the event handler for click events on the start button
   */
  public void onStartButtonPressed(Consumer<Difficulty> eventHandler) {
    this.onStartGame = eventHandler;
  }

  /**
   * Sets the event handler for click events on the options button
   */
  public void onOptionsButtonPressed(EventHandler<ActionEvent> eventHandler) {
    optionsButton.setOnAction(new EventHandlerSoundDecorator(eventHandler));
  }

  /**
   * Sets the event handler for click events on the leaderboard button
   */
  public void onLeaderboardButtonPressed(EventHandler<ActionEvent> eventHandler) {
    leaderboardButton.setOnAction(new EventHandlerSoundDecorator(eventHandler));
  }
}
