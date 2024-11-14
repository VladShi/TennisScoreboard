package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.MatchScore;

import java.util.Optional;
import java.util.UUID;

public interface OngoingMatchesService {

    UUID addNewMatchScore(String playerOneName, String playerTwoName);

    Optional<MatchScore> getMatchScore(String uuidString);
}
