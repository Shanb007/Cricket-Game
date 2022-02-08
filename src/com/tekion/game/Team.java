package com.tekion.game;
import java.util.Random;

public class Team {
    String[] teamName = {"India", "Australia", "New Zealand", "South Africa", "West Indies", "Pakistan", "Sri Lanka"};
public int ran ;
public String selectFirstTeam(){
Random N = new Random();
    ran = N.nextInt(teamName.length);
    //System.out.println("the first numb: "+ ran);
return teamName[ran];
}
    public String selectSecondTeam(){
    int ran2;
        Random N = new Random();
        ran2 = N.nextInt(teamName.length);
        // System.out.println("the first numb check: "+ ran);
        //System.out.println("the second numb: "+ ran2);
        if (ran2 == ran) {
           // System.out.println("the new second numb: "+ (ran2 + 1) % teamName.length);
                return teamName[(ran2 + 1) % teamName.length];
            }
        return teamName[ran2];
    }
}
