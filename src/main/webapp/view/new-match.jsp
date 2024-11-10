<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 04.11.2024
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String errorMessage = (String) session.getAttribute("errorMessage");
    String playerOneName = (String) session.getAttribute("playerOneName");
    String playerTwoName = (String) session.getAttribute("playerTwoName");
    session.invalidate();
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | New Match</title>
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
                <a class="nav-link" href="/">Home</a>
                <a class="nav-link" href="matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <div>
            <h1>Start new match</h1>
            <div class="new-match-image"></div>
            <div class="form-container center">
                <form method="post" action="">
                    <% if  (errorMessage != null && !errorMessage.isEmpty()) { %>
                        <p style="color: red;"> <%= errorMessage %> </p>
                    <% } else { %>
                        <p> &nbsp </p>
                    <% } %>
                    <label class="label-player" for="playerOne">Player one</label>
                    <input class="input-player" placeholder="<%= playerOneName == null ? "Name" : "" %>" type="text"
                           id="playerOne" value="<%= playerOneName != null ? playerOneName : "" %>"
                           name="playerOne" pattern="[a-zA-Z\s.'-]+" required
                           title="This field only accepts English letters, space, and the following characters: ' . -">
                    <label class="label-player" for="playerTwo">Player two</label>
                    <input class="input-player" placeholder="<%= playerTwoName == null ? "Name" : "" %>" type="text"
                           id="playerTwo" value="<%= playerTwoName != null ? playerTwoName : "" %>"
                           name="playerTwo" pattern="[a-zA-Z\s.'-]+" required
                           title="This field only accepts English letters, space, and the following characters: ' . -">
                    <input class="form-button" type="submit" value="Start">
                </form>
            </div>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
    </div>
</footer>
</body>
</html>