package ru.vladshi.javalearning.tennisscoreboard.Entities;

public class MatchScore {

    public final Player playerOne;
    public final Player playerTwo;
    public PlayerScore playerOneScore;
    public PlayerScore playerTwoScore;

    public MatchScore(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.playerOneScore = new PlayerScore();
        this.playerTwoScore = new PlayerScore();
    }
}
