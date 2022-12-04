package com.example.snake;

import com.example.snake.game.Game;
import com.example.snake.game.MovementController;
import com.example.snake.graphics.Renderer;
import com.example.snake.menu.MainMenu;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SnakeApplication extends Application {

  // Arbitrary dimensions for now
  private static final int WINDOW_WIDTH = 640;
  private static final int WINDOW_HEIGHT = 480;


  @Override
  public void start(Stage stage) {

    MainMenu mainMenu = new MainMenu();
    Scene scene = new Scene(mainMenu.getMenuRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);
//    Label message = new Label("snake game");
//    message.setStyle("-fx-border-color: blue; -fx-border-width: 2px; " +
//      "-fx-background-color: white; -fx-padding: 6px");

    mainMenu.onStartPressed(event -> startGame(stage));
    mainMenu.onOptionsPressed(event -> showOptionsMenu(stage));
    mainMenu.onLeaderboardPressed(event -> showLeaderboardMenu(stage));

    // Disabled resizing for now
    stage.setResizable(false);
    stage.setTitle("Snake Base");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

  public static void startGame(Stage stage) {
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TilePane root = new TilePane(canvas);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    Renderer renderer = new Renderer(canvas);

    MovementController movementController = new MovementController();
    scene.setOnKeyPressed(movementController);
    scene.setOnKeyReleased(movementController);
    Game currentGame = new Game(renderer, movementController);

    stage.setScene(scene);

    currentGame.start();

  }

  public static void showOptionsMenu(Stage stage) {
    // Create the layout for the options menu here
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TilePane root = new TilePane(canvas);

    root.setBackground(Background.fill(Color.valueOf("3FA7D6")));

    // Set the background to a placeholder color for now
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    stage.setScene(scene);
  }

  public void showLeaderboardMenu(Stage stage) {
    TableView<Player> playerTable;

    double COLUMN_WIDTH = 100;
    double COLUMN_HEIGHT= 100;

    TableColumn<Player,String> playerNameColumn = new TableColumn<>("Player Name");
    playerNameColumn.setMinWidth(COLUMN_WIDTH);
    playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

    TableColumn<Player,Integer> playerScoreColumn = new TableColumn<>("Player Score");
    playerScoreColumn.setMinWidth(COLUMN_HEIGHT);
    playerScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

    playerTable = new TableView<>();
    playerTable.setItems(getPlayers());
    playerTable.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);
    playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    playerTable.getColumns().add(playerNameColumn);
    playerTable.getColumns().add(playerScoreColumn);

    MainMenu.onMainMenu(event -> start(stage));
    HBox hbox = new HBox();
    hbox.setAlignment(Pos.BOTTOM_LEFT);
    hbox.getChildren().addAll(MainMenu.mainMenu);

    VBox vbox = new VBox();
    vbox.setBackground(Background.fill(Color.valueOf("DCDCDC")));
    vbox.getChildren().addAll(playerTable,MainMenu.mainMenu);

    Scene scene = new Scene(vbox, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.setScene(scene);
   }
    public static ObservableList<Player> getPlayers() {

      ObservableList<Player> Players = FXCollections.observableArrayList();
      Players.add(new Player("Player 1", 91));
      Players.add(new Player("Player 2", 76));
      Players.add(new Player("Player 3", 58));
      Players.add(new Player("Player 4", 36));
      Players.add(new Player("Player 5", 23));
      return Players;
    }


}