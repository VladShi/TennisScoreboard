package ru.vladshi.javalearning.tennisscoreboard.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Match;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.MatchScore;
import ru.vladshi.javalearning.tennisscoreboard.dao.MatchDao;
import ru.vladshi.javalearning.tennisscoreboard.dao.MatchDaoImpl;
import ru.vladshi.javalearning.tennisscoreboard.exceptions.DatabaseException;
import ru.vladshi.javalearning.tennisscoreboard.util.HibernateUtil;

public enum FinishedMatchesPersistenceServiceImpl implements FinishedMatchesPersistenceService {

    INSTANCE;

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final MatchDao matchDao = MatchDaoImpl.INSTANCE;

    @Override
    public void save(MatchScore matchScore) {
        Player winner = matchScore.getWinner().orElseThrow(() -> new IllegalStateException("Match is not finished"));
        Match match = new Match(matchScore.playerOne, matchScore.playerTwo, winner);

        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            matchDao.save(match);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new DatabaseException("Error when working with a database");
        }
    }
}
