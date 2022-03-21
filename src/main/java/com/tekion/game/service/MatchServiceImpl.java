package com.tekion.game.service;

import com.tekion.game.DTO.AllMatchesDTO;
import com.tekion.game.DTO.MatchDetailDTO;
import com.tekion.game.Repository.MatchRepository;
import com.tekion.game.Repository.ScoreBoardRepository;
import com.tekion.game.Repository.TeamRepository;
import com.tekion.game.bean.Matches;
import com.tekion.game.models.Team;
import com.tekion.game.util.MatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private ScoreBoardRepository scoreBoardRepository;

    public MatchServiceImpl(){
    }

    public String matchDeclaration(String team1, String team2, int overs) throws SQLException, ClassNotFoundException {
        Matches matches = new Matches();
        ArrayList<String> teamName = new ArrayList<>();
        Team FirstInningsTeam  = new Team();
        Team SecondInningsTeam = new Team();
        Random randomPick = new Random();
        teamName.add(team1.toLowerCase());
        teamName.add(team2.toLowerCase());
        System.out.println("\nThe Match: " + team1 + " V/S " + team2);
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("The Umpire behind the stumps for the match is: "+ MatchUtils.getFirstUmpire()+".");
        System.out.println("The Leg Side Umpire for the match is: "+ MatchUtils.getSecondUmpire()+".");
        System.out.println("The Third Umpire for the match is: "+ MatchUtils.getThirdUmpire()+".");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nBoth the Captains are on the field for the Toss.");
        int won = MatchUtils.tossWinner(0,1);
        int lost = MatchUtils.tossLoser();
        int choiceMade = randomPick.nextInt(2);
        if (choiceMade==1){
            FirstInningsTeam.setTeamName(teamName.get(won));
            SecondInningsTeam.setTeamName(teamName.get(lost));
            teamRepository.setDataTeamDB(teamName.get(won));
            FirstInningsTeam.setTeamID(teamRepository.getIdByTeamName(teamName.get(won)));
            teamRepository.setDataTeamDB(teamName.get(lost));
            SecondInningsTeam.setTeamID(teamRepository.getIdByTeamName(teamName.get(lost)));
            System.out.println(FirstInningsTeam.getTeam() + " won the toss, and chose to Bat first.");
            setMatchBeanMatchDetails(matches,FirstInningsTeam.getTeamID(),SecondInningsTeam.getTeamID(), teamName.get(won),"Bat");
        }
        else{
            FirstInningsTeam.setTeamName(teamName.get(lost));
            SecondInningsTeam.setTeamName(teamName.get(won));
            teamRepository.setDataTeamDB(teamName.get(won));
            FirstInningsTeam.setTeamID(teamRepository.getIdByTeamName(teamName.get(lost)));
            teamRepository.setDataTeamDB(teamName.get(lost));
            SecondInningsTeam.setTeamID(teamRepository.getIdByTeamName(teamName.get(won)));
            System.out.println(SecondInningsTeam.getTeam()+" won the toss, and chose to ball first.");
            setMatchBeanMatchDetails(matches,FirstInningsTeam.getTeamID(),SecondInningsTeam.getTeamID(), teamName.get(won),"Ball");
        }
        matches.setTotalOvers(overs);
        int checkMatchExists = matchRepository.setMatchDBMatchDetails(matches);
        if(checkMatchExists == 0){
            return "Match Already going on between the two teams. Can't create a new one.";
        }
        matches.setMatchID(matchRepository.getMatchIDbyTeamsIDAndStatus(FirstInningsTeam.getTeamID(),SecondInningsTeam.getTeamID(),"ongoing"));
        return "Match Created, Match id: "+matches.getMatchID()+" Team to bat first: "+FirstInningsTeam.getTeam()+" First Inning Team ID: "+FirstInningsTeam.getTeamID()+" Team to bat second: "+SecondInningsTeam.getTeam()+" Second Inning Team ID: "+SecondInningsTeam.getTeamID();
    }

    public ArrayList<String> startFirstInnings(int overs, int matchID, int BatTeamID, int BallTeamID){
        InningServiceImpl FirstInningService = new InningServiceImpl();
        teamRepository.setScoreBoard(matchID,BatTeamID);
        teamRepository.setScoreBoard(matchID,BallTeamID);
        String BatTeamName = teamRepository.getTeamName(BatTeamID);
        String BallTeamName = teamRepository.getTeamName(BallTeamID);
       // System.out.println(BatTeamID + "   " +BallTeamID);
        return  FirstInningService.InitiateInnings(BatTeamName,BallTeamName,overs,"1st");
    }

    public ArrayList<String> startSecondInnings(int overs, int matchID, int BatTeamID, int BallTeamID){
        InningServiceImpl SecondInningService = new InningServiceImpl();
        String BatTeamName = teamRepository.getTeamName(BatTeamID);
        String BallTeamName = teamRepository.getTeamName(BallTeamID);
        return SecondInningService.InitiateInnings(BatTeamName,BallTeamName,overs,"2nd");
    }

    public String ShowResults(int matchID, int TeamA_ID, int TeamB_ID){
        Matches matches = new Matches();
       // System.out.println();
        Team FirstInningsTeam = teamRepository.getTeamScorecardRequested(matchID,TeamA_ID);
        Team SecondInningsTeam = teamRepository.getTeamScorecardRequested(matchID,TeamB_ID);
        if (FirstInningsTeam.TeamScore() == SecondInningsTeam.TeamScore()){
            System.out.println("Match Tied.\n\n");
            setMatchBeanResult(matches,matchID,"Tied",FirstInningsTeam.TeamScore(),FirstInningsTeam.totalWicketsGone(),SecondInningsTeam.TeamScore(), SecondInningsTeam.totalWicketsGone());
            matchRepository.setDBMatchResults(matches);
            scoreBoardRepository.setScoreBoardDBDetails(matches,FirstInningsTeam.getTeam(),SecondInningsTeam.getTeam());
            return "Match Id: "+matchID+". Match Tied.";
        }
        if (FirstInningsTeam.TeamScore() > SecondInningsTeam.TeamScore()){
            System.out.println(FirstInningsTeam.getTeam() + " won by " + (FirstInningsTeam.TeamScore()-SecondInningsTeam.TeamScore()) + " runs.\n\n");
            setMatchBeanResult(matches,matchID,teamRepository.getTeamName(TeamA_ID),FirstInningsTeam.TeamScore(),FirstInningsTeam.totalWicketsGone(),SecondInningsTeam.TeamScore(), SecondInningsTeam.totalWicketsGone());
            matchRepository.setDBMatchResults(matches);
            scoreBoardRepository.setScoreBoardDBDetails(matches,teamRepository.getTeamName(TeamA_ID),teamRepository.getTeamName(TeamB_ID));
            return teamRepository.getTeamName(TeamA_ID) + " won by " + (FirstInningsTeam.TeamScore()-SecondInningsTeam.TeamScore()) + " runs.";
        }
        if (FirstInningsTeam.TeamScore() < SecondInningsTeam.TeamScore()){
            System.out.println(SecondInningsTeam.getTeam() + " won by " + (10-SecondInningsTeam.totalWicketsGone() + " wickets.\n\n"));
            setMatchBeanResult(matches,matchID,teamRepository.getTeamName(TeamB_ID),FirstInningsTeam.TeamScore(),FirstInningsTeam.totalWicketsGone(),SecondInningsTeam.TeamScore(), SecondInningsTeam.totalWicketsGone());
            matchRepository.setDBMatchResults(matches);
            scoreBoardRepository.setScoreBoardDBDetails(matches,teamRepository.getTeamName(TeamA_ID),teamRepository.getTeamName(TeamB_ID));
            return teamRepository.getTeamName(TeamB_ID) + " won by " + (10-SecondInningsTeam.totalWicketsGone()) + " wickets.";
        }
        return "?";
    }

    public List<MatchDetailDTO> matchDetails(int matchID) throws SQLException {
        return matchRepository.getMatchDetail(matchID);
    }

    public List<AllMatchesDTO> AllMatches(){
        return matchRepository.getAllMatches();
    }

    public List<MatchDetailDTO> AllCompletedMatches(){
        return matchRepository.getAllCompletedMatches();
    }

    public List<List<String>> matchScoreBoard(int matchID,int teamID){
        return matchRepository.getBattingScoreBoard(matchID,teamID);
    }

    public double getOversForMatch(int matchID) {
        return matchRepository.getOversByMatchID(matchID);
    }

    private void setMatchBeanMatchDetails(Matches matches,int TeamA_ID,int TeamB_ID, String tossWinner, String tossWinnerChoice){
        matches.setTeamA_ID(TeamA_ID);
        matches.setTeamB_ID(TeamB_ID);
        matches.setTossWinner(tossWinner);
        matches.setTossWinnerChoice(tossWinnerChoice);
    }

    private void setMatchBeanResult(Matches matches,int id, String MatchWinner,int TeamAScore, int TeamAWicketsFallen,int TeamBScore, int TeamBWicketsFallen){
        matches.setMatchID(id);
        matches.setMatch_Winner(MatchWinner);
        matches.setTeamA_Score(TeamAScore);
        matches.setTeamB_Score(TeamBScore);
        matches.setTeamA_WicketsFallen(TeamAWicketsFallen);
        matches.setTeamB_WicketsFallen(TeamBWicketsFallen);
    }
}
