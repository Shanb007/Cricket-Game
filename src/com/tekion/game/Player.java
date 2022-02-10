package com.tekion.game;

public class Player {
String PlayerName;
String PlayerCategory;
int runsScored=0;
int BallsPlayed=0;
int wicketsTaken=0;
int numberOf6s =0;
int numberOf4s = 0;
int maidenOver = 0;


void setPlayerName(String name){
    PlayerName = name;
}

void setPlayerCategory(String cat){
    PlayerCategory = cat;
}
String getPlayerCategory(){
    return PlayerCategory;
}
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


}
