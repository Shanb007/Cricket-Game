package com.tekion.game.service;

import com.tekion.game.bean.Matches;
import com.tekion.game.models.Inning;
import com.tekion.game.models.Team;

import java.sql.SQLException;

public class InningsService {

    public static void InningsStart(Matches match,Team team1, Team team2, int overs) throws SQLException, ClassNotFoundException {
        Inning FirstInning = new Inning();
        System.out.println("\n1st Innings:");
        FirstInning.InitiateInnings(match,team1,team2,overs,"1st");
        Inning SecondInning = new Inning();
        System.out.println("2nd Innings:");
        SecondInning.InitiateInnings(match,team2,team1,overs,"2nd");
    }
}
