package com.tekion.game.service;

public class ballsToOvers {
    public static double oversBowled(double balls){
        if (balls % 6 == 0.0){
            return balls/6;
        }
        else{
            return (int)balls/6 + (balls%6)/10 ;
        }
    }
}
