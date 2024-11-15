package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.*;

import static ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.Point.*;

public enum MatchScoreCalculationServiceImpl implements MatchScoreCalculationService {

    INSTANCE;

    private static final int NUMBER_OF_SETS_TO_WIN_MATCH = 2;
    private static final int NUMBER_OF_POINTS_TO_WIN_TIEBREAK = 7;
    private static final int NUMBER_OF_GAMES_TO_WIN_SET = 6;

    @Override
    public MatchScore addPointToPlayer(MatchScore match, String playerOrdinal) {
        if (!isPlayerOrdinalValid(playerOrdinal) || match == null || match.isFinished) {
            return match;
        }
        PlayerOrdinal scoredPlayer = getScoredPlayer(playerOrdinal);
        PlayerOrdinal loserPlayer = getLoserPlayer(playerOrdinal);
        SetScore currentSet = match.getSet();
        GameScore currentGame = match.getGame();

        if (!currentSet.hasTiebreak) {

            increaseGameScore(currentGame, scoredPlayer, loserPlayer);
            if (!currentGame.isFinished) {
                return match;
            }

            increaseSetScore(currentSet, scoredPlayer, loserPlayer);
            if (!currentSet.isFinished) {
                return match;
            }

        } else {  // if current set has tiebreak
            TiebreakScore tiebreak = currentSet.tiebreak;
            increaseTiebreakScore(tiebreak, scoredPlayer, loserPlayer);
            if (!tiebreak.isFinished) {
                return match;
            }
            currentSet.increaseScore(scoredPlayer);
            currentSet.isFinished = true;
        }

        match.increaseScore(scoredPlayer);
        int setsOfScorer = match.getScore(scoredPlayer);
        if (setsOfScorer == NUMBER_OF_SETS_TO_WIN_MATCH) {
            match.isFinished = true;
        } else {
            startNextSetIn(match);
        }
        return match;
    }

    private void increaseTiebreakScore(TiebreakScore tiebreak, PlayerOrdinal scoredPlayer, PlayerOrdinal loserPlayer) {
        tiebreak.increaseScore(scoredPlayer);

        int pointsOfScorer = tiebreak.getScore(scoredPlayer);
        int pointsOfLoser = tiebreak.getScore(loserPlayer);
        int differenceInScore = pointsOfScorer - pointsOfLoser;

        boolean isTiebreakFinished = pointsOfScorer >= NUMBER_OF_POINTS_TO_WIN_TIEBREAK && differenceInScore >= 2;

        if (isTiebreakFinished) {
            tiebreak.isFinished = true;
        }
    }

    private void increaseGameScore(GameScore game, PlayerOrdinal scoredPlayer, PlayerOrdinal pointLoser) {
        Point pointsOfScorer = game.getScore(scoredPlayer);
        Point pointsOfLoser = game.getScore(pointLoser);

        boolean hasScoredPlayerAdvantage = pointsOfScorer == AD;
        boolean hasScoredPlayer_40 = pointsOfScorer == FORTY;
        boolean hasLoserPlayerLessThan_40 = pointsOfLoser.ordinal() < FORTY.ordinal();

        boolean willGameBeFinished = hasScoredPlayerAdvantage || (hasScoredPlayer_40 && hasLoserPlayerLessThan_40);

        if (willGameBeFinished) {
            game.isFinished = true;
        } else if (pointsOfLoser == AD) {
            game.setScore(pointLoser, FORTY);
        } else {
            game.increaseScore(scoredPlayer);
        }
    }

    private void increaseSetScore(SetScore set, PlayerOrdinal scoredPlayer, PlayerOrdinal pointLoser) {
        set.increaseScore(scoredPlayer);

        int gamesOfScorer = set.getScore(scoredPlayer);
        int gamesOfLoser = set.getScore(pointLoser);
        int differenceInScore = gamesOfScorer - gamesOfLoser;

        boolean isSetFinished = gamesOfScorer >= NUMBER_OF_GAMES_TO_WIN_SET && differenceInScore >= 2;

        if (isSetFinished) {
            set.isFinished = true;
        } else if (gamesOfScorer == NUMBER_OF_GAMES_TO_WIN_SET && gamesOfLoser == NUMBER_OF_GAMES_TO_WIN_SET) {
            set.hasTiebreak = true;
            set.tiebreak = new TiebreakScore();
        } else {
            startNextGameIn(set);
        }
    }

    private boolean isPlayerOrdinalValid(String playerOrdinal) {
        String playerOneOrdinal = PlayerOrdinal.PLAYER_ONE.toString();
        String playerTwoOrdinal = PlayerOrdinal.PLAYER_TWO.toString();
        return playerOrdinal != null
                && (playerOrdinal.equals(playerOneOrdinal) || playerOrdinal.equals(playerTwoOrdinal));
    }

    private PlayerOrdinal getScoredPlayer(String playerOrdinal) {
        String playerOneOrdinal = PlayerOrdinal.PLAYER_ONE.toString();
        return playerOrdinal.equals(playerOneOrdinal) ? PlayerOrdinal.PLAYER_ONE : PlayerOrdinal.PLAYER_TWO;
    }

    private PlayerOrdinal getLoserPlayer(String playerOrdinal) {
        String playerOneOrdinal = PlayerOrdinal.PLAYER_ONE.toString();
        return playerOrdinal.equals(playerOneOrdinal) ? PlayerOrdinal.PLAYER_TWO : PlayerOrdinal.PLAYER_ONE;
    }

    private static void startNextGameIn(SetScore set) {
        set.games.add(new GameScore());
    }

    private static void startNextSetIn(MatchScore match) {
        match.sets.add(new SetScore());
    }
}
