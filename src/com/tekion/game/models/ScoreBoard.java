package com.tekion.game.models;

import java.util.ArrayList;

public class ScoreBoard {

    void viewScoreBoard(Team team, ArrayList<Player> players, ArrayList<Player> bowl, String inning){
        System.out.println();
    System.out.println(team.getTeam()+ " scored " + team.TeamScore() + " at loss of " + team.totalWicketsGone() + " wickets.");
    System.out.println("ScoreCard after "+ inning + " Innings:");
    System.out.println("Name\t\tRuns\t\t4s\t\t\t6s\t\t\tTB");
        for (Player value : players) {
            System.out.println(value.getName() + "\t\t\t" + value.getRunsScored() + "\t\t\t" + value.getNumberOf4s() + "\t\t\t" + value.getNumberOf6s() + "\t\t\t" + value.TotalBallsPlayed());
        }
        System.out.println("Total Extras: " + team.TotalExtras());
        System.out.println("NB: "+team.TotalNoBalls()+ " WB: "+ team.TotalWideBalls());
        System.out.println("Extra Runs: "+ team.getExtraScore());

        System.out.println();
        System.out.println("Name\t\tOvers Bowled\t\tWickets\t\tNB\t\t\tWB\t\t\tRuns Given");
        for(Player value : bowl){
            System.out.println(value.getName()+"\t\t\t\t"+ value.getOversBowled()+"\t\t\t\t"+ value.getWicketsTaken()+"\t\t\t"+ value.getNoBallsBowled()+"\t\t\t"+ value.getWideBallsBowled()+"\t\t\t"+ value.getRunsGiven());
        }

    }

}
