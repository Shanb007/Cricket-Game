package com.tekion.game.models;

import com.tekion.game.DBUpdateHelper.MatchDBHelper;
import com.tekion.game.DBUpdateHelper.ScoreBoardDBHelper;
import com.tekion.game.DBUpdateHelper.TeamDBHelper;
import com.tekion.game.bean.Matches;
import com.tekion.game.bean.Teams;
import com.tekion.game.service.InningsService;
import com.tekion.game.service.TossService;

import java.sql.SQLException;
import java.util.Random;

public class Match {
    // two teams to be declared for a match
    private final Team FirstInningsTeam  = new Team();
    private final Team SecondInningsTeam = new Team();
    TeamDBHelper teamDBHelper = new TeamDBHelper();
    MatchDBHelper matchDBHelper = new MatchDBHelper();
    ScoreBoardDBHelper scoreBoardDBHelper = new ScoreBoardDBHelper();
    Matches matches = new Matches();
    Teams teamA = new Teams();
    Teams teamB = new Teams();

    public Match() throws SQLException, ClassNotFoundException {
    }

    public void matchDeclaration() throws SQLException {
        String[] teamName = {"India", "Australia", "New Zealand", "South Africa", "West Indies", "Pakistan", "Sri Lanka"};
        //add check and then proceed.
        Random randomPick = new Random();
        int team1 = randomPick.nextInt(teamName.length);
        int team2 = randomPick.nextInt(teamName.length);

        while(team1 == team2){
            team2 = randomPick.nextInt(teamName.length);
        }
        Umpire umpire = new Umpire();
        System.out.println("\nThe Match: " + teamName[team1] + " V/S " + teamName[team2]);
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("The Umpire behind the stumps for the match is: "+ umpire.getFirstUmpire()+".");
        System.out.println("The Leg Side Umpire for the match is: "+ umpire.getSecondUmpire()+".");
        System.out.println("The Third Umpire for the match is: "+ umpire.getThirdUmpire()+".");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nBoth the Captains are on the field for the Toss.");
        int won = TossService.tossWinner(team1, team2);
        int lost = TossService.tossLoser();
        int choiceMade = randomPick.nextInt(2);
        if (choiceMade==1){
            FirstInningsTeam.setTeamName(teamName[won]);
            SecondInningsTeam.setTeamName(teamName[lost]);
            setTeamBeans(won,lost,teamName);
            System.out.println(FirstInningsTeam.getTeam() + " won the toss, and chose to Bat first.");
            setMatchBeanMatchDetails(teamA.getTeamID(),teamB.getTeamID(),teamName[won],"Bat");
        }
        else{
            FirstInningsTeam.setTeamName(teamName[lost]);
            SecondInningsTeam.setTeamName(teamName[won]);
            setTeamBeans(lost,won,teamName);
            System.out.println(SecondInningsTeam.getTeam()+" won the toss, and chose to ball first.");
            setMatchBeanMatchDetails(teamA.getTeamID(),teamB.getTeamID(),teamName[won],"Ball");
        }
    }

//teamA is always firstinnings team.

    public void startTheMatch(int overs) throws SQLException, ClassNotFoundException {
        matches.setTotalOvers(overs);
        matchDBHelper.setMatchDBMatchDetails(matches);
        matches.setMatchID(matchDBHelper.getMatchIDbyTeamsID(teamA.getTeamID(),teamB.getTeamID()));
        InningsService.InningsStart(matches,FirstInningsTeam,SecondInningsTeam,overs);
        ShowResults();
    }

    private void ShowResults() throws SQLException {
        System.out.println();
        if (FirstInningsTeam.TeamScore() == SecondInningsTeam.TeamScore()){
            System.out.println("Match Tied.\n\n");
            setMatchBeanResult("Tied",FirstInningsTeam.TeamScore(),FirstInningsTeam.totalWicketsGone(),SecondInningsTeam.TeamScore(), SecondInningsTeam.totalWicketsGone());
        }
        if (FirstInningsTeam.TeamScore() > SecondInningsTeam.TeamScore()){
            System.out.println(FirstInningsTeam.getTeam() + " won by " + (FirstInningsTeam.TeamScore()-SecondInningsTeam.TeamScore()) + " runs.\n\n");
            setMatchBeanResult(FirstInningsTeam.getTeam(),FirstInningsTeam.TeamScore(),FirstInningsTeam.totalWicketsGone(),SecondInningsTeam.TeamScore(), SecondInningsTeam.totalWicketsGone());
        }
        if (FirstInningsTeam.TeamScore() < SecondInningsTeam.TeamScore()){
            System.out.println(SecondInningsTeam.getTeam() + " won by " + (10-SecondInningsTeam.totalWicketsGone() + " wickets.\n\n"));
            setMatchBeanResult(SecondInningsTeam.getTeam(),FirstInningsTeam.TeamScore(),FirstInningsTeam.totalWicketsGone(),SecondInningsTeam.TeamScore(), SecondInningsTeam.totalWicketsGone());
        }
        matchDBHelper.setDBMatchResults(matches);
        scoreBoardDBHelper.setScoreBoardDBDetails(matches,FirstInningsTeam.getTeam(),SecondInningsTeam.getTeam());
       // System.out.println(matches.getMatchID());
    }

    private void setMatchBeanMatchDetails(int TeamA_ID,int TeamB_ID, String tossWinner, String tossWinnerChoice){
        //matches.setMatchID(MatchID);
        matches.setTeamA_ID(TeamA_ID);
        matches.setTeamB_ID(TeamB_ID);
        matches.setTossWinner(tossWinner);
        matches.setTossWinnerChoice(tossWinnerChoice);
    }

    private void setMatchBeanResult(String MatchWinner,int TeamAScore, int TeamAWicketsFallen,int TeamBScore, int TeamBWicketsFallen){
        matches.setMatch_Winner(MatchWinner);
        matches.setTeamA_Score(TeamAScore);
        matches.setTeamB_Score(TeamBScore);
        matches.setTeamA_WicketsFallen(TeamAWicketsFallen);
        matches.setTeamB_WicketsFallen(TeamBWicketsFallen);
    }

    private void setTeamBeans(int team1, int team2, String[] teamName) throws SQLException {
        teamDBHelper.setDataTeamDB(teamName[team1]);
        teamA.setTeamName(teamName[team1]);
        teamA.setTeamID(teamDBHelper.getIdByTeamName(teamA.getTeamName()));
        teamDBHelper.setDataTeamDB(teamName[team2]);
        teamB.setTeamName(teamName[team2]);
        teamB.setTeamID(teamDBHelper.getIdByTeamName(teamB.getTeamName()));
    }
}
