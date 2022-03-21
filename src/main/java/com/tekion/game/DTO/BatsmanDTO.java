package com.tekion.game.DTO;

public class BatsmanDTO {
    private int playerID;
    private int matchID;
    private int runsScored;
    private int ballsPlayed;
    private int numberOf4s;
    private int numberOf6s;
    private String DidBat;
    private String wicketTakenBy;

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getDidBat() {
        return DidBat;
    }

    public String getWicketTakenBy() {
        return wicketTakenBy;
    }

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public void setNumberOf4s(int numberOf4s) {
        this.numberOf4s = numberOf4s;
    }

    public void setNumberOf6s(int numberOf6s) {
        this.numberOf6s = numberOf6s;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public void setWicketTakenBy(String wicketTakenBy) {
        this.wicketTakenBy = wicketTakenBy;
    }

    public void setDidBat(String didBat) {
        DidBat = didBat;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getNumberOf4s() {
        return numberOf4s;
    }

    public int getNumberOf6s() {
        return numberOf6s;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public int getMatchID() {
        return matchID;
    }
}
