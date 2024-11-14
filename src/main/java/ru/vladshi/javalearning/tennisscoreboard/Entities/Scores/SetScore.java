package ru.vladshi.javalearning.tennisscoreboard.Entities.Scores;

import java.util.LinkedList;
import java.util.List;

public class SetScore extends ScoreHolder {

    public List<GameScore> games;

    public boolean hasTiebreak = false;
    public TiebreakScore tiebreak;

    public SetScore() {
        super();
        this.games = new LinkedList<>();
        this.games.add(new GameScore());
    }
}
