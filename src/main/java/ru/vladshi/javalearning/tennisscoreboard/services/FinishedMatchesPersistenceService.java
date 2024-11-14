package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.MatchScore;

public interface FinishedMatchesPersistenceService {

    void save(MatchScore matchScore);
}
