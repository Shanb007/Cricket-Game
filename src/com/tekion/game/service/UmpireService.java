package com.tekion.game.service;

import java.util.Random;

public class UmpireService {

    private static int u1;
    private static int u2;

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

}
