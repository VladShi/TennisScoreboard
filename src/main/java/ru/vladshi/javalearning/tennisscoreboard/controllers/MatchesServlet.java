package ru.vladshi.javalearning.tennisscoreboard.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vladshi.javalearning.tennisscoreboard.services.MatchesService;
import ru.vladshi.javalearning.tennisscoreboard.services.MatchesServiceImpl;

import java.io.IOException;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private final MatchesService matchesService = MatchesServiceImpl.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("matches", matchesService.getAllMatches().stream().limit(5).toList());
        req.getRequestDispatcher("view/matches.jsp").forward(req, resp);
    }
}
