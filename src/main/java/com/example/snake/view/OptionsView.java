package com.example.snake.view;

import com.example.snake.sound.SoundManager;
import com.example.snake.utils.GameColor;
import com.example.snake.utils.IOUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class OptionsView {

  private final BorderPane root = new BorderPane();
  private final VBox mainContainer = new VBox();

  private final GridPane testLayout;

  //(d)Main Menu
  public final Button mainMenuButton;

  public final Slider musicSlider;
  public final Slider soundEffectsSlider;


  public OptionsView() {

    StackPane howToPLayContainer = new StackPane(new ImageView(IOUtils.loadImage("/UI/how-to-play.png")));
    howToPLayContainer.setPadding(new Insets(0, 0, 0, 0));

    GridPane musicSliderContainer = createGridContainer();
    Label musicLabel = createLabel("Music Volume");
    musicSlider = createSlider();
    musicSliderContainer.add(musicLabel, 0, 0);
    musicSliderContainer.add(musicSlider, 0, 1);
    adjustMusic();

    GridPane soundsSliderContainer = createGridContainer();
    Label soundEffectsLabel = createLabel("SFX Volume");
    soundEffectsSlider = createSlider();
    soundsSliderContainer.add(soundEffectsLabel, 0, 0);
    soundsSliderContainer.add(soundEffectsSlider, 0, 1);
    adjustSoundFX();

    mainMenuButton = createButton("/UI/main-menu-button.png");

    testLayout = createGridContainer();

    testLayout.add(musicSliderContainer, 0, 0);
    testLayout.add(soundsSliderContainer, 0, 1);
    testLayout.add(mainMenuButton, 0, 2);
    testLayout.add(howToPLayContainer, 1, 0, 1, 4);

    mainContainer.setBackground(Background.fill(Color.web(GameColor.DARK_GREY.getHexValue())));
    mainContainer.setAlignment(Pos.CENTER);
    mainContainer.getChildren().addAll(testLayout);

    root.setCenter(mainContainer);
  }

  public Parent getRoot() {
    return this.root;
  }

  //(d)to go back to main menu
  public void onMainMenuButtonPressed(EventHandler<ActionEvent> eventHandler) {
    mainMenuButton.setOnAction(new EventHandlerSoundDecorator(eventHandler));
  }

  private Button createButton(String path) {
    ImageView buttonImageView = new ImageView(IOUtils.loadImage(path));
    Button button = new Button();
    button.setGraphic(buttonImageView);
    button.setPadding(Insets.EMPTY);
    button.setBackground(null);
    return button;
  }

  private Label createLabel(String text) {
    Label label = new Label(text);

    label.setTextFill(Color.WHITE);
    label.setAlignment(Pos.CENTER);

    Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 20);
    label.setFont(font);

    return label;
  }

  private Slider createSlider() {
    Slider slider = new Slider(0, 1, 0.5);
    slider.setPrefHeight(400.8);
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(0.25f);
    slider.setBlockIncrement(0.1f);
    slider.setStyle("-fx-control-inner-background: " + GameColor.ORANGE.getHexValue() + ";");

    return slider;
  }

  public void adjustMusic() {
    musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      SoundManager.getInstance().getMenuMusicPlayer().setVolume((Double) newValue);
      SoundManager.getInstance().getInGameMusicPlayer().setVolume((Double) newValue);
    });
  }

  public void adjustSoundFX() {
    soundEffectsSlider.valueProperty().addListener(new ChangeListener<>() {
      final ArrayList soundFX = SoundManager.getInstance().getSoundFX();

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        for (Object fx : soundFX) {
          AudioClip clip = (AudioClip) fx;
          clip.setVolume((Double) newValue);
        }
      }
    });
  }

  private GridPane createGridContainer() {
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setPadding(new Insets(20));
//    gridPane.setVgap(20);
    gridPane.setHgap(20);

    return gridPane;
  }
}
