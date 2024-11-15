<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 04.11.2024
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.vladshi.javalearning.tennisscoreboard.Entities.Match" %>
<%@ page import="java.util.List" %>

<%
    String playerName = (String) session.getAttribute("playerName");
    List<Match> matches = (List<Match>) session.getAttribute("matches");
    int pageNumber = (int) session.getAttribute("pageNumber");
    int lastPageNumber = (int) session.getAttribute("lastPageNumber");
    List<Integer> pageNumberRange = (List<Integer>) session.getAttribute("pageNumberRange");
    session.invalidate();
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
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
        <h1>Matches</h1>
        <div class="input-container">
            <form style="width: 100%" action="?" method="GET">
                <input class="input-filter" placeholder="<%= playerName.isBlank() ? "Filter by name": "" %>" type="text" name="filter_by_player_name" value="<%= !playerName.isBlank() ? playerName : "" %>"/>
                <input type="submit" hidden />
            </form>
            <div>
                <a href="matches">
                    <button class="btn-filter">Reset Filter</button>
                </a>
            </div>
        </div>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <%
                for (Match match : matches) {
            %>
            <tr>
                <td><%= match.getPlayerOne().getName() %></td>
                <td><%= match.getPlayerTwo().getName() %></td>
                <td><span class="winner-name-td"><%= match.getWinner().getName() %></span></td>
            </tr>
            <%
                }
            %>
        </table>

        <div class="pagination">
            <a class="prev" href="?page=1&filter_by_player_name=<%= playerName.isBlank() ? "" : playerName %>"> < </a>
            <%
                for (int number : pageNumberRange) {
                    if (number == pageNumber) {
            %>
            <span class="num-page current"><%= number %></span>
            <%
                    } else {
            %>
            <a class="num-page" href="?page=<%= number %>&filter_by_player_name=<%= playerName.isBlank() ? "" : playerName %>"><%= number %></a>
            <%
                    }
                }
            %>
            <a class="next" href="?page=<%= lastPageNumber %>&filter_by_player_name=<%= playerName.isBlank() ? "" : playerName %>"> > </a>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>