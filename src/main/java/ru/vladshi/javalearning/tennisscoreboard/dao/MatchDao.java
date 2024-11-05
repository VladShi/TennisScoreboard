package ru.vladshi.javalearning.tennisscoreboard.dao;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Match;

import java.util.List;

public interface MatchDao {

    List<Match> getAll();
}
