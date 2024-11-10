package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;

public interface PlayerService {
    Player getOrSaveByName(String playerName);
}
