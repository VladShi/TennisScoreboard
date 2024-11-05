package ru.vladshi.javalearning.tennisscoreboard.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Match;
import ru.vladshi.javalearning.tennisscoreboard.util.HibernateUtil;

import java.util.List;

public enum MatchDaoImpl implements MatchDao {

    INSTANCE;

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<Match> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Match", Match.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new RuntimeException("Error when working with a database", e);
        }
    }
}
