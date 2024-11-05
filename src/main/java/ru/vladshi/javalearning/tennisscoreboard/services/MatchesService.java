package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Match;

import java.util.List;

public interface MatchesService {

    List<Match> getAllMatches();
}
