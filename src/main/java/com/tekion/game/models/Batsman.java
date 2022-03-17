package com.tekion.game.models;

public class Batsman {
    private  double StrikeRate;
    private int NoOf50s;
    private int NoOf100s;
    private String BatsmanHanded;
    private int TotalMatchesPlayed;
    private int TotalRunsSoFar;

    void setStrikeRate(double SR){
        StrikeRate = SR;
    }
    double getStrikeRate(){
        return StrikeRate;
    }

    void setNoOf50s(int total50s){
        NoOf50s=total50s;
    }
    int getNoOf50s(){
        return NoOf50s;
    }

    void setNoOf100s(int total100s){
        NoOf100s=total100s;
    }
    int getNoOf100s(){
        return NoOf100s;
    }

    void setBatsmanHanded(String hand){
        BatsmanHanded = hand;
    }
    String getBatsmanHanded(){
        return BatsmanHanded;
    }

    void setTotalMatchesPlayed(int matches){
        TotalMatchesPlayed = matches;
    }
    int getTotalMatchesPlayed(){
        return TotalMatchesPlayed;
    }

    void setTotalRunsSoFar(int runs){
        TotalRunsSoFar = runs;
    }
    int getTotalRunsSoFar(){
        return TotalRunsSoFar;
    }
}
