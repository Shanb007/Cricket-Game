package com.tekion.game.Repository;

import com.tekion.game.DTO.*;
import com.tekion.game.bean.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class MatchRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public MatchRepository(){
    }

    public boolean checkIfMatchExistsOngoing(int teamA_ID, int teamB_ID){
        Integer check = null;
        try {
            String sqlQuery=  "SELECT MatchID FROM Matches WHERE TeamA_ID = ? AND TeamB_ID = ? AND matchStatus = ? ";
            check = jdbcTemplate.queryForObject(sqlQuery,Integer.class,teamA_ID,teamB_ID,"ongoing");
        } catch (EmptyResultDataAccessException e) {
           // e.printStackTrace();
            return false;
        }
        return check!=null && check>0;
    }

    public int setMatchDBMatchDetails(Matches match){
        if(!checkIfMatchExistsOngoing(match.getTeamA_ID(),match.getTeamB_ID())) {
            String sqlQuery = "INSERT INTO Matches(TeamA_ID, TeamB_ID, TotalOvers,tossWinner, tossWinnerChoice, matchStatus) VALUES (?,?,?,?,?,?)";
            return jdbcTemplate.update(sqlQuery,match.getTeamA_ID(),match.getTeamB_ID(),match.getTotalOvers(),match.getTossWinner(),match.getTossWinnerChoice(),"ongoing");
        }
        else{
            return 0;
        }
    }

    public void setDBMatchResults(Matches match){
        String sqlQuery = "INSERT INTO MatchResults(MatchID, Match_Winner) VALUES (?,?)";
        int status1 = jdbcTemplate.update(sqlQuery,match.getMatchID(),match.getMatch_Winner());
        String sqlQuery1 = "UPDATE Matches SET matchStatus = ? WHERE MatchID = ? ";
        int status2 = jdbcTemplate.update(sqlQuery1,"completed",match.getMatchID());
        if(status1 == 1 && status2 == 1){
            System.out.println("Results for the given match"+match.getMatchID()+" updated.");
        }
    }

    //matchID retrieve by ------
    public int getMatchIDbyTeamsIDAndStatus(int teamA_id, int teamB_id, String status){
        String sqlQuery = "Select MatchID FROM Matches WHERE TeamA_ID = (?) AND TeamB_ID = (?) AND matchStatus = (?) ";
        Object [] params = new Object[]{teamA_id,teamB_id,status};
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class, params);
    }

    public List<MatchDetailDTO> getMatchDetail(int matchID){
        RowMapper<MatchDetailDTO> matchDetailRowMapper = new BeanPropertyRowMapper<>(MatchDetailDTO.class);
        String sqlQuery = "select Matches.MatchID, Matches.TeamA_ID, Matches.TeamB_ID, Matches.TotalOvers, Matches.tossWinner, Matches.tossWinnerChoice, MatchResults.Match_Winner from Matches INNER JOIN MatchResults ON Matches.MatchID = MatchResults.MatchID WHERE Matches.MatchID = ? ";
        return jdbcTemplate.query(sqlQuery,matchDetailRowMapper,matchID);
    }

    public List<AllMatchesDTO> getAllMatches() {
        RowMapper<AllMatchesDTO> allMatchesRowMapper = new BeanPropertyRowMapper<>(AllMatchesDTO.class);
        String sqlQuery = "select * from Matches";
        return jdbcTemplate.query(sqlQuery,allMatchesRowMapper);
    }

    public List<MatchDetailDTO> getAllCompletedMatches(){
        RowMapper<MatchDetailDTO> matchDetailRowMapper = new BeanPropertyRowMapper<>(MatchDetailDTO.class);
        String sqlQuery = "select Matches.MatchID, Matches.TeamA_ID, Matches.TeamB_ID, Matches.TotalOvers, Matches.tossWinner, Matches.tossWinnerChoice, MatchResults.Match_Winner from Matches INNER JOIN MatchResults ON Matches.MatchID = MatchResults.MatchID";
        return jdbcTemplate.query(sqlQuery,matchDetailRowMapper);
    }

    public List<List<String>> getBattingScoreBoard(int matchID, int teamID){
        List<List<String>> ScoreCard = new ArrayList<>();
        RowMapper<BatsmanDTO> batsmanDTORowMapper = new BeanPropertyRowMapper<>(BatsmanDTO.class);
        String DidBat = "Y";
        String sqlQueryBat = "select playerID, runsScored, ballsPlayed, numberOf4s,numberOf6s,wicketTakenBy FROM PlayersMatchDetails WHERE matchID = ? AND teamID = ? AND DidBat = ?";
        List<BatsmanDTO> battingLine = jdbcTemplate.query(sqlQueryBat,batsmanDTORowMapper,matchID,teamID,DidBat);
        for(BatsmanDTO value: battingLine){
            List<String> batsman = new ArrayList<>();
            batsman.add("Batsman ID: "+value.getPlayerID());
            batsman.add("Runs Scored: "+value.getRunsScored());
            batsman.add("Balls Played: "+value.getBallsPlayed());
            batsman.add("4s: "+value.getNumberOf4s());
            batsman.add("6s: "+value.getNumberOf6s());
            batsman.add("Wicket Taken By: "+value.getWicketTakenBy());
            ScoreCard.add(batsman);
        }
        // bowlers of 2nd team.
        RowMapper<BowlerDTO> bowlerDTORowMapper = new BeanPropertyRowMapper<>(BowlerDTO.class);
        String DidBall = "Y";
        String sqlQueryBall = "select playerID, oversBowled, wicketsTaken, noBallsBowled, wideBallsBowled, runsGiven FROM PlayersMatchDetails  WHERE matchID = ? AND teamID = ? AND DidBall = ? ";
        List<BowlerDTO> bowlingLine = jdbcTemplate.query(sqlQueryBall,bowlerDTORowMapper,matchID,getBowlingTeamIDbyBatTeamAndMatchID(matchID,teamID),DidBall);
        for(BowlerDTO value: bowlingLine){
            List<String> bowler = new ArrayList<>();
            bowler.add("Bowler ID: "+value.getPlayerID());
            bowler.add("Overs Bowled: "+value.getOversBowled());
            bowler.add("Wickets Taken: "+value.getWicketsTaken());
            bowler.add("No Balls Bowled: "+value.getNoBallsBowled());
            bowler.add("Wide Balls Bowled: "+value.getWideBallsBowled());
            bowler.add("Runs Given: "+value.getRunsGiven());
            ScoreCard.add(bowler);
        }
        return ScoreCard;
    }

    private int getBowlingTeamIDbyBatTeamAndMatchID(int matchID,int teamID){
        String sqlQuery = "select TeamA_ID, TeamB_ID FROM Matches where MatchID = ? ";
        RowMapper<TeamIDsDTO> teamIDsRowMapper = new BeanPropertyRowMapper<>(TeamIDsDTO.class);
        List<TeamIDsDTO> otherTeamIDCheck = jdbcTemplate.query(sqlQuery,teamIDsRowMapper,matchID);
        if(otherTeamIDCheck.get(0).getTeamA_ID()==teamID){
            return otherTeamIDCheck.get(0).getTeamB_ID();
        }
        return otherTeamIDCheck.get(0).getTeamA_ID();
    }

    public int getOtherTeamIDbyMatchIDAndOneTeamID(int matchID, int teamID){
        String sqlQuery = "select TeamA_ID, TeamB_ID FROM Matches where MatchID = ? ";
        RowMapper<TeamIDsDTO> teamIDsRowMapper = new BeanPropertyRowMapper<>(TeamIDsDTO.class);
        List<TeamIDsDTO> otherTeamIDCheck = jdbcTemplate.query(sqlQuery,teamIDsRowMapper,matchID);
        if(otherTeamIDCheck.get(0).getTeamA_ID()==teamID){
            return otherTeamIDCheck.get(0).getTeamB_ID();
        }
        return otherTeamIDCheck.get(0).getTeamA_ID();
    }

    public double getOversByMatchID(int matchID){
        String sqlQuery = "select TotalOvers FROM Matches where MatchID = ? ";
        return jdbcTemplate.queryForObject(sqlQuery,Integer.class,matchID);
    }

}
