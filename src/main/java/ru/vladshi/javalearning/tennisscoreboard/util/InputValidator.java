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
        if (pageNumberStr == null || pageNumberStr.isBlank()
                || pageNumberStr.length() > 12 || !isPositiveNumber(pageNumberStr)) {
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

    public static String validatePlayers(String playerOne, String playerTwo) {
        String errorMessage = checkPlayerNameAndGetErrorMessage(playerOne);
        if (!errorMessage.isEmpty()) {
            return "Player one name is invalid. " + errorMessage;
        }
        errorMessage = checkPlayerNameAndGetErrorMessage(playerTwo);
        if (!errorMessage.isEmpty()) {
            return "Player two name is invalid. " + errorMessage;
        }
        playerOne = playerOne.strip();
        playerTwo = playerTwo.strip();
        if (playerOne.equalsIgnoreCase(playerTwo)) {
            return "Player cannot play against himself";
        }
        return errorMessage;
    }

    private static String checkPlayerNameAndGetErrorMessage(String name) {
        if (name == null || name.isBlank())  {
            return "The name must not be empty";
        }
        name = name.strip();
        if (name.length() < 3 || name.length() > 35) {
            return "The name must be from 3 to 35 characters long";
        }
        if (!name.matches("[a-zA-Z\\s.'-]+")) {
            return "Only English letters, space and \". ' -\" characters are allowed";
        }
        return "";
    }

    private static boolean isPositiveNumber(String string) {
        return string.matches("\\d+");
    }
}