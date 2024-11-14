package ru.vladshi.javalearning.tennisscoreboard.Entities.Scores;

import ru.vladshi.javalearning.tennisscoreboard.Entities.Player;

import java.util.LinkedList;
import java.util.List;

public class MatchScore extends ScoreHolder {

    public Player playerOne;
    public Player playerTwo;

    public List<SetScore> sets;

    public MatchScore(Player playerOne, Player playerTwo) {
        super();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.sets = new LinkedList<>();
        this.sets.add(new SetScore());
    }

    public SetScore getSet() {
        return sets.getLast();
    }

    public GameScore getGame() {
        return sets.getLast().games.getLast();
    }
}
