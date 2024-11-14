package ru.vladshi.javalearning.tennisscoreboard.Entities.Scores;

abstract public class ScoreHolder {

    protected int playerOneScore;
    protected int playerTwoScore;
    public boolean isFinished;

    public ScoreHolder() {
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.isFinished = false;
    }

    public int getScore(PlayerOrdinal playerOrdinal) {
        switch (playerOrdinal) {
            case PLAYER_ONE -> {return this.playerOneScore;}
            case PLAYER_TWO -> {return this.playerTwoScore;}
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);
        }
    }

    public void setScore(PlayerOrdinal playerOrdinal, int score) {
        switch (playerOrdinal) {
            case PLAYER_ONE -> this.playerOneScore = score;
            case PLAYER_TWO -> this.playerTwoScore = score;
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);
        }
    }

    public void increaseScore(PlayerOrdinal playerOrdinal) {
        switch (playerOrdinal) {
            case PLAYER_ONE -> this.playerOneScore++;
            case PLAYER_TWO -> this.playerTwoScore++;
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);
        }
    }
}
