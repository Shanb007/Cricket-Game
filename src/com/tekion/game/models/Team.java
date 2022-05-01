package com.tekion.game.models;

public class Team {
    private String teamName;
    private int currentOver=0;
    private int teamScore=0;
    private int wicketsFallen=0;
    private int NoBall=0;
    private int WideBall=0;
    private int extraScore = 0;

    void setTeamName(String T){
        this.teamName = T;
    }

    public String getTeam(){
        return teamName;
    }

    public int TeamScore() {
        return teamScore;
    }

    void TeamScoreCalculator(int currentBallScore) {
        teamScore += currentBallScore;
    }

    void wicketGoneTracker() {
        wicketsFallen++;
    }

    public int totalWicketsGone() {
        return wicketsFallen;
    }

    public int TotalNoBalls(){
        return NoBall;
    }
    void NoBallTracker(){
        NoBall++;
    }

    public int TotalWideBalls(){
        return WideBall;
    }

    void WideBallTracker(){
        WideBall++;
    }
    int currentOver(){
        return currentOver;
    }

    void currentOverCalculator(){
        currentOver++;
    }

    public int TotalExtras(){
        return NoBall + WideBall;
    }

    public int getExtraScore(){
        return extraScore;
    }

    void ExtraScoreTracker(int score){
        extraScore+=score;
    }
}

