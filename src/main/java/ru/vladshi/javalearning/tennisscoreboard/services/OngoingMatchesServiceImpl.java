package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.MatchScore;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum OngoingMatchesServiceImpl implements OngoingMatchesService {

    INSTANCE;

    private final PlayerService playerService = PlayerServiceImpl.INSTANCE;
    private final ConcurrentMap<UUID, MatchScore> ongoingMatches = new ConcurrentHashMap<>();

    @Override
    public UUID addNewMatchScore(String playerOneName, String playerTwoName) {

        Player playerOne = playerService.getOrSaveByName(playerOneName);
        Player playerTwo = playerService.getOrSaveByName(playerTwoName);

        UUID uuid = UUID.randomUUID();
        ongoingMatches.put(uuid, new MatchScore(playerOne, playerTwo));
        return uuid;
    }

    @Override
    public Optional<MatchScore> getMatchScore(String uuidString) {
        if (uuidString == null || !uuidString.matches(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            return Optional.empty();
        }
        UUID uuid = UUID.fromString(uuidString);
        return Optional.ofNullable(ongoingMatches.get(uuid));
    }
}
