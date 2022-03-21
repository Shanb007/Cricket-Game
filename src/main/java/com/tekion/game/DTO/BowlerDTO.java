package com.tekion.game.DTO;

public class BowlerDTO {
    private int playerID;
    private int matchID;
    private int runsGiven;
    private double oversBowled;
    private String DidBall;
    private int noBallsBowled;
    private int wideBallsBowled ;
    private int wicketsTaken;

    public void setOversBowled(double oversBowled) {
        this.oversBowled = oversBowled;
    }

    public void setDidBall(String didBall) {
        DidBall = didBall;
    }

    public void setWideBallsBowled(int wideBallsBowled) {
        this.wideBallsBowled = wideBallsBowled;
    }

    public String getDidBall() {
        return DidBall;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public void setRunsGiven(int runsGiven) {
        this.runsGiven = runsGiven;
    }

    public void setNoBallsBowled(int noBallsBowled) {
        this.noBallsBowled = noBallsBowled;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public int getWideBallsBowled() {
        return wideBallsBowled;
    }

    public int getRunsGiven() {
        return runsGiven;
    }

    public double getOversBowled() {
        return oversBowled;
    }

    public int getWicketsTaken() {
        return wicketsTaken;
    }

    public int getNoBallsBowled() {
        return noBallsBowled;
    }

    public int getMatchID() {
        return matchID;
    }

    public int getPlayerID() {
        return playerID;
    }
}

