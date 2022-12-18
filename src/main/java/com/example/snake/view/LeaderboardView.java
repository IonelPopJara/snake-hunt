package com.example.snake.view;

import java.io.File;
import java.util.List;

import com.example.snake.player.Player;
import com.example.snake.utils.GameColor;
import com.example.snake.utils.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

  private final Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 20);

  public LeaderboardView() {
    root = new VBox();

    ImageView mainMenuButtonView = new ImageView(IOUtils.loadImage("/main-menu-button.png"));
    mainMenuButton.setGraphic(mainMenuButtonView);
    mainMenuButton.setPadding(Insets.EMPTY);

    TableView<Player> playerTable = new TableView<>();

    TableColumn<Player, String> playerNameColumn = createTableColumn("Player Name", "playerName");
    TableColumn<Player, Integer> playerScoreColumn = createTableColumn("Player Score", "score");

    playerTable.setItems(readScores());
    playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    playerTable.getColumns().add(playerNameColumn);
    playerTable.getColumns().add(playerScoreColumn);
    playerTable.setBackground(Background.fill(Color.valueOf(GameColor.DARK_GREY.getHexValue())));

    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    hbox.getChildren().addAll(mainMenuButton);

    root.setBackground(Background.fill(Color.valueOf(GameColor.DARK_GREY.getHexValue())));
    root.getChildren().addAll(playerTable, hbox);

    playerNameColumn.setCellFactory(this::createTableCell);
    playerScoreColumn.setCellFactory(this::createTableCell);
  }

  // TODO: Make sure this reads each time the view is shown, not just in the constructor
  private ObservableList<Player> readScores() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      List<Player> players = objectMapper.readValue(new File("scores.json"), new TypeReference<>() {});
      return FXCollections.observableList(players);
    } catch (Exception e) {
      e.printStackTrace();
      return FXCollections.emptyObservableList();
    }
  }

  private <T> TableColumn<Player, T> createTableColumn(String headerTitle, String property) {
    Label headerLabel = new Label(headerTitle);
    headerLabel.setFont(font);
    headerLabel.setTextFill(Color.BLACK);

    TableColumn<Player, T> column = new TableColumn<>();
    column.setGraphic(headerLabel);
    column.setCellValueFactory(new PropertyValueFactory<>(property));

    return column;
  }

  private <T> TableCell<Player, T> createTableCell(TableColumn<Player, T> playerStringTableColumn) {
    TableCell<Player, T> tableCell = new TableCell<>() {
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
    mainMenuButton.setOnAction(eventHandler);
  }
}
