package com.example.snake.player;

public class Player {
  private final String playerName;
  private final String score;

  public Player(String playerName, int score) {
    this.playerName = playerName;
    this.score = Integer.toString(score);
  }

  public String getScore() {
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


