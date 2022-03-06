package com.tekion.game.bean;

public class Matches {
    private int MatchID;
    private int TeamA_ID;
    private int TeamB_ID;
    private double TotalOvers;
    private String tossWinner;
    private String tossWinnerChoice;
    private int TeamA_Score;
    private int TeamA_WicketsFallen;
    private int TeamB_Score;
    private int TeamB_WicketsFallen;
    private String Match_Winner;

    public void setMatchID(int matchID) {
        MatchID = matchID;
    }

    public double getTotalOvers() {
        return TotalOvers;
    }

    public void setTotalOvers(double totalOvers) {
        TotalOvers = totalOvers;
    }

    public void setMatch_Winner(String match_Winner) {
        Match_Winner = match_Winner;
    }

    public void setTeamA_ID(int teamA_ID) {
        TeamA_ID = teamA_ID;
    }

    public void setTeamA_Score(int teamA_Score) {
        TeamA_Score = teamA_Score;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public String getTossWinnerChoice() {
        return tossWinnerChoice;
    }

    public String getMatch_Winner() {
        return Match_Winner;
    }

    public int getMatchID() {
        return MatchID;
    }

    public void setTeamA_WicketsFallen(int teamA_WicketsFallen) {
        TeamA_WicketsFallen = teamA_WicketsFallen;
    }

    public void setTeamB_ID(int teamB_ID) {
        TeamB_ID = teamB_ID;
    }

    public void setTeamB_Score(int teamB_Score) {
        TeamB_Score = teamB_Score;
    }

    public void setTeamB_WicketsFallen(int teamB_WicketsFallen) {
        TeamB_WicketsFallen = teamB_WicketsFallen;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public void setTossWinnerChoice(String tossWinnerChoice) {
        this.tossWinnerChoice = tossWinnerChoice;
    }

    public int getTeamA_Score() {
        return TeamA_Score;
    }

    public int getTeamB_Score() {
        return TeamB_Score;
    }

    public int getTeamA_ID() {
        return TeamA_ID;
    }

    public int getTeamA_WicketsFallen() {
        return TeamA_WicketsFallen;
    }

    public int getTeamB_ID() {
        return TeamB_ID;
    }

    public int getTeamB_WicketsFallen() {
        return TeamB_WicketsFallen;
    }
}
