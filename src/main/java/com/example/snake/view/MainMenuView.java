package com.example.snake.view;

import com.example.snake.game.Difficulty;
import com.example.snake.sound.SoundManager;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.function.Consumer;

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
    ImageView titleImageView = new ImageView(IOUtils.loadImage("/title.png"));
    StackPane titleImageContainer = new StackPane(titleImageView);
    titleImageContainer.setPadding(new Insets(0, 0, 30, 0));

    startButton = createButton("/start-button.png");
    optionsButton = createButton("/options-button.png");
    leaderboardButton = createButton("/leaderboard-button.png");
    exitButton = createButton("/exit-button.png");

    easyDifficultyButton = createButton("/easy-button.png");
    mediumDifficultyButton = createButton("/medium-button.png");
    hardDifficultyButton = createButton("/hard-button.png");
    backButton = createButton("/back-button.png");

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

  private Label createLabel(String text) {
    Label label = new Label(text);

    label.setTextFill(Color.WHITE);
    label.setAlignment(Pos.CENTER);

    Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 20);
    label.setFont(font);

    return label;
  }

  private Button createButton(String path) {
    ImageView buttonImageView = new ImageView(IOUtils.loadImage(path));
    Button button = new Button();
    button.setGraphic(buttonImageView);
    button.setPadding(Insets.EMPTY);
    button.setBackground(null);
    return button;
  }

  private GridPane createButtonContainer() {
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setPadding(new Insets(20));
    gridPane.setVgap(20);
    gridPane.setHgap(20);

    return gridPane;
  }

  private void setupEventHandlers() {
    startButton.setOnAction(e -> showDifficultyButtons());
    exitButton.setOnAction(e -> Platform.exit());

    easyDifficultyButton.setOnAction(getDifficultyEventHandler(Difficulty.EASY));
    mediumDifficultyButton.setOnAction(getDifficultyEventHandler(Difficulty.MEDIUM));
    hardDifficultyButton.setOnAction(getDifficultyEventHandler(Difficulty.HARD));

    backButton.setOnAction(e -> showMainButtons());
  }

  private EventHandler<ActionEvent> getDifficultyEventHandler(Difficulty difficulty) {
    return e -> {
      // TODO: Fix the button sounds
//      SoundManager.getInstance().playButtonSound();
      showMainButtons();
      onStartGame.accept(difficulty);
    };
  }

  private void showMainButtons() {
    mainContainer.getChildren().remove(difficultyButtonLayout);
    mainContainer.getChildren().add(mainButtonLayout);
  }

  private void showDifficultyButtons() {
    mainContainer.getChildren().remove(mainButtonLayout);
    mainContainer.getChildren().add(difficultyButtonLayout);
  }

  public Parent getRoot() {
    return this.root;
  }

  public void onStartButtonPressed(Consumer<Difficulty> eventHandler) {
    this.onStartGame = eventHandler;
  }

  public void onOptionsButtonPressed(EventHandler<ActionEvent> eventHandler) {
    optionsButton.setOnAction(event -> {
      // TODO: Fix Button Sounds
//      SoundManager.getInstance().playButtonSound();
      eventHandler.handle(event);
    });
  }

  public void onLeaderboardButtonPressed(EventHandler<ActionEvent> eventHandler) {
    leaderboardButton.setOnAction(eventHandler);
  }
}
