package ru.vladshi.javalearning.tennisscoreboard.exceptions;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String messageError) {
        super(messageError);
    }
}
