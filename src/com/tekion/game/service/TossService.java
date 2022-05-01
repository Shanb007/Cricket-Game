package com.tekion.game.service;

import java.util.Random;

public class TossService {

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
}
