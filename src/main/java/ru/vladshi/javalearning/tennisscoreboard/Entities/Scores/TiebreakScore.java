package ru.vladshi.javalearning.tennisscoreboard.Entities.Scores;

public class TiebreakScore { // TODO вынести в абстрактный класс повторяющиеся методы и поля для Тайбрэйка, Сэта и матча. Инициализация через конструктор.

    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    public boolean isFinished = false;

    public int getScore(PlayerOrdinal playerOrdinal) {
        switch (playerOrdinal) {
            case PLAYER_ONE -> {return playerOneScore;}
            case PLAYER_TWO -> {return playerTwoScore;}
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);

        }
    }

    public void setScore(PlayerOrdinal playerOrdinal, int score) {  // TODO increaseScoreFor вместо setScore , поскольку мы везде увеличиваем на 1. Но для тестов нужна возможность на любое число увеличить. Два метода?
        switch (playerOrdinal) {
            case PLAYER_ONE -> playerOneScore = score;
            case PLAYER_TWO -> playerTwoScore = score;
            default -> throw new IllegalStateException("Invalid player ordinal: " + playerOrdinal);
        }
    }
}
