package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.MatchScore;

public interface MatchScoreCalculationService {
    MatchScore addPointToPlayer(MatchScore matchScore, String playerOrdinal);
}
