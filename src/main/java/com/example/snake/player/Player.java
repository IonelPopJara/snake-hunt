package com.example.snake.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class Player {

  private final String playerName;
  private final int score;

  @JsonCreator
  public Player(@JsonProperty("playerName") String playerName, @JsonProperty("score") int score) {
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


