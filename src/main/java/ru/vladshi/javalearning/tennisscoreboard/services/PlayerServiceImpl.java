package ru.vladshi.javalearning.tennisscoreboard.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;
import ru.vladshi.javalearning.tennisscoreboard.dao.PlayerDao;
import ru.vladshi.javalearning.tennisscoreboard.dao.PlayerDaoImpl;
import ru.vladshi.javalearning.tennisscoreboard.util.HibernateUtil;

import java.util.Optional;

public enum PlayerServiceImpl implements PlayerService {

    INSTANCE;

    private final PlayerDao playerDao = PlayerDaoImpl.INSTANCE;
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Player getOrSaveByName(String playerName) {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Optional<Player> playerOptional = playerDao.findByName(playerName);
            Player player = playerOptional.orElseGet(() -> playerDao.saveAndReturn(new Player(playerName)));

            session.getTransaction().commit();
            return player;

        } catch (HibernateException e) {
            e.printStackTrace();
            throw  new RuntimeException("Error when working with a database", e);
        }
    }
}
