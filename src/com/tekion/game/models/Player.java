package com.tekion.game.models;

public class Player {
    private final String PlayerName;
    private int runsScored;
    private int BallsPlayed;
    private int wicketsTaken;
    private int numberOf6s;
    private int numberOf4s;
    private double ballsBowled;
    private int noBallsBowled;
    private int wideBallsBowled;
    private int runsGiven;
    private String wicketTakenBy;

    public Player(String name){
        this.PlayerName = name;
        this.runsScored = 0;
        this.BallsPlayed = 0;
        this.numberOf4s = 0;
        this.numberOf6s = 0;
        this.wicketsTaken = 0;
        this.ballsBowled = 0.0;
        this.noBallsBowled = 0;
        this.wideBallsBowled= 0;
        this.runsGiven = 0;
        this.wicketTakenBy = "NOT OUT";
    }

    public String getName(){
        return PlayerName;
    }

    public int getRunsScored(){
        return runsScored;
    }

    void runTracker(int score){
        runsScored += score;
    }

    public int TotalBallsPlayed(){
        return BallsPlayed;
    }

    void BallsPlayedTracker(){
        BallsPlayed ++;
    }

    public int getNumberOf6s(){
        return numberOf6s;
    }

    void trackNoOf6s(){
        numberOf6s++;
    }

    public int getNumberOf4s(){
        return numberOf4s;
    }

    void trackNoOf4s(){
        numberOf4s++;
    }

    public int getWicketsTaken(){
        return wicketsTaken;
    }

    void wicketTracker(){
        wicketsTaken++;
    }

    public int getNoBallsBowled(){
        return noBallsBowled;
    }

    void NoBallsBowledTracker(){
        noBallsBowled++;
    }

    public int getWideBallsBowled(){
        return wideBallsBowled;
    }

    void WideBallsBowledTracker(){
        wideBallsBowled++;
    }

    public double getBallsBowled(){
        return ballsBowled;
    }

    void BallsBowledTracker(){
        ballsBowled++;
    }

    public int getRunsGiven(){
        return runsGiven;
    }

    void runsGivenTracker(int runs){
        runsGiven += runs;
    }

    void setWicketTakenBy(String nameBowler){
        wicketTakenBy = nameBowler;
    }

    public String getWicketTakenBy(){
        return wicketTakenBy;
    }

}
