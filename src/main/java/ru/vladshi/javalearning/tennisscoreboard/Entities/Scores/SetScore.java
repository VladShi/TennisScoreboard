package ru.vladshi.javalearning.tennisscoreboard.Entities.Scores;

import java.util.LinkedList;
import java.util.List;

public class SetScore {

    private int playerOneScore;
    private int playerTwoScore;
    public List<GameScore> games;

    public boolean hasTiebreak = false;
    public TiebreakScore tiebreak;

    public boolean isFinished = false;

    public SetScore() {
        this.games = new LinkedList<>();
        this.games.add(new GameScore());
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
