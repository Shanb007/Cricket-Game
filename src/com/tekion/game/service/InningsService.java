package com.tekion.game.service;

import com.tekion.game.models.Inning;
import com.tekion.game.models.Team;

public class InningsService {

    public static void InningsStart(Team team1,Team team2, int overs){
        Inning FirstInning = new Inning();
        System.out.println("\n1st Innings:");
        FirstInning.InitiateInnings(team1,team2,overs,"1st");
        Inning SecondInning = new Inning();
        System.out.println("2nd Innings:");
        SecondInning.InitiateInnings(team2,team1,overs,"2nd");
    }
}
