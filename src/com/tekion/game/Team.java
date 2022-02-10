package com.tekion.game;

public class Team {
    String teamName;
    int currentOver=0;
    int teamScore=0;
    int wicketsFallen=0;

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
void wicketGone() {
    wicketsFallen++;
}

  int totalWicketsGone() {
        return wicketsFallen;

    }
}

