package ru.vladshi.javalearning.tennisscoreboard.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;
import ru.vladshi.javalearning.tennisscoreboard.exceptions.DatabaseException;
import ru.vladshi.javalearning.tennisscoreboard.util.HibernateUtil;

import java.util.Optional;

public enum PlayerDaoImpl implements PlayerDao {

    INSTANCE;

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Optional<Player> findByName(String name) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("from Player where name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();

        } catch (HibernateException e) {
            throw new DatabaseException("Error when working with a database");
        }
    }

    @Override
    public Player saveAndReturn(Player player) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(player);
            session.flush();
            return player;

        } catch (HibernateException e) {
            throw new DatabaseException("Error when working with a database");
        }
    }
}
