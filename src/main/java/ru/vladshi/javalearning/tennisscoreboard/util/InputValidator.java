package ru.vladshi.javalearning.tennisscoreboard.util;

import jakarta.servlet.http.HttpServletRequest;

public class InputValidator {

    public static String getValidFilterByPlayerName(HttpServletRequest req) {
        String playerName = req.getParameter("filter_by_player_name");
        return playerName == null ? "" : playerName.strip();
    }

    public static int getValidPageNumber(HttpServletRequest req, int lastPageNumber) {
        String pageNumberStr = req.getParameter("page");
        int pageNumber;
        if (pageNumberStr == null || pageNumberStr.isBlank() || pageNumberStr.length() > 12 || !pageNumberStr.matches("\\d+")) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.parseInt(pageNumberStr);
        }
        if (pageNumber > lastPageNumber) {
            pageNumber = lastPageNumber;
        }
        if (pageNumber == 0 ) {
            pageNumber = 1;
        }
        return pageNumber;
    }
}
