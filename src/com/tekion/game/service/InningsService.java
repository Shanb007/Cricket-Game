package com.tekion.game.service;

import com.tekion.game.models.Inning;
import com.tekion.game.models.Team;

public class InningsService {
    public void InningsStart(Team team1,Team team2, int overs){
        Inning inning = new Inning();
        System.out.println("1st Innings:");
        inning.InitiateFirstInnings(team1,overs);
        System.out.println("2nd Innings:");
        inning.InitiateSecondInnings(team2,team1,overs);
    }
}
