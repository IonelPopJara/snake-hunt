package com.example.snake.view;

import com.example.snake.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LeaderboardView {

  private static final double COLUMN_WIDTH = 100;
  private static final double COLUMN_HEIGHT = 100;

  private final VBox root;

  public final Button mainMenuButton;

  public LeaderboardView() {
    root = new VBox();
    mainMenuButton = new Button("Main Menu");

    TableView<Player> playerTable;

    TableColumn<Player, String> playerNameColumn = new TableColumn<>("Player Name");
    playerNameColumn.setMinWidth(COLUMN_WIDTH);
    playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

    TableColumn<Player, Integer> playerScoreColumn = new TableColumn<>("Player Score");
    playerScoreColumn.setMinWidth(COLUMN_HEIGHT);
    playerScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

    playerTable = new TableView<>();
    playerTable.setItems(getPlayers());
    // I will fix this later
    //playerTable.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    playerTable.getColumns().add(playerNameColumn);
    playerTable.getColumns().add(playerScoreColumn);

    HBox hbox = new HBox();
    hbox.setAlignment(Pos.BOTTOM_LEFT);
    hbox.getChildren().addAll(mainMenuButton);

    root.setBackground(Background.fill(Color.valueOf("DCDCDC")));
    root.getChildren().addAll(playerTable, mainMenuButton);
  }

  public Parent getRoot() {
    return root;
  }

  public void onMainMenuButtonPressed(EventHandler<ActionEvent> eventHandler) {
    mainMenuButton.setOnAction(eventHandler);
  }

  public static ObservableList<Player> getPlayers() {
    ObservableList<Player> players = FXCollections.observableArrayList();
    players.add(new Player("Player 1", 91));
    players.add(new Player("Player 2", 76));
    players.add(new Player("Player 3", 58));
    players.add(new Player("Player 4", 36));
    players.add(new Player("Player 5", 23));
    return players;
  }
}
