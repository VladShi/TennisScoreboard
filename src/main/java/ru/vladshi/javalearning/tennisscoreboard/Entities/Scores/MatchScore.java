package ru.vladshi.javalearning.tennisscoreboard.Entities.Scores;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;

import java.util.LinkedList;
import java.util.List;

public class MatchScore {

    public Player playerOne;
    public Player playerTwo;

    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    public List<SetScore> sets;

    public boolean isFinished = false;

    public MatchScore(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.sets = new LinkedList<>();
        this.sets.add(new SetScore());
    }

    public int getScore(PlayerOrdinal playerOrdinal) {
        switch (playerOrdinal) {
            case PLAYER_ONE -> {return playerOneScore;}
            case PLAYER_TWO -> {return playerTwoScore;}
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);
        }
    }

    public void setScore(PlayerOrdinal playerOrdinal, int score) {
        switch (playerOrdinal) {
            case PLAYER_ONE -> playerOneScore = score;
            case PLAYER_TWO -> playerTwoScore = score;
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);
        }
    }
}
