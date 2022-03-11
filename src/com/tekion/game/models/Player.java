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
    private String didBat;
    private String didBall;
    private int playerID;
    private double oversBowled;

    public Player(String PlayerName){
        this.PlayerName = PlayerName;
        this.runsScored = 0;
        this.BallsPlayed = 0;
        this.numberOf4s = 0;
        this.numberOf6s = 0;
        this.wicketsTaken = 0;
        this.ballsBowled = 0.0;
        this.noBallsBowled = 0;
        this.wideBallsBowled= 0;
        this.runsGiven = 0;
        this.wicketTakenBy = "-";
        this.didBall= "N";
        this.didBat = "N";
        this.oversBowled = 0.0;
    }

    public String getName(){
        return PlayerName;
    }

    public int getRunsScored(){
        return runsScored;
    }

    public void runTracker(int score){
        runsScored += score;
    }

    public int TotalBallsPlayed(){
        return BallsPlayed;
    }

    public void BallsPlayedTracker(){
        BallsPlayed ++;
    }

    public int getNumberOf6s(){
        return numberOf6s;
    }

    public void trackNoOf6s(){
        numberOf6s++;
    }

    public int getNumberOf4s(){
        return numberOf4s;
    }

    public void trackNoOf4s(){
        numberOf4s++;
    }

    public int getWicketsTaken(){
        return wicketsTaken;
    }

    public void wicketTracker(){
        wicketsTaken++;
    }

    public int getNoBallsBowled(){
        return noBallsBowled;
    }

    public void NoBallsBowledTracker(){
        noBallsBowled++;
    }

    public int getWideBallsBowled(){
        return wideBallsBowled;
    }

    public void WideBallsBowledTracker(){
        wideBallsBowled++;
    }

    public double getBallsBowled(){
        return ballsBowled;
    }

    public void BallsBowledTracker(){
        ballsBowled++;
    }

    public int getRunsGiven(){
        return runsGiven;
    }

    public void runsGivenTracker(int runs){
        runsGiven += runs;
    }

    public void setWicketTakenBy(String nameBowler){
        wicketTakenBy = nameBowler;
    }

    public String getWicketTakenBy(){
        return wicketTakenBy;
    }

    public String getDidBall() {
        return didBall;
    }

    public String getDidBat() {
        return didBat;
    }

    public void setDidBall(String didBall) {
        this.didBall = didBall;
    }

    public void setDidBat(String didBat) {
        this.didBat = didBat;
    }


    public void setOversBowled(double oversBowled) {
        this.oversBowled = oversBowled;
    }

    public void setWideBallsBowled(int wideBallsBowled) {
        this.wideBallsBowled = wideBallsBowled;
    }


    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public void setRunsGiven(int runsGiven) {
        this.runsGiven = runsGiven;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setNumberOf6s(int numberOf6s) {
        this.numberOf6s = numberOf6s;
    }

    public void setNumberOf4s(int numberOf4s) {
        this.numberOf4s = numberOf4s;
    }

    public void setNoBallsBowled(int noBallsBowled) {
        this.noBallsBowled = noBallsBowled;
    }

    public void setBallsPlayed(int ballsPlayed) {
        BallsPlayed = ballsPlayed;
    }

    public int getPlayerID() {
        return playerID;
    }

    public double getOversBowled() {
        return oversBowled;
    }
}
