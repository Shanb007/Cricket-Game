package com.tekion.game.models;

public class Player {
private String PlayerName;
//String PlayerCategory;
private int runsScored;
private int BallsPlayed;
private int wicketsTaken;
private int numberOf6s;
private int numberOf4s;
private int maidenOver;
private double oversBowled;
private double ballsBowled;
private int noBallsBowled;
private int wideBallsBowled;
private int runsGiven;

public Player(String name){
    this.PlayerName = name;
    this.runsScored = 0;
    this.BallsPlayed = 0;
    this.numberOf4s = 0;
    this.numberOf6s = 0;
    this.wicketsTaken = 0;
    this.maidenOver = 0;
    this.oversBowled = 0.0;
    this.ballsBowled = 0.0;
    this.noBallsBowled = 0;
    this.wideBallsBowled= 0;
    this.runsGiven = 0;
}
   /*

    void setPlayerCategory(String cat){
        PlayerCategory = cat;
    }
    String getPlayerCategory(){
        return PlayerCategory;
    }

    */
String getName(){
    return PlayerName;
}

int getRunsScored(){
    return runsScored;
}

void runTracker(int score){
    runsScored += score;
}

int TotalBallsPlayed(){
    return BallsPlayed;
}

void BallsPlayedTracker(){
    BallsPlayed ++;
}

int getNumberOf6s(){
    return numberOf6s;
}

void trackNoOf6s(){
    numberOf6s++;
}

int getNumberOf4s(){
    return numberOf4s;
}

void trackNoOf4s(){
    numberOf4s++;
}

int getWicketsTaken(){
    return wicketsTaken;
}

void wicketTracker(){
    wicketsTaken++;
}

int getMaidenOver(){
    return maidenOver;
}

void MaidenOverTracker(){
    maidenOver++;
}

int getNoBallsBowled(){
    return noBallsBowled;
}

void NoBallsBowledTracker(){
    noBallsBowled++;
}

int getWideBallsBowled(){
    return wideBallsBowled;
}

void WideBallsBowledTracker(){
    wideBallsBowled++;
}

double getOversBowled(){
    return oversBowled;
}

void OversBowledTracker(){
    ballsBowled++;
    if (ballsBowled%6 == 0.0){
        oversBowled = ballsBowled/6;
    }
    else{
        oversBowled += (ballsBowled%6)/10;
    }
}

int getRunsGiven(){
    return runsGiven;
}

void runsGivenTracker(int runs){
    runsGiven += runs;
}

}
