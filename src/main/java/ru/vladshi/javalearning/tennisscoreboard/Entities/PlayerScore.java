package ru.vladshi.javalearning.tennisscoreboard.Entities;

public class PlayerScore {

    public int set;
    public int game;
    public Point point;

    public PlayerScore() {
        this.set = 0;
        this.game = 0;
        this.point = Point.ZERO;
    }
}
