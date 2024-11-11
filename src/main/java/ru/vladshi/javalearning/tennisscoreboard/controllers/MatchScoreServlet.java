package ru.vladshi.javalearning.tennisscoreboard.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vladshi.javalearning.tennisscoreboard.Entities.MatchScore;
import ru.vladshi.javalearning.tennisscoreboard.services.OngoingMatchesService;
import ru.vladshi.javalearning.tennisscoreboard.services.OngoingMatchesServiceImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesServiceImpl.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        Optional<MatchScore> matchScore = ongoingMatchesService.getMatchScore(uuid);
        req.getSession().setAttribute("matchScore", matchScore);
        req.getRequestDispatcher("view/match-score.jsp").forward(req, resp);
    }
}
