package com.tekion.game.DTO;

public class MatchDetailDTO {
    private int matchID;
    private int teamA_id;
    private int teamB_id;
    private int TotalOvers;
    private String tossWinner;
    private String tossWinnerChoice;
    private String matchWinner;

    public String getTossWinner() {
        return tossWinner;
    }

    public String getTossWinnerChoice() {
        return tossWinnerChoice;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public void setTossWinnerChoice(String tossWinnerChoice) {
        this.tossWinnerChoice = tossWinnerChoice;
    }

    public void setTotalOvers(int totalOvers) {
        TotalOvers = totalOvers;
    }

    public String getMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(String matchWinner) {
        this.matchWinner = matchWinner;
    }

    public void setTeamA_id(int teamA_id) {
        this.teamA_id = teamA_id;
    }

    public void setTeamB_id(int teamB_id) {
        this.teamB_id = teamB_id;
    }

    public int getMatchID() {
        return matchID;
    }

    public int getTeamA_id() {
        return teamA_id;
    }

    public int getTotalOvers() {
        return TotalOvers;
    }

    public int getTeamB_id() {
        return teamB_id;
    }
}
