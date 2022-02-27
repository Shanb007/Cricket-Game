package com.tekion.game.models;

import java.util.Random;

public class Toss {

    private int loser;

    int tossWinner(int team1, int team2){
        Random N = new Random();
        int coin = N.nextInt(2);
        System.out.println("Coins in the Air........");
        if (coin == 1){
            System.out.println("and it's Tails.");
            tossLostWho(team1);
            return team2;
        }
        else{
            System.out.println("and it's Heads.");
            tossLostWho(team2);
            return team1;
        }
    }

    private void tossLostWho(int lost){
        loser = lost;
    }

    int tossLoser(){
        return loser;
    }

}
