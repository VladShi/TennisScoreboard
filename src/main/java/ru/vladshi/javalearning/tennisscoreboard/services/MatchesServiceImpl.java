package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Match;
import ru.vladshi.javalearning.tennisscoreboard.dao.MatchDao;
import ru.vladshi.javalearning.tennisscoreboard.dao.MatchDaoImpl;

import java.util.LinkedList;
import java.util.List;

public enum MatchesServiceImpl implements MatchesService {

    INSTANCE;

    private final MatchDao matchDao = MatchDaoImpl.INSTANCE;

    @Override
    public List<Match> getListOfMatches(String name, int pageNumber, int pageSize) {
        int from = (pageNumber - 1) * pageSize;
        return matchDao.findAllByFilter(name, from, pageSize);
    }

    @Override
    public int getLastPageNumberByPlayerName(String name, int pageSize) {
        long countResultsL = matchDao.countByFilter(name);
        int countResults = Math.toIntExact(countResultsL);
        int additionForRemainder = (countResults % pageSize) > 0 ? 1 : 0;
        return countResults / pageSize + additionForRemainder;
    }
}
