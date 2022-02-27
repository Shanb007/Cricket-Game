package com.tekion.game.models;

import java.util.Random;

public class Ball {

    int runs(){
        Random randomOutcome = new Random();
        int run = randomOutcome.nextInt(7);
        while(run == 5){
            // can't score 5 runs if played the ball (No extra)
            run = randomOutcome.nextInt(7);
        }
        return run;
    }

    int NoBall(){
        Random randomOutcome = new Random();
        if(randomOutcome.nextInt(2)==1){
            //1 score for NoBall Plus if extra are scored on NoBall (0,1,2,3,4) {5 ain't possible}
            return (1 + randomOutcome.nextInt(5));
        }
        //if a six is scored on a NoBall
        return 7;
    }

    int wideBall(){
        Random randomOutcome = new Random();
        return (1 + randomOutcome.nextInt(5));
    }

    int wicket(){
        return 0;
    }

}
