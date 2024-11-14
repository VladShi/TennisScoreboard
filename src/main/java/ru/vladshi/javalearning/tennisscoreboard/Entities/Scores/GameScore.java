package ru.vladshi.javalearning.tennisscoreboard.Entities.Scores;

public class GameScore {

    private Point playerOneScore = Point.ZERO;
    private Point playerTwoScore = Point.ZERO;
    public boolean isFinished = false;

    public Point getScore(PlayerOrdinal playerOrdinal) {
        switch (playerOrdinal) {
            case PLAYER_ONE -> {return playerOneScore;}
            case PLAYER_TWO -> {return playerTwoScore;}
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);
        }
    }

    public void setScore(PlayerOrdinal playerOrdinal, Point score) {
        switch (playerOrdinal) {
            case PLAYER_ONE -> playerOneScore = score;
            case PLAYER_TWO -> playerTwoScore = score;
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);
        }
    }

    public void increaseScore(PlayerOrdinal playerOrdinal) {
        switch (playerOrdinal) {
            case PLAYER_ONE -> playerOneScore = playerOneScore.next();
            case PLAYER_TWO -> playerTwoScore = playerTwoScore.next();
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);
        }
    }
}
