package com.example.snake.player;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerScore {

  private final String playerName;
  private final int score;

  @JsonCreator
  public PlayerScore(@JsonProperty("playerName") String playerName, @JsonProperty("score") int score) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlayerScore that = (PlayerScore) o;
    return score == that.score && Objects.equals(playerName, that.playerName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerName, score);
  }
}


