package ru.vladshi.javalearning.tennisscoreboard.Entities.Scores;

public enum PlayerOrdinal {
    PLAYER_ONE("playerOne"), PLAYER_TWO("playerTwo");

    private final String value;

    PlayerOrdinal(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}