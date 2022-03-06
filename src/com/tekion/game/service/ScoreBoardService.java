package com.tekion.game.service;

import com.tekion.game.DBUpdateHelper.PlayerDBHelper;
import com.tekion.game.DBUpdateHelper.TeamDBHelper;
import com.tekion.game.bean.Matches;
import com.tekion.game.models.Player;
import com.tekion.game.models.Team;

import java.sql.SQLException;
import java.util.ArrayList;


public class ScoreBoardService {
    static PlayerDBHelper playerDBHelper;

    static {
        try {
            playerDBHelper = new PlayerDBHelper();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static TeamDBHelper teamDBHelper;

    static {
        try {
            teamDBHelper = new TeamDBHelper();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void showScoreBoard(Matches match, Team BatTeam, Team BowlTeam, ArrayList<Player> players, ArrayList<Player> bowl, String inning, ArrayList<String> wicketsFallen ) throws SQLException {
        viewScoreBoard(match,BatTeam,BowlTeam,players,bowl,inning, wicketsFallen);
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

    private static void viewScoreBoard(Matches match, Team BatTeam,Team BowlTeam,ArrayList<Player> players, ArrayList<Player> bowl, String inning, ArrayList<String> wicketsFallen) throws SQLException {
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        System.out.println("ScoreCard after "+ inning + " Innings:");
        System.out.println("\nBatsman\t\tRuns\t\t4s\t\t\t6s\t\t\tTB\t\t\tWicket Taken By");
        for (Player value : players) {
            System.out.println(value.getName() + "\t\t\t" + value.getRunsScored() + "\t\t\t" + value.getNumberOf4s() + "\t\t\t" + value.getNumberOf6s() + "\t\t\t" + value.TotalBallsPlayed() + "\t\t\t"+ value.getWicketTakenBy());
            //figure out how to extract matchID
            playerDBHelper.setDataPlayerMatchDetailsDB(playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BatTeam.getTeam()),value.getName()),match.getMatchID(),value);
        }
        System.out.println("\n\nBowler\t\tOvers Bowled\t\tWickets\t\tNB\t\t\tWB\t\t\tRuns Given");
        for(Player value : bowl){
            System.out.println(value.getName()+"\t\t\t\t"+ ballsToOvers.oversBowled(value.getBallsBowled())+"\t\t\t\t"+ value.getWicketsTaken()+"\t\t\t"+ value.getNoBallsBowled()+"\t\t\t"+ value.getWideBallsBowled()+"\t\t\t"+ value.getRunsGiven());
            playerDBHelper.setDataPlayerMatchDetailsDB(playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BowlTeam.getTeam()),value.getName()),match.getMatchID(),value);
        }


        System.out.println("\n"+"Total Extras: " + BatTeam.TotalExtras());
        System.out.println("NB: "+BatTeam.TotalNoBalls()+ "\t\t WB: "+ BatTeam.TotalWideBalls()+"\nExtra Runs: "+ BatTeam.getExtraScore());

        System.out.print("\nWickets: ");
        if(wicketsFallen.isEmpty()){
            System.out.print("Not fallen.");
        }
        else {
            for(String value : wicketsFallen) {
                System.out.print(value + "\t");
            }
        }

        System.out.println("\n\n"+BatTeam.getTeam()+ " scored " + BatTeam.TeamScore() + " at loss of " + BatTeam.totalWicketsGone() + " wickets.");
        System.out.println("\n-----------------------------------------------------------------------------------------------------\n");
    }

}
