package ru.vladshi.javalearning.tennisscoreboard.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;
import ru.vladshi.javalearning.tennisscoreboard.services.PlayerService;
import ru.vladshi.javalearning.tennisscoreboard.services.PlayerServiceImpl;
import ru.vladshi.javalearning.tennisscoreboard.util.InputValidator;
import ru.vladshi.javalearning.tennisscoreboard.util.StringUtil;

import java.io.IOException;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final PlayerService playerService = PlayerServiceImpl.INSTANCE;

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
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("errorMessage", errorMessage);
            httpSession.setAttribute("playerOneName", playerOneName);
            httpSession.setAttribute("playerTwoName", playerTwoName);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.getRequestDispatcher("/view/new-match.jsp").forward(req, resp);
        } else {
            playerOneName = StringUtil.capitalize(playerOneName);
            playerTwoName = StringUtil.capitalize(playerTwoName);

            Player playerOne = playerService.getOrSaveByName(playerOneName);
            Player playerTwo = playerService.getOrSaveByName(playerTwoName);
            // TODO создать матч с игроками, положить в коллекцию матчей с ключом uuid, перенаправить на match-score
            //  с get параметром uuid
        }
    }
}
