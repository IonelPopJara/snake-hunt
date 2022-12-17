package com.example.snake.player;

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
}


