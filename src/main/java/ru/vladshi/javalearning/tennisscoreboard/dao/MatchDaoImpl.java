package ru.vladshi.javalearning.tennisscoreboard.dao;

import jakarta.persistence.criteria.*;
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
    public List<Match> findAllByFilter(String filter, int from, int maxPageSize) {
        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Match> criteriaQuery = builder.createQuery(Match.class);
            Root<Match> fromMatch = criteriaQuery.from(Match.class);
            fromMatch.fetch("playerOne", JoinType.LEFT);
            fromMatch.fetch("playerTwo", JoinType.LEFT);

            Predicate nameIsPresentInMatch = buildFilterByStringPredicate(builder, fromMatch, filter);
            criteriaQuery.select(fromMatch).where(nameIsPresentInMatch);

            return session.createQuery(criteriaQuery)
                            .setFirstResult(from)
                            .setMaxResults(maxPageSize)
                            .getResultList();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new RuntimeException("Error when working with a database", e);
        }
    }

    @Override
    public long countByFilter(String filter) {
        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
            Root<Match> fromMatch = countQuery.from(Match.class);
            fromMatch.join("playerOne", JoinType.LEFT);
            fromMatch.join("playerTwo", JoinType.LEFT);

            Predicate nameIsPresentInMatch = buildFilterByStringPredicate(builder, fromMatch, filter);
            countQuery.select(builder.countDistinct(fromMatch.get("id"))).where(nameIsPresentInMatch);

            return session.createQuery(countQuery).getSingleResult();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new RuntimeException("Error when working with a database", e);
        }
    }

    @Override
    public void save(Match match) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(match);

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new RuntimeException("Error when working with a database", e);
        }
    }

    private Predicate buildFilterByStringPredicate(CriteriaBuilder builder, Root<?> root, String filter) {
        return builder.or(
                builder.like(builder.lower(root.get("playerOne").get("name")), "%" + filter.toLowerCase() + "%"),
                builder.like(builder.lower(root.get("playerTwo").get("name")), "%" + filter.toLowerCase() + "%")
        );
    }
}
