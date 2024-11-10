package ru.vladshi.javalearning.tennisscoreboard.dao;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;

import java.util.Optional;

public interface PlayerDao {

    Optional<Player> findByName(String name);

    Player saveAndReturn(Player player);
}
