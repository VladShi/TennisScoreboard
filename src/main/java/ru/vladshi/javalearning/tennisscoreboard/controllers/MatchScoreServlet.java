package ru.vladshi.javalearning.tennisscoreboard.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.MatchScore;
import ru.vladshi.javalearning.tennisscoreboard.services.MatchScoreCalculationService;
import ru.vladshi.javalearning.tennisscoreboard.services.MatchScoreCalculationServiceImpl;
import ru.vladshi.javalearning.tennisscoreboard.services.OngoingMatchesService;
import ru.vladshi.javalearning.tennisscoreboard.services.OngoingMatchesServiceImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesServiceImpl.INSTANCE;
    private final MatchScoreCalculationService matchScoreCalculationService = MatchScoreCalculationServiceImpl.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        Optional<MatchScore> matchScoreOptional = ongoingMatchesService.getMatchScore(uuid);
        req.getSession().setAttribute("matchScore", matchScoreOptional);
        req.getRequestDispatcher("view/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchUuid = req.getParameter("uuid");
        String playerOrdinal = req.getParameter("playerOrdinal");
        Optional<MatchScore> matchScoreOptional = ongoingMatchesService.getMatchScore(matchUuid);
        if (matchScoreOptional.isPresent()) {
            MatchScore matchScore =
                    matchScoreCalculationService.addPointToPlayer(matchScoreOptional.get(), playerOrdinal);
            if (matchScore.isFinished) {
                // TODO удалить матч из коллекции и записать его в бд через FinishedMatchesPersistenceService
            }
        }
        req.getSession().setAttribute("matchScore", matchScoreOptional);
        req.getSession().setAttribute("uuid", matchUuid);
        req.getRequestDispatcher("view/match-score.jsp").forward(req, resp); // TODO сделать отображение законченного матча.
    }
}
