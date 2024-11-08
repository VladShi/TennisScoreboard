package ru.vladshi.javalearning.tennisscoreboard.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vladshi.javalearning.tennisscoreboard.services.MatchesService;
import ru.vladshi.javalearning.tennisscoreboard.services.MatchesServiceImpl;
import ru.vladshi.javalearning.tennisscoreboard.util.InputValidator;

import java.io.IOException;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private final MatchesService matchesService = MatchesServiceImpl.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int PAGE_SIZE = 4;
        final int PAGINATION_LINKS_MAX_NUMBER = 3;

        String filterByPlayerName = InputValidator.getValidFilterByPlayerName(req);

        int lastPageNumber = matchesService.getLastPageNumberByPlayerName(filterByPlayerName, PAGE_SIZE);

        int pageNumber = InputValidator.getValidPageNumber(req, lastPageNumber);

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("matches",
                matchesService.getListOfMatches(filterByPlayerName, pageNumber, PAGE_SIZE));
        httpSession.setAttribute("lastPageNumber", lastPageNumber);
        httpSession.setAttribute("pageNumberRange",
                matchesService.getPageNumberRange(pageNumber, lastPageNumber, PAGINATION_LINKS_MAX_NUMBER));
        httpSession.setAttribute("playerName", filterByPlayerName);
        httpSession.setAttribute("pageNumber", pageNumber);

        req.getRequestDispatcher("view/matches.jsp").forward(req, resp);
    }
}
