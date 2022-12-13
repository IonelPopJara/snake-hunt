package com.example.snake.view;

import com.example.snake.player.Player;
import com.example.snake.utils.GameColors;
import com.example.snake.utils.IOUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

  private static final double COLUMN_WIDTH = 100;
  private static final double COLUMN_HEIGHT = 100;

  private final VBox root;

  public final Button mainMenuButton = new Button();

  Font font = Font.loadFont(GameView.class.getResourceAsStream("/Fonts/joystix.otf"), 20);

  public LeaderboardView() {
    root = new VBox();

    // Main Menu Button
    ImageView mainMenuButtonView = new ImageView(IOUtils.loadImage("/main-menu-button.png"));
    mainMenuButton.setGraphic(mainMenuButtonView);
    mainMenuButton.setPadding(Insets.EMPTY);

    // Table View
    TableView<Player> playerTable = new TableView<>();

    TableColumn<Player, String> playerNameColumn = new TableColumn<>();
    Label playerNameHeader = new Label("Player Name");
    playerNameHeader.setFont(font);
    playerNameHeader.setTextFill(Color.BLACK);
    playerNameColumn.setGraphic(playerNameHeader);
    playerNameColumn.setMinWidth(COLUMN_WIDTH);
    playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

    TableColumn<Player, Integer> playerScoreColumn = new TableColumn<>();
    Label playerScoreHeader = new Label("Player Score");
    playerScoreHeader.setFont(font);
    playerScoreHeader.setTextFill(Color.BLACK);
    playerScoreColumn.setGraphic(playerScoreHeader);
    playerScoreColumn.setMinWidth(COLUMN_HEIGHT);
    playerScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

    playerTable.setItems(getPlayers());
    // I will fix this later
    playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    playerTable.getColumns().add(playerNameColumn);
    playerTable.getColumns().add(playerScoreColumn);
    playerTable.setBackground(Background.fill(Color.valueOf(GameColors.DARK_GREY.getColorValue())));

    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    hbox.getChildren().addAll(mainMenuButton);

    root.setBackground(Background.fill(Color.valueOf(GameColors.DARK_GREY.getColorValue())));
    root.getChildren().addAll(playerTable, hbox);


    // I used this link to help me figure out this function
    // https://stackoverflow.com/questions/39782952/javafx-set-cell-background-color-of-tablecolumn
    playerNameColumn.setCellFactory(e -> new TableCell<>() {
      @Override
      public void updateItem(String item, boolean empty) {
        // Always invoke super constructor.
        super.updateItem(item, empty);

        String greyColor = String.format("-fx-background-color: %s", GameColors.DARK_GREY.getColorValue());

        this.setTextFill(Color.WHITE);
        this.setFont(font);
        this.setAlignment(Pos.CENTER);
        this.setStyle(greyColor);

        if (item == null || empty) {
          setText(null);
        } else {
          setText(item);

        }
      }
    });

    playerScoreColumn.setCellFactory(e -> new TableCell<Player, Integer>() {
      @Override
      public void updateItem(Integer item, boolean empty) {
        // Always invoke super constructor.
        super.updateItem(item, empty);

        String greyColor = String.format("-fx-background-color: %s", GameColors.DARK_GREY.getColorValue());

        this.setStyle(greyColor);
        this.setTextFill(Color.WHITE);
        this.setAlignment(Pos.CENTER);
        this.setFont(font);
//        System.out.println(this.lookup());

        if (item == null || empty) {
          setText(null);
        } else {
          setText(item.toString());

        }
      }
    });
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
    players.add(new Player("Player 6", 60));
    players.add(new Player("Player 7", 69));
    players.add(new Player("Player 8", 32));
    players.add(new Player("Player 9", 100));
    players.add(new Player("Player 10", 420));

    return players;
  }
}
