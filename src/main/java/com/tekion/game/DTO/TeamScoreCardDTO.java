package com.tekion.game.DTO;

public class TeamScoreCardDTO {
    private int matchID;
    private int teamID;
    private int TeamScore;
    private int TotalNoBalls;
    private int TotalWideBalls;
    private int ExtraRuns;
    private int currentOver;
    private int totalWicketsGone;

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public void setTeamScore(int teamScore) {
        TeamScore = teamScore;
    }

    public void setCurrentOver(int currentOver) {
        this.currentOver = currentOver;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public void setExtraRuns(int extraRuns) {
        ExtraRuns = extraRuns;
    }

    public int getTeamScore() {
        return TeamScore;
    }

    public void setTotalNoBalls(int totalNoBalls) {
        TotalNoBalls = totalNoBalls;
    }

    public void setTotalWicketsGone(int totalWicketsGone) {
        this.totalWicketsGone = totalWicketsGone;
    }

    public void setTotalWideBalls(int totalWideBalls) {
        TotalWideBalls = totalWideBalls;
    }

    public int getMatchID() {
        return matchID;
    }

    public int getTeamID() {
        return teamID;
    }

    public int getCurrentOver() {
        return currentOver;
    }

    public int getTotalNoBalls() {
        return TotalNoBalls;
    }

    public int getExtraRuns() {
        return ExtraRuns;
    }

    public int getTotalWicketsGone() {
        return totalWicketsGone;
    }

    public int getTotalWideBalls() {
        return TotalWideBalls;
    }
}
