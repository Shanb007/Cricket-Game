package com.tekion.game.service;

public class StrikeService {

    private static int onStrike = 0;
    private static int notOnStrike = 1;

    public static int OnStrike(){
        return onStrike;
    }

    public static int NotOnStrike(){
        return notOnStrike;
    }

    public static void changeStrike(int currentStrike, int currentNonStrike){
        notOnStrike = currentStrike;
        onStrike = currentNonStrike;
    }
    public static void changeCurrentOnStrike(int newPlayerNumber){
        onStrike = newPlayerNumber;
    }
}
