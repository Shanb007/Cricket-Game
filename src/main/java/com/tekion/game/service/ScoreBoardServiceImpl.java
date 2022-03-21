package com.tekion.game.service;

import com.tekion.game.Repository.PlayerRepository;
import com.tekion.game.Repository.TeamRepository;
import com.tekion.game.bean.Matches;
import com.tekion.game.models.Player;
import com.tekion.game.models.Team;
import com.tekion.game.util.MatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ScoreBoardServiceImpl implements ScoreBoardService {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamRepository teamRepository;

    public ScoreBoardServiceImpl() {
    }


    public void viewScoreBoardAfterWicketOROver(ArrayList<Player> bat, ArrayList<Player> bowl ){
        System.out.println("Batsman\t\tRuns\t\t4s\t\t\t6s\t\t\tTB\t\t\tWicket Taken By");
        for (Player value : bat) {
            System.out.println(value.getName() + "\t\t\t" + value.getRunsScored() + "\t\t\t" + value.getNumberOf4s() + "\t\t\t" + value.getNumberOf6s() + "\t\t\t" + value.TotalBallsPlayed() + "\t\t\t"+ value.getWicketTakenBy());
        }

        System.out.println("\nBowler\t\tOvers Bowled\t\tWickets\t\tNB\t\t\tWB\t\t\tRuns Given");
        for(Player value : bowl){
            System.out.println(value.getName()+"\t\t\t\t"+ MatchUtils.oversBowled(value.getBallsBowled())+"\t\t\t\t"+ value.getWicketsTaken()+"\t\t\t"+ value.getNoBallsBowled()+"\t\t\t"+ value.getWideBallsBowled()+"\t\t\t"+ value.getRunsGiven());
        }
    }

    public void viewScoreBoard(Matches match, Team BatTeam, Team BowlTeam, ArrayList<Player> players, ArrayList<Player> bowl, String inning, ArrayList<String> wicketsFallen) {
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        System.out.println("ScoreCard after "+ inning + " Innings:");
        System.out.println("\nBatsman\t\tRuns\t\t4s\t\t\t6s\t\t\tTB\t\t\tWicket Taken By");
        for (Player value : players) {
            System.out.println(value.getName() + "\t\t\t" + value.getRunsScored() + "\t\t\t" + value.getNumberOf4s() + "\t\t\t" + value.getNumberOf6s() + "\t\t\t" + value.TotalBallsPlayed() + "\t\t\t"+ value.getWicketTakenBy());
            //figure out how to extract matchID
       //     playerRepository.setDataPlayerMatchDetailsDB(playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(BatTeam.getTeam()),value.getName()),1,match.getMatchID(),value);
        }
        System.out.println("\n\nBowler\t\tOvers Bowled\t\tWickets\t\tNB\t\t\tWB\t\t\tRuns Given");
        for(Player value : bowl){
            System.out.println(value.getName()+"\t\t\t\t"+ MatchUtils.oversBowled(value.getBallsBowled())+"\t\t\t\t"+ value.getWicketsTaken()+"\t\t\t"+ value.getNoBallsBowled()+"\t\t\t"+ value.getWideBallsBowled()+"\t\t\t"+ value.getRunsGiven());
          //  playerRepository.setDataPlayerMatchDetailsDB(playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(BowlTeam.getTeam()),value.getName()),1,match.getMatchID(),value);
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
