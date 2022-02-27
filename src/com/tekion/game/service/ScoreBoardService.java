package com.tekion.game.service;

import com.tekion.game.models.Player;
import com.tekion.game.models.Team;
import java.util.ArrayList;


public class ScoreBoardService {

    public static void showScoreBoard(Team team, ArrayList<Player> players, ArrayList<Player> bowl, String inning,ArrayList<String> wicketsFallen ){
        viewScoreBoard(team,players,bowl,inning, wicketsFallen);
    }

    public static void showScoreBoardAfterWicketOROver(ArrayList<Player> bat, ArrayList<Player> bowl ){
        viewScoreBoardAfterWicketOROver(bat,bowl);
    }

    private static void viewScoreBoardAfterWicketOROver(ArrayList<Player> bat, ArrayList<Player> bowl ){
        System.out.println("Batsman\t\tRuns\t\t4s\t\t\t6s\t\t\tTB\t\t\tWicket Taken By");
        for (Player value : bat) {
            System.out.println(value.getName() + "\t\t\t" + value.getRunsScored() + "\t\t\t" + value.getNumberOf4s() + "\t\t\t" + value.getNumberOf6s() + "\t\t\t" + value.TotalBallsPlayed() + "\t\t\t"+ value.getWicketTakenBy());
        }

        System.out.println("\nBowler\t\tOvers Bowled\t\tWickets\t\tNB\t\t\tWB\t\t\tRuns Given");
        for(Player value : bowl){
            System.out.println(value.getName()+"\t\t\t\t"+ ballsToOvers.oversBowled(value.getBallsBowled())+"\t\t\t\t"+ value.getWicketsTaken()+"\t\t\t"+ value.getNoBallsBowled()+"\t\t\t"+ value.getWideBallsBowled()+"\t\t\t"+ value.getRunsGiven());
        }
    }

    private static void viewScoreBoard(Team team, ArrayList<Player> players, ArrayList<Player> bowl, String inning, ArrayList<String> wicketsFallen){
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        System.out.println("ScoreCard after "+ inning + " Innings:");
        System.out.println("\nBatsman\t\tRuns\t\t4s\t\t\t6s\t\t\tTB\t\t\tWicket Taken By");
        for (Player value : players) {
            System.out.println(value.getName() + "\t\t\t" + value.getRunsScored() + "\t\t\t" + value.getNumberOf4s() + "\t\t\t" + value.getNumberOf6s() + "\t\t\t" + value.TotalBallsPlayed() + "\t\t\t"+ value.getWicketTakenBy());
        }
        System.out.println("\n\nBowler\t\tOvers Bowled\t\tWickets\t\tNB\t\t\tWB\t\t\tRuns Given");
        for(Player value : bowl){
            System.out.println(value.getName()+"\t\t\t\t"+ ballsToOvers.oversBowled(value.getBallsBowled())+"\t\t\t\t"+ value.getWicketsTaken()+"\t\t\t"+ value.getNoBallsBowled()+"\t\t\t"+ value.getWideBallsBowled()+"\t\t\t"+ value.getRunsGiven());
        }


        System.out.println("\n"+"Total Extras: " + team.TotalExtras());
        System.out.println("NB: "+team.TotalNoBalls()+ "\t\t WB: "+ team.TotalWideBalls()+"\nExtra Runs: "+ team.getExtraScore());

        System.out.print("\nWickets: ");
        if(wicketsFallen.isEmpty()){
            System.out.print("Not fallen.");
        }
        else {
            for(String value : wicketsFallen) {
                System.out.print(value + "\t");
            }
        }

        System.out.println("\n\n"+team.getTeam()+ " scored " + team.TeamScore() + " at loss of " + team.totalWicketsGone() + " wickets.");
        System.out.println("\n-----------------------------------------------------------------------------------------------------\n");
    }

}
