package ru.vladshi.javalearning.tennisscoreboard.services;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.*;

public enum MatchScoreCalculationServiceImpl implements MatchScoreCalculationService {

    INSTANCE;

    private static final int NUMBER_OF_SETS_TO_WIN_MATCH = 2;

    @Override
    public MatchScore addPointToPlayer(MatchScore matchScore, String playerOrdinal) {
        if (!isPlayerOrdinalValid(playerOrdinal) || matchScore == null || matchScore.isFinished) {  // TODO проверка isFinished добавить
            return matchScore;
        }
        PlayerOrdinal pointWinner = getPointWinner(playerOrdinal);  // TODO scoredPlayer
        PlayerOrdinal pointLoser = getPointLoser(playerOrdinal);  // TODO loserPlayer
        SetScore currentSet = matchScore.sets.getLast();
        GameScore currentGame = currentSet.games.getLast();

        if (currentSet.hasTiebreak && !currentSet.tiebreak.isFinished) {
            TiebreakScore tiebreak = currentSet.tiebreak;
            calculateTiebreak(tiebreak, pointWinner, pointLoser);
            if (!currentSet.tiebreak.isFinished) {
                return matchScore;
            }
            currentSet.setScore(pointWinner, currentSet.getScore(pointWinner) + 1);
            currentSet.isFinished = true;
            calculateInMatchScore(matchScore, pointWinner);  // TODO возможно стоит проверять что тайбрейка нет и внутри этого условия выполнить два метода для гейма и сета
            return matchScore;                               //  потом в блоке элсе выполнить прогонку тайбрейка и после закрытия элс сделать прогонку для матча, один раз, и не будет два как сейчас? DRY
        }

        calculateInGameScore(currentGame, pointWinner, pointLoser);  // TODO proceedGame
        if (!currentGame.isFinished) {
            return matchScore;
        }

        calculateInSetScore(currentSet, pointWinner, pointLoser);  // TODO proceedSet
        if (!currentSet.isFinished) {
            return matchScore;
        }

        calculateInMatchScore(matchScore, pointWinner);  // TODO proceedMatch
        return matchScore;
    }

    private void calculateTiebreak(TiebreakScore tiebreak, PlayerOrdinal pointWinner, PlayerOrdinal pointLoser) {
        int pointWinnerScore = tiebreak.getScore(pointWinner);
        int pointLoserScore = tiebreak.getScore(pointLoser);
        boolean willTiebreakBeFinished = pointWinnerScore >= 6 && (pointWinnerScore - pointLoserScore) >= 1;
        if (willTiebreakBeFinished) {
            tiebreak.isFinished = true;
        }
        tiebreak.setScore(pointWinner, pointWinnerScore + 1);  // TODO сначала прибавить а потом сравнивать с константой вместо магических чисел и тогда will можно заменить на is
    }

    private void calculateInGameScore(GameScore game, PlayerOrdinal pointWinner, PlayerOrdinal pointLoser) {
        Point pointWinnerScore = game.getScore(pointWinner);  // TODO pointsOfScorer
        Point pointLoserScore = game.getScore(pointLoser);  // TODO pointsOfLoser
        boolean willGameBeFinished = pointWinnerScore == Point.AD
                || (pointWinnerScore == Point.FORTY && pointLoserScore.ordinal() < Point.FORTY.ordinal());
        if (willGameBeFinished) {
            game.isFinished = true;
        } else if (pointLoserScore == Point.AD) {
            game.setScore(pointLoser, Point.FORTY);
        } else {
            game.setScore(pointWinner, pointWinnerScore.next());  // TODO в Гейме НЕ получится сначала прибавить а потом сравнивать с константой вместо магических чисел
        }
    }
    // TODO посмотреть какие ещё условия можно вынести с осмысленным названием
    private void calculateInSetScore(SetScore set, PlayerOrdinal pointWinner, PlayerOrdinal pointLoser) {
        int gameWinnerScore = set.getScore(pointWinner);   // TODO gamesOfScorer
        int gameLoserScore = set.getScore(pointLoser);  // TODO gamesOfLoser
//        boolean willSetBeFinished = (gameWinnerScore == 5 && gameLoserScore < 5)
//                || (gameWinnerScore == 6 && gameLoserScore == 5);
        boolean willSetBeFinished = (gameWinnerScore >= 5 && (gameWinnerScore - gameLoserScore) >= 1);
        if (willSetBeFinished) {
            set.isFinished = true;
        } else if (gameWinnerScore == 5 && gameLoserScore == 6) {
            set.hasTiebreak = true;
            set.tiebreak = new TiebreakScore();
        } else {
            set.games.add(new GameScore());
        }
        set.setScore(pointWinner, gameWinnerScore + 1);  // TODO сначала прибавить а потом сравнивать с константой вместо магических чисел и тогда will можно заменить на is
    }

    private void calculateInMatchScore(MatchScore matchScore, PlayerOrdinal pointWinner) {
        int setWinnerScore = matchScore.getScore(pointWinner);  // TODO setsOfScorer
        boolean willMatchBeFinished = setWinnerScore == NUMBER_OF_SETS_TO_WIN_MATCH - 1;
        if (willMatchBeFinished) {
            matchScore.isFinished = true;
        } else {
            matchScore.sets.add(new SetScore());
        }
        matchScore.setScore(pointWinner, setWinnerScore + 1);  // TODO сначала прибавить а потом сравнивать с константой вместо магических чисел и тогда will можно заменить на is
    }

    private boolean isPlayerOrdinalValid(String playerOrdinal) {
        String playerOneOrdinal = PlayerOrdinal.PLAYER_ONE.toString();
        String playerTwoOrdinal = PlayerOrdinal.PLAYER_TWO.toString();
        return playerOrdinal != null
                && (playerOrdinal.equals(playerOneOrdinal) || playerOrdinal.equals(playerTwoOrdinal));
    }

    private PlayerOrdinal getPointWinner(String playerOrdinal) {  // TODO getScoredPlayer
        String playerOneOrdinal = PlayerOrdinal.PLAYER_ONE.toString();
        return playerOrdinal.equals(playerOneOrdinal) ? PlayerOrdinal.PLAYER_ONE : PlayerOrdinal.PLAYER_TWO;
    }

    private PlayerOrdinal getPointLoser(String playerOrdinal) {  // TODO getLoserPlayer
        String playerOneOrdinal = PlayerOrdinal.PLAYER_ONE.toString();
        return playerOrdinal.equals(playerOneOrdinal) ? PlayerOrdinal.PLAYER_TWO : PlayerOrdinal.PLAYER_ONE;
    }
}
