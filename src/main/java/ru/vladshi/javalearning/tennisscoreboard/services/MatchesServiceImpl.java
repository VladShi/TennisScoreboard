package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Match;
import ru.vladshi.javalearning.tennisscoreboard.dao.MatchDao;
import ru.vladshi.javalearning.tennisscoreboard.dao.MatchDaoImpl;

import java.util.List;

public enum MatchesServiceImpl implements MatchesService {

    INSTANCE;

    private final MatchDao matchDao = MatchDaoImpl.INSTANCE;

    @Override
    public List<Match> getAllMatches() {
        return matchDao.getAll();
    }
}
