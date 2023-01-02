package com.example.snake.player;

import com.example.snake.game.Difficulty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PlayerScore {

  private final String playerName;
  private final int score;
  private final Difficulty gameDifficulty;

  @JsonCreator
  public PlayerScore(@JsonProperty("playerName") String playerName, @JsonProperty("score") int score, @JsonProperty("gameDifficulty") Difficulty gameDifficulty) {
    this.playerName = playerName;
    this.score = score;
    this.gameDifficulty = gameDifficulty;
  }

  public int getScore() {
    return score;
  }

  public String getPlayerName() {
    return playerName;
  }

  public Difficulty getGameDifficulty() {
    return gameDifficulty;
  }

  @Override
  public String toString() {
    return playerName + " (" + score + ")" + " <" + gameDifficulty + ">";
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
    return Objects.hash(playerName, score, gameDifficulty);
  }
}


