package ru.vladshi.javalearning.tennisscoreboard.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vladshi.javalearning.tennisscoreboard.services.OngoingMatchesService;
import ru.vladshi.javalearning.tennisscoreboard.services.OngoingMatchesServiceImpl;
import ru.vladshi.javalearning.tennisscoreboard.util.InputValidator;
import ru.vladshi.javalearning.tennisscoreboard.util.StringUtil;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesServiceImpl.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/view/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String playerOneName = req.getParameter("playerOne");
        String playerTwoName = req.getParameter("playerTwo");
        playerOneName = StringUtil.removeRedundantSpaces(playerOneName);
        playerTwoName = StringUtil.removeRedundantSpaces(playerTwoName);

        String errorMessage = InputValidator.validatePlayers(playerOneName, playerTwoName);
        if (!errorMessage.isEmpty()) {
            HttpSession session = req.getSession();
            session.setAttribute("errorMessage", errorMessage);
            session.setAttribute("playerOneName", playerOneName);
            session.setAttribute("playerTwoName", playerTwoName);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.getRequestDispatcher("/view/new-match.jsp").forward(req, resp);
        } else {
            playerOneName = StringUtil.capitalize(playerOneName);
            playerTwoName = StringUtil.capitalize(playerTwoName);

            UUID uuid = ongoingMatchesService.addNewMatchScore(playerOneName, playerTwoName);
            resp.sendRedirect(String.format("%s/match-score?uuid=%s", req.getContextPath(), uuid.toString()));
        }
    }
}
