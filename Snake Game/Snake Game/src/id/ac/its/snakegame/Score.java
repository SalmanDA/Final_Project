package id.ac.its.snakegame;

import java.io.Serializable;

public class Score implements Serializable {
    private String name;
    private int score;

    public Score() {
        name = "player";
        score = 0;
    }

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

}