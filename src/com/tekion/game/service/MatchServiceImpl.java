package com.tekion.game.service;

import com.tekion.game.Repository.MatchRepository;
import com.tekion.game.Repository.ScoreBoardRepository;
import com.tekion.game.Repository.TeamRepository;
import com.tekion.game.bean.Matches;
import com.tekion.game.bean.Teams;
import com.tekion.game.models.Team;
import com.tekion.game.models.Umpire;
import com.tekion.game.util.MatchUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

@Service
public class MatchServiceImpl implements MatchService {
    // two teams to be declared for a match
    private final Team FirstInningsTeam  = new Team();
    private final Team SecondInningsTeam = new Team();
    TeamRepository teamRepository = new TeamRepository();
    MatchRepository matchRepository = new MatchRepository();
    ScoreBoardRepository scoreBoardRepository = new ScoreBoardRepository();
    Matches matches = new Matches();
    Teams teamA = new Teams();
    Teams teamB = new Teams();

    public MatchServiceImpl() throws SQLException, ClassNotFoundException {
    }


    public String matchDeclaration(int overs) throws SQLException {
        String[] teamName = {"India", "Australia", "New Zealand", "South Africa", "West Indies", "Pakistan", "Sri Lanka", "England","Bangladesh", "Zimbabwe"};
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
        int won = MatchUtils.tossWinner(team1, team2);
        int lost = MatchUtils.tossLoser();
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
        matches.setTotalOvers(overs);
        matchRepository.setMatchDBMatchDetails(matches);
        matches.setMatchID(matchRepository.getMatchIDbyTeamsIDAndStatus(teamA.getTeamID(),teamB.getTeamID(),"ongoing"));
    return "Match Created, Match id: "+matches.getMatchID()+" Team to bat first: "+teamA.getTeamName()+" First Inning Team ID: "+teamA.getTeamID()+" Team to bat second: "+teamB.getTeamName()+" Second Inning Team ID: "+teamB.getTeamID();
    }

//teamA is always firstInnings team.

    public ArrayList<String> startFirstInnings(int overs) throws SQLException, ClassNotFoundException {
        InningServiceImpl FirstInningService = new InningServiceImpl();
       return  FirstInningService.InitiateInnings(matches,FirstInningsTeam,SecondInningsTeam,overs,"1st");
    }

    public ArrayList<String> startSecondInnings(int overs) throws SQLException, ClassNotFoundException {
        InningServiceImpl SecondInningService = new InningServiceImpl();
        return SecondInningService.InitiateInnings(matches,SecondInningsTeam,FirstInningsTeam,overs,"2nd");
    }

    public void ShowResults() throws SQLException {
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
        matchRepository.setDBMatchResults(matches);
        scoreBoardRepository.setScoreBoardDBDetails(matches,FirstInningsTeam.getTeam(),SecondInningsTeam.getTeam());
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
        teamRepository.setDataTeamDB(teamName[team1]);
        teamA.setTeamName(teamName[team1]);
        teamA.setTeamID(teamRepository.getIdByTeamName(teamA.getTeamName()));
        FirstInningsTeam.setTeamID(teamA.getTeamID());
        teamRepository.setDataTeamDB(teamName[team2]);
        teamB.setTeamName(teamName[team2]);
        teamB.setTeamID(teamRepository.getIdByTeamName(teamB.getTeamName()));
        SecondInningsTeam.setTeamID(teamB.getTeamID());
    }
}
