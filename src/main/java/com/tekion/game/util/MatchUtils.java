package com.tekion.game.util;

import java.util.Random;

public class MatchUtils {

    private static int u1;
    private static int u2;
    private static final String[] umpires = {"Suresh Shastri", "Aleem Dar", "Billy Bowden"};

    public static String getFirstUmpire(){
        return umpires[MatchUtils.SetFirstUmpire()];
    }

    public static String getSecondUmpire(){
        return umpires[MatchUtils.SetSecondUmpire()];
    }

    public static String getThirdUmpire(){
        return umpires[MatchUtils.SetThirdUmpire()];
    }

    public static int SetFirstUmpire(){
        Random N  = new Random();
        u1 = N.nextInt(3);
        return u1;
    }

    public static int SetSecondUmpire(){
        Random N  = new Random();
        do{ u2 = N.nextInt(3);}
        while(u1 == u2);
        return u2;
    }

    public static int SetThirdUmpire(){
        return 3 - u1 - u2;
    }
    public static double oversBowled(double balls){
        if (balls % 6 == 0.0){
            return balls/6;
        }
        else{
            return (int)balls/6 + (balls%6)/10 ;
        }
    }


    private static int loser;

    public static int tossWinner(int team1, int team2){
        Random N = new Random();
        int coin = N.nextInt(2);
        System.out.print("\n\nCoins in the Air........");
        if (coin == 1){
            System.out.println(" and Tails it is.\n");
            tossLostWho(team1);
            return team2;
        }
        else{
            System.out.println(" and Heads it is.\n");
            tossLostWho(team2);
            return team1;
        }
    }

    private static void tossLostWho(int lost){
        loser = lost;
    }

    public static int tossLoser(){
        return loser;
    }


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
