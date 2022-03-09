package com.tekion.game.models;

public class Team {
    private int teamID;
    private String teamName;
    private int currentOver=0;
    private int teamScore=0;
    private int wicketsFallen=0;
    private int NoBall=0;
    private int WideBall=0;
    private int extraScore = 0;


    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamName(String T){
        this.teamName = T;
    }

    public String getTeam(){
        return teamName;
    }

    public int TeamScore() {
        return teamScore;
    }

    public void TeamScoreCalculator(int currentBallScore) {
        teamScore += currentBallScore;
    }

    public void wicketGoneTracker() {
        wicketsFallen++;
    }

    public int totalWicketsGone() {
        return wicketsFallen;
    }

    public int TotalNoBalls(){
        return NoBall;
    }
    public void NoBallTracker(){
        NoBall++;
    }

    public int TotalWideBalls(){
        return WideBall;
    }

    public void WideBallTracker(){
        WideBall++;
    }
    public int currentOver(){
        return currentOver;
    }

    public void currentOverCalculator(){
        currentOver++;
    }

    public int TotalExtras(){
        return NoBall + WideBall;
    }

    public int getExtraScore(){
        return extraScore;
    }

    public void ExtraScoreTracker(int score){
        extraScore+=score;
    }
}

