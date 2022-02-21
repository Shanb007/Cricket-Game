package com.tekion.game.models;

public class Team {
    String teamName;
    int currentOver=0;
    int teamScore=0;
    int wicketsFallen=0;
    int NoBall=0;
    int WideBall=0;
    int extraScore = 0;

    void setTeamName(String T){
        this.teamName = T;
    }

    String getTeam(){
        return teamName;
    }


int TeamScore() {
    return teamScore;
}
void TeamScoreCalculator(int currentBallScore) {
        teamScore += currentBallScore;
    }
void wicketGoneTracker() {
    wicketsFallen++;
}

  int totalWicketsGone() {
        return wicketsFallen;
    }

    int TotalNoBalls(){
        return NoBall;
    }
    void NoBallTracker(){
        NoBall++;
    }

    int TotalWideBalls(){
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

    int TotalExtras(){
        return NoBall + WideBall;
    }

    int getExtraScore(){
        return extraScore;
    }

    void ExtraScoreTracker(int score){
        extraScore+=score;
    }
}

