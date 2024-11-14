package ru.vladshi.javalearning.tennisscoreboard.Entities.Scores;

public enum Point {
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    AD("AD");
    public final String value;

    Point(String value) {
        this.value = value;
    }

    public Point next() {
        return values()[(this.ordinal() + 1)];
    }
}
