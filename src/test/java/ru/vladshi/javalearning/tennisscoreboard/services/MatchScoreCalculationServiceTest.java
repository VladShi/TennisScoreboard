package ru.vladshi.javalearning.tennisscoreboard.services;

import org.junit.jupiter.api.*;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.*;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScoreCalculationServiceTest {

    private static MatchScore matchScore;
    private static MatchScore matchScoreExpected;

    private static final PlayerOrdinal PLAYER_ONE = PlayerOrdinal.PLAYER_ONE;
    private static final PlayerOrdinal PLAYER_TWO = PlayerOrdinal.PLAYER_TWO;
    private static final String PLAYER_ONE_STRING = PlayerOrdinal.PLAYER_ONE.toString();
    private static final String PLAYER_TWO_STRING = PlayerOrdinal.PLAYER_TWO.toString();
    private static final int NUMBER_OF_SETS_TO_WIN_MATCH = 2;
    private static final int NUMBER_OF_POINTS_TO_WIN_TIEBREAK = 7;

    private final MatchScoreCalculationService matchScoreCalculationService = MatchScoreCalculationServiceImpl.INSTANCE;

    @BeforeAll
    static void setUp() {
        Player playerOne = new Player("NameOfPlayerOne");
        playerOne.setId(1);
        Player playerTwo = new Player("NameOfPlayerTwo");
        playerTwo.setId(2);
        matchScore = new MatchScore(playerOne, playerTwo);
        matchScoreExpected = new MatchScore(playerOne, playerTwo);
    }

    @BeforeEach
    public void clearPlayersPoints() {
        resetMatchScore(matchScore);
        resetMatchScore(matchScoreExpected);
    }

    @Test
    @DisplayName("При счете 0-0, когда первый теннисист набирает очко, то счет становится 15-0")
    public void given0_0_whenPlayerOneScores_then15_0() {
        fillMatchScore(PLAYER_ONE, 0, 0, Point.ZERO);
        fillMatchScore(PLAYER_TWO, 0, 0, Point.ZERO);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 0, Point.FIFTEEN);
        fillMatchScoreExpected(PLAYER_TWO, 0, 0, Point.ZERO);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 40-0, когда второй теннисист набирает очко, то счет становится 40-15")
    public void given40_0_whenPlayerTwoScores_then40_15() {
        fillMatchScore(PLAYER_ONE, 0, 0, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 0, 0, Point.ZERO);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_TWO_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 0, Point.FORTY);
        fillMatchScoreExpected(PLAYER_TWO, 0, 0, Point.FIFTEEN);

        assertMatchScoreEquals(matchScoreExpected, result);
    }


    @Test
    @DisplayName("При счете 40-40, когда первый теннисист набирает очко, то счет становится AD-40")
    public void given40_40_whenPlayerOneScores_thenAD_40() {
        fillMatchScore(PLAYER_ONE, 0, 0, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 0, 0, Point.FORTY);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 0, Point.AD);
        fillMatchScoreExpected(PLAYER_TWO, 0, 0, Point.FORTY);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 40-AD, когда первый теннисист набирает очко, то счет становится 40-40")
    public void given40_AD_whenPlayerOneScores_then40_40() {
        fillMatchScore(PLAYER_ONE, 0, 0, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 0, 0, Point.AD);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 0, Point.FORTY);
        fillMatchScoreExpected(PLAYER_TWO, 0, 0, Point.FORTY);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 40-0, когда первый теннисист набирает очко, то счет становится 1-0 в сете и 0-0 в гейме")
    public void given40_0_whenPlayerOneScores_then1_0inSetAnd0_0inGame() {
        fillMatchScore(PLAYER_ONE, 0, 0, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 0, 0, Point.ZERO);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 1, Point.ZERO);
        fillMatchScoreExpected(PLAYER_TWO, 0, 0, Point.ZERO);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 40-AD, когда второй теннисист набирает очко, то счет становится 0-1 в сете и 0-0 в гейме")
    public void given40_AD_whenPlayerTwoScores_then0_1inSetAnd0_0inGame() {
        fillMatchScore(PLAYER_ONE, 0, 0, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 0, 0, Point.AD);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_TWO_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 0, Point.ZERO);
        fillMatchScoreExpected(PLAYER_TWO, 0, 1, Point.ZERO);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 2-5 в сете и 40-30 в гейме, когда первый теннисист набирает очко," +
            " то счет становится 3-5 в сете и 0-0 в гейме")
    public void given2_5inSetAnd40_30inGame_whenPlayerOneScores_then3_5inSetAnd0_0inGame() {
        fillMatchScore(PLAYER_ONE, 0, 2, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 0, 5, Point.THIRTY);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 3, Point.ZERO);
        fillMatchScoreExpected(PLAYER_TWO, 0, 5, Point.ZERO);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 5-5 в сете, когда первый теннисист выигрывает гейм, то счет становится 6-5 в сете")
    public void given5_5inSet_whenPlayerOneWinsGame_then6_5inSet() {
        fillMatchScore(PLAYER_ONE, 0, 5, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 0, 5, Point.ZERO);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 6, Point.ZERO);
        fillMatchScoreExpected(PLAYER_TWO, 0, 5, Point.ZERO);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 4-5 в сете, когда второй теннисист выигрывает гейм, " +
            "то счет становится 0-0 в сете и 0-1 в матче")
    public void given4_5inSet_whenPlayerTwoWinsGame_then0_0inSetAnd0_1inMatch() {
        fillMatchScore(PLAYER_ONE, 0, 4, Point.ZERO);
        fillMatchScore(PLAYER_TWO, 0, 5, Point.FORTY);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_TWO_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 0, Point.ZERO);
        fillMatchScoreExpected(PLAYER_TWO, 1, 0, Point.ZERO);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 6-5 в сете и 0-1 в матче, когда первый теннисист выигрывает гейм, " +
            "то счет становится 0-0 в сете и 1-1 в матче")
    public void given6_5inSetAnd0_1inMatch_whenPlayerOneWinsGame_then_0_0inSetAnd1_1inMatch() {
        fillMatchScore(PLAYER_ONE, 0, 6, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 1, 5, Point.ZERO);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 1, 0, Point.ZERO);
        fillMatchScoreExpected(PLAYER_TWO, 1, 0, Point.ZERO);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 5-3 в сете и первому теннисисту не хватает одного сета для победы, когда первый " +
            "теннисист выигрывает гейм, то счет становится 6-3 и матч заканчивается")
    public void given5_3inSetAndPlayerOneIsOneSetShortOfVictory_whenPlayerOneWinsGame_then_6_3AndMatchIsFinished() {
        fillMatchScore(PLAYER_ONE, NUMBER_OF_SETS_TO_WIN_MATCH - 1, 5, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 0, 3, Point.ZERO);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, NUMBER_OF_SETS_TO_WIN_MATCH, 6, Point.FORTY);
        fillMatchScoreExpected(PLAYER_TWO, 0, 3, Point.ZERO);
        matchScoreExpected.getGame().isFinished = true;
        matchScoreExpected.getSet().isFinished = true;
        matchScoreExpected.isFinished = true;

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 5-6 в сете, когда первый теннисист выигрывает гейм, " +
            "то счет становится 6-6 в сете и у сета появляется тайбрейк")
    public void given5_6inSet_whenPlayerOneWinsGame_then_6_6inSetAndSetHasTiebreak() {
        fillMatchScore(PLAYER_ONE, 0, 5, Point.FORTY);
        fillMatchScore(PLAYER_TWO, 0, 6, Point.ZERO);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 0, 6, Point.FORTY);
        fillMatchScoreExpected(PLAYER_TWO, 0, 6, Point.ZERO);
        matchScoreExpected.getGame().isFinished = true;
        matchScoreExpected.getSet().hasTiebreak = true;
        matchScoreExpected.getSet().tiebreak = new TiebreakScore();

        assertNotNull(result.getSet().tiebreak, "The set should have tiebreak");
        assertTiebreakScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 1-2 на тайбрейке, когда первый теннисист выигрывает очко, " +
            "то счет становится 2-2 на тайбрейке")
    public void given1_2inTiebreak_whenPlayerOneWinsPoint_then_2_2inTiebreak() {
        fillTiebreakScore(PLAYER_ONE, 0, 6, 1);
        fillTiebreakScore(PLAYER_TWO, 0, 6, 2);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillTiebreakScoreExpected(PLAYER_ONE, 0, 6, 2);
        fillTiebreakScoreExpected(PLAYER_TWO, 0, 6, 2);

        assertTiebreakScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("При счете 6-6 на тайбрейке, когда первый теннисист выигрывает очко, " +
            "то счет становится 7-6 на тайбрейке и он не заканчивается")
    public void given6_6inTiebreak_whenPlayerOneWinsPoint_then_7_6AndTiebreakIsNotFinished() {
        fillTiebreakScore(PLAYER_ONE, 0, 6, 6);
        fillTiebreakScore(PLAYER_TWO, 0, 6, 6);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillTiebreakScoreExpected(PLAYER_ONE, 0, 6, 7);
        fillTiebreakScoreExpected(PLAYER_TWO, 0, 6, 6);

        assertTiebreakScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("Первом игроку не хватает одного очка для победы в тайбрейке и разница очков меньше единицы, когда " +
            "первый теннисист выигрывает очко, то тайбрейк не заканчивается")
    public void givenPlayerOneLacksOnePointToWinTiebreakAndPointDifferenceIsLessThanTwo_whenPlayerOneWinsPoint_then_TiebreakIsNotFinished() {
        fillTiebreakScore(PLAYER_ONE, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK - 1);
        fillTiebreakScore(PLAYER_TWO, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK - 1);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillTiebreakScoreExpected(PLAYER_ONE, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK);
        fillTiebreakScoreExpected(PLAYER_TWO, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK - 1);

        assertTiebreakScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("Тайбрейк не должен заканчивается когда количество очков больше необходимого, но разница очков " +
            "меньше двух")
    public void tiebreakShouldNotFinishWhenNumberOfPointsIsMoreThanRequiredButDifferenceLessThanTwo() {
        fillTiebreakScore(PLAYER_ONE, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK + 2);
        fillTiebreakScore(PLAYER_TWO, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK +2);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillTiebreakScoreExpected(PLAYER_ONE, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK + 3);
        fillTiebreakScoreExpected(PLAYER_TWO, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK + 2);

        assertTiebreakScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("Когда первый игрок выигрывает тайбрейк, то он выигрывает сет")
    public void whenPlayerOneWinsTiebreak_thenHeWinsSet() {
        fillTiebreakScore(PLAYER_ONE, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK - 1);
        fillTiebreakScore(PLAYER_TWO, 0, 6, 0);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 1, 0, Point.ZERO);
        fillMatchScoreExpected(PLAYER_TWO, 0, 0, Point.ZERO);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("Если у обоих игроков очков больше необходимых для победы,когда первый игрок выигрывает тайбрейк, " +
            "то он выигрывает сет")
    public void givenTwoPlayersHasRequiredPointsToWinAndDifferenceIsOneOrLess_whenPlayerOneWinsPoint_thenHeWinsSet() {
        fillTiebreakScore(PLAYER_ONE, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK + 2);
        fillTiebreakScore(PLAYER_TWO, 0, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK + 1);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillMatchScoreExpected(PLAYER_ONE, 1, 0, Point.ZERO);
        fillMatchScoreExpected(PLAYER_TWO, 0, 0, Point.ZERO);

        assertMatchScoreEquals(matchScoreExpected, result);
    }

    @Test
    @DisplayName("Первом игроку не хватает одного сета для победы в матче, когда первый теннисист выигрывает " +
            "тайбрейк, то он выигрывает сет и матч заканчивается")
    public void givenPlayerOneLacksOneSerToWinMatch_whenPlayerOneWinsTiebreak_thenHeWinsSetAndMatchIsFinished() {
        fillTiebreakScore(PLAYER_ONE, NUMBER_OF_SETS_TO_WIN_MATCH - 1, 6, NUMBER_OF_POINTS_TO_WIN_TIEBREAK - 1);
        fillTiebreakScore(PLAYER_TWO, 0, 6, 0);

        MatchScore result = matchScoreCalculationService.addPointToPlayer(matchScore, PLAYER_ONE_STRING);

        fillTiebreakScoreExpected(PLAYER_ONE, NUMBER_OF_SETS_TO_WIN_MATCH, 7, NUMBER_OF_POINTS_TO_WIN_TIEBREAK);
        fillTiebreakScoreExpected(PLAYER_TWO, 0, 6, 0);
        matchScoreExpected.getGame().isFinished = true;
        matchScoreExpected.getSet().tiebreak.isFinished = true;
        matchScoreExpected.getSet().isFinished = true;
        matchScoreExpected.isFinished = true;

        assertTiebreakScoreEquals(matchScoreExpected, result);
    }

    private void fillScore(MatchScore matchScore, PlayerOrdinal playerOrdinal, int set, int game, Point point) {
        matchScore.setScore(playerOrdinal, set);
        matchScore.getSet().setScore(playerOrdinal, game);
        matchScore.getGame().setScore(playerOrdinal, point);
    }

    private void fillMatchScore(PlayerOrdinal playerOrdinal, int set, int game, Point point) {
        fillScore(matchScore, playerOrdinal, set, game, point);
    }

    private void fillMatchScoreExpected(PlayerOrdinal playerOrdinal, int set, int game, Point point) {
        fillScore(matchScoreExpected, playerOrdinal, set, game, point);
    }

    private void fillTiebreakScore(
            MatchScore matchScore, PlayerOrdinal playerOrdinal, int set, int game, int tiebreakPoint) {
        fillScore(matchScore, playerOrdinal, set, game, Point.ZERO);
        matchScore.getGame().isFinished = true;
        if (!matchScore.getSet().hasTiebreak) {
            matchScore.getSet().tiebreak = new TiebreakScore();
            matchScore.getSet().hasTiebreak = true;
        }
        matchScore.getSet().tiebreak.setScore(playerOrdinal, tiebreakPoint);
    }

    private void fillTiebreakScore(PlayerOrdinal playerOrdinal, int set, int game, int tiebreakPoint) {
        fillTiebreakScore(matchScore, playerOrdinal, set, game, tiebreakPoint);
    }

    private void fillTiebreakScoreExpected(PlayerOrdinal playerOrdinal, int set, int game, int tiebreakPoint) {
        fillTiebreakScore(matchScoreExpected, playerOrdinal, set, game, tiebreakPoint);
    }

    private static void assertMatchScoreEquals(MatchScore expected, MatchScore actual) {
        assertAll("Check that the actual match score is equal to the expected match score in sets, games and points.",
                () -> assertEquals(expected.getScore(PLAYER_ONE),
                        actual.getScore(PLAYER_ONE),
                        "Player One set score is not equal to the expected."),
                () -> assertEquals(expected.getSet().getScore(PLAYER_ONE),
                        actual.getSet().getScore(PLAYER_ONE),
                        "Player One game score is not equal to the expected."),
                () -> assertEquals(expected.getGame().getScore(PLAYER_ONE),
                        actual.getGame().getScore(PLAYER_ONE),
                        "Player One point score is not equal to the expected."),
                () -> assertEquals(expected.getScore(PLAYER_TWO),
                        actual.getScore(PLAYER_TWO),
                        "Player Two set score is not equal to the expected."),
                () -> assertEquals(expected.getSet().getScore(PLAYER_TWO),
                        actual.getSet().getScore(PLAYER_TWO),
                        "Player Two game score is not equal to the expected."),
                () -> assertEquals(expected.getGame().getScore(PLAYER_TWO),
                        actual.getGame().getScore(PLAYER_TWO),
                        "Player Two point score is not equal to the expected."),

                () -> assertEquals(expected.isFinished,
                        actual.isFinished,
                        "Match finished status is not equal to the expected"),
                () -> assertEquals(expected.getSet().isFinished,
                        actual.getSet().isFinished,
                        "Set finished status is not equal to the expected"),
                () -> assertEquals(expected.getSet().hasTiebreak,
                        actual.getSet().hasTiebreak,
                        "Set tiebreak status is not equal to the expected"),
                () -> assertEquals(expected.getGame().isFinished,
                        actual.getGame().isFinished,
                        "Game finished status is not equal to the expected")
        );
    }

    private static void assertTiebreakScoreEquals(MatchScore expected, MatchScore actual) {
        assertMatchScoreEquals(expected, actual);
        assertAll("Check that the actual match score is equal to the expected match score in tiebreak",
                () -> assertEquals(expected.getSet().tiebreak.isFinished,
                        actual.getSet().tiebreak.isFinished,
                        "Tiebreak finished status is not equal to the expected"),
                () -> assertEquals(expected.getSet().tiebreak.getScore(PLAYER_ONE),
                        actual.getSet().tiebreak.getScore(PLAYER_ONE),
                        "Player One tiebreak score is not equal to the expected"),
                () -> assertEquals(expected.getSet().tiebreak.getScore(PLAYER_TWO),
                        actual.getSet().tiebreak.getScore(PLAYER_TWO),
                        "Player Two tiebreak score is not equal to the expected")
        );
    }

    private void resetMatchScore(MatchScore matchScore) {
        while (matchScore.sets.size() > 1) {
            matchScore.sets.removeFirst();
        }
        while (matchScore.getSet().games.size() > 1) {
            matchScore.getSet().games.removeFirst();
        }
        fillMatchScore(PLAYER_ONE, 0,0, Point.ZERO);
        fillMatchScore(PLAYER_TWO, 0,0, Point.ZERO);
        matchScore.isFinished = false;
        matchScore.getSet().hasTiebreak = false;
        matchScore.getSet().tiebreak = null;
        matchScore.getSet().isFinished = false;
        matchScore.getGame().isFinished = false;
    }
}
