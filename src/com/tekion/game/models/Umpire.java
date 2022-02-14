package com.tekion.game.models;

import java.util.Random;

public class Umpire {
private final String[] umpires = {"Suresh Shastri", "Aleem Dar", "Billy Bowden"};
 private int u1, u2, u3;

void SetFirstUmpire(){
    Random N  = new Random();
    u1 = N.nextInt(3);
}

String getFirstUmpire(){
    return umpires[u1];
}

void SetSecondUmpire(){
        Random N  = new Random();
       do{ u2 = N.nextInt(3);}
       while(u1 == u2);
    }

    String getSecondUmpire(){
        return umpires[u2];
    }

void SetThirdUmpire(){
        u3 = 3-u1-u2;
    }

    String getThirdUmpire(){
        return umpires[u3];
    }

}
