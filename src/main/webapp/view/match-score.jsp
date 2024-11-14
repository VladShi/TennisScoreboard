<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 04.11.2024
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.MatchScore" %>
<%@ page import="java.util.Optional" %>
<%@ page import="static ru.vladshi.javalearning.tennisscoreboard.Entities.Scores.PlayerOrdinal.*" %>
<%
    Optional<MatchScore> matchScoreOptional = (Optional<MatchScore>) session.getAttribute("matchScore");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}view/css/style.css">

    <script src="${pageContext.request.contextPath}view/js/app.js"></script>
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <% if (matchScoreOptional.isPresent()) {
                MatchScore matchScore = matchScoreOptional.get();
                boolean hasSetTiebreak = matchScore.sets.getLast().hasTiebreak;
            %>
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text" <% if(hasSetTiebreak) { %>style="background-color: #e7e7e7; font-weight: bold"<% } %> > <%= hasSetTiebreak ? "Tiebreak" : "Points" %></th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text"><%= matchScore.playerOne.getName() %></td>
                    <td class="table-text"><%= matchScore.getScore(PLAYER_ONE) %></td>
                    <td class="table-text"><%= matchScore.sets.getLast().getScore(PLAYER_ONE) %></td>
                    <td class="table-text" <% if(hasSetTiebreak) { %>style="background-color: #e7e7e7; font-weight: bold"<% } %>><%= hasSetTiebreak ?
                            matchScore.sets.getLast().tiebreak.getScore(PLAYER_ONE)
                            : matchScore.sets.getLast().games.getLast().getScore(PLAYER_ONE).value %></td>
                    <td class="table-text">
                        <form method="post" action="">
                            <input hidden name="playerOrdinal" value="playerOne">
                            <input class="score-btn" style="width:90%;" type="submit" value="Score">
                        </form>
                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text"><%= matchScore.playerTwo.getName() %></td>
                    <td class="table-text"><%= matchScore.getScore(PLAYER_TWO) %></td>
                    <td class="table-text"><%= matchScore.sets.getLast().getScore(PLAYER_TWO) %></td>
                    <td class="table-text" <% if(hasSetTiebreak) { %>style="background-color: #e7e7e7; font-weight: bold"<% } %>><%= hasSetTiebreak ?
                            matchScore.sets.getLast().tiebreak.getScore(PLAYER_TWO)
                            : matchScore.sets.getLast().games.getLast().getScore(PLAYER_TWO).value %></td>
                    <td class="table-text">
                        <form method="post" action="">
                            <input hidden name="playerOrdinal" value="playerTwo">
                            <input class="score-btn" style="width:90%;" type="submit" value="Score">
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
            <% } else { %>
            <p style="color: red">Requested match was not found.</p>
            <% } %>
        </section>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
    </div>
</footer>
</body>
</html>
