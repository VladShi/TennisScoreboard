package ru.vladshi.javalearning.tennisscoreboard.dao;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Match;

import java.util.List;

public interface MatchDao {

    List<Match> findAllByFilter(String name, int from, int pageSize);

    long countByFilter(String name);

    void save(Match match);
}
