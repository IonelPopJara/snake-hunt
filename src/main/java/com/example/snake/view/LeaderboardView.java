package com.example.snake.view;

import com.example.snake.game.Difficulty;
import com.example.snake.player.PlayerScore;
import com.example.snake.utils.GameColor;
import com.example.snake.utils.IOUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LeaderboardView {

  private final VBox root;

  public final Button mainMenuButton = new Button();

  private final TableView<PlayerScore> playerTable = new TableView<>();

  private final Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 20);

  public LeaderboardView() {
    root = new VBox();

    ImageView mainMenuButtonView = new ImageView(IOUtils.loadImage("/UI/main-menu-button.png"));
    mainMenuButton.setGraphic(mainMenuButtonView);
    mainMenuButton.setPadding(Insets.EMPTY);

    TableColumn<PlayerScore, String> playerNameColumn = createTableColumn("Player Name", "playerName");
    TableColumn<PlayerScore, Integer> playerScoreColumn = createTableColumn("Player Score", "score");
    TableColumn<PlayerScore, Difficulty> playerDifficulty = createTableColumn("Difficulty", "gameDifficulty");

    playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    playerTable.getColumns().add(playerNameColumn);
    playerTable.getColumns().add(playerScoreColumn);
    playerTable.getColumns().add(playerDifficulty);
    playerTable.setBackground(Background.fill(Color.valueOf(GameColor.DARK_GREY.getHexValue())));

    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    hbox.getChildren().addAll(mainMenuButton);

    root.setBackground(Background.fill(Color.valueOf(GameColor.DARK_GREY.getHexValue())));
    root.getChildren().addAll(playerTable, hbox);

    playerNameColumn.setCellFactory(this::createTableCell);
    playerScoreColumn.setCellFactory(this::createTableCell);
    playerDifficulty.setCellFactory(this::createTableCell);
  }

  private <T> TableColumn<PlayerScore, T> createTableColumn(String headerTitle, String property) {
    Label headerLabel = new Label(headerTitle);
    headerLabel.setFont(font);
    headerLabel.setTextFill(Color.BLACK);

    TableColumn<PlayerScore, T> column = new TableColumn<>();
    column.setGraphic(headerLabel);
    column.setCellValueFactory(new PropertyValueFactory<>(property));

    return column;
  }

  private <T> TableCell<PlayerScore, T> createTableCell(TableColumn<PlayerScore, T> playerStringTableColumn) {
    TableCell<PlayerScore, T> tableCell = new TableCell<>() {
      @Override
      protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
          setText(String.valueOf(item));
        }
      }

      @Override
      public void updateIndex(int newIndex) {
        super.updateIndex(newIndex);
        if (newIndex % 2 == 0) {
          setStyle("-fx-background-color: " + GameColor.DARK_GREY.getHexValue());
        } else {
          setStyle("-fx-background-color: " + GameColor.LIGHT_GREY.getHexValue());
        }
      }
    };

    tableCell.setFont(font);
    tableCell.setTextFill(Color.WHITE);
    tableCell.setAlignment(Pos.CENTER);

    return tableCell;
  }

  public Parent getRoot() {
    return root;
  }

  public void onMainMenuButtonPressed(EventHandler<ActionEvent> eventHandler) {
    mainMenuButton.setOnAction(new EventHandlerSoundDecorator(eventHandler));
  }

  public void reloadScores() {
    playerTable.setItems(FXCollections.observableList(IOUtils.loadScores()));
  }
}
