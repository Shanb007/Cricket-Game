package com.tekion.game.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UmpireServiceImpl implements UmpireService {
    private static int u1;
    private static int u2;

    public int SetFirstUmpire(){
        Random N  = new Random();
        u1 = N.nextInt(3);
        return u1;
    }

    public int SetSecondUmpire(){
        Random N  = new Random();
        do{ u2 = N.nextInt(3);}
        while(u1 == u2);
        return u2;
    }

    public int SetThirdUmpire(){
        return 3 - u1 - u2;
    }

}
