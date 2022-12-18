package com.example.snake.player;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Comparator;

public class Player {
  private final String playerName;
  private final int score;

  public Player(String playerName, int score) {
    this.playerName = playerName;
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public String getPlayerName() {
    return playerName;
  }

  @Override
  public String toString() {
    return playerName + " (" + score + ")";
  }
  public static void saveHighScores(ObservableList<Player> highScores) {
      try {
        Gson gson = new Gson();
        String json = gson.toJson(highScores());

        try (Writer writer = new FileWriter("highscores.json")) {
          writer.write(json);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  public static ObservableList<Player> highScores() {
    ObservableList<Player> players = FXCollections.observableArrayList();
    players.add(new Player("Player 1", 91));
    players.add(new Player("Player 2", 76));
    players.add(new Player("Player 3", 55));
    players.add(new Player("Player 4", 36));
    players.add(new Player("Player 5", 23));
    return players;
  }
    public static ObservableList<Player> loadHighScores() {
      try {
        Gson gson = new Gson();

        try (Reader reader = new FileReader("highscores.json")) {
          Player[] highScores = gson.fromJson(reader, Player[].class);
          return FXCollections.observableArrayList(highScores);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      return FXCollections.observableArrayList();
    }

    public static ObservableList<Player> getPlayers() {
      ObservableList<Player> players = loadHighScores();
      players.sort(Comparator.comparingInt(Player::getScore).reversed());
      return (ObservableList<Player>) players.subList(0, Math.min(players.size(), 10));
    }
}



