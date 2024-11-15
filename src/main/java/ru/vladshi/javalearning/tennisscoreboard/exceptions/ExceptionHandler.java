package ru.vladshi.javalearning.tennisscoreboard.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExceptionHandler {

    private ExceptionHandler() {}

    public static void handleException(HttpServletRequest req, HttpServletResponse resp, Throwable throwable)
            throws IOException, ServletException {
        if (throwable instanceof DatabaseException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/view/500.jsp").forward(req, resp);
        } else {
            throw new RuntimeException("Unhandled exception", throwable);
        }
    }
}
