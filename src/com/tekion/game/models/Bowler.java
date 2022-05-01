package com.tekion.game.models;

public class Bowler {
    private double Econ;
    private int noOf5w;
    private int noOf10w;
    private int TotalWicketsTakenSoFar;
    private int TotalMatchesPlayed;
    private String BowlerHanded;
    private String BowlerType;

    void setEcon(double econ){
        Econ = econ;
    }
    double getEcon(){
        return Econ;
    }

    void setNoOf5w(int total5ws){
        noOf5w=total5ws;
    }
    int getNoOf5w(){
        return noOf5w;
    }

    void setNoOf10w(int total10ws){
        noOf10w=total10ws;
    }
    int getNoOf10w(){
        return noOf10w;
    }

    void setTotalMatchesPlayed(int TotalMatch){
        TotalMatchesPlayed = TotalMatch;
    }
    int getTotalMatchesPlayed(){
        return TotalMatchesPlayed;
    }

    void setTotalWicketsTakenSoFar(int totalWickets){
        TotalWicketsTakenSoFar = totalWickets;
    }
    int getTotalWicketsTakenSoFar(){
        return TotalWicketsTakenSoFar;
    }

    void setBowlerType(String type){
        BowlerType = type;
    }
    String getBowlerType(){
        return BowlerType;
    }

    void setBowlerHanded(String hand){
        BowlerHanded = hand;
    }
    String getBowlerHanded(){
        return BowlerHanded;
    }
}
