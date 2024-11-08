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

    @Override
    public List<Integer> getPageNumberRange(int currentPage, int lastPageNumber, int maxCount) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(currentPage);
        for (int i = 1; i <= maxCount; i++) {
            int previousPage = currentPage - i;
            if (previousPage > 0)
                list.addFirst(previousPage);
            if (list.size() >= maxCount) {
                break;
            }
            int nextPage = currentPage + i;
            if (nextPage <= lastPageNumber)
                list.addLast(nextPage);
            if (list.size() >= maxCount) {
                break;
            }
        }
        return list;
    }
}
