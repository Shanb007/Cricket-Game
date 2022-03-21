package com.tekion.game.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Map;


@Repository
public class BallRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public BallRepository(){
    }

    public void setBallDetailsDB(int matchID, int batTeamID ,int ballNumber, int currentOver, String ballOutcome, int batsmanID, int bowlerID, String inning){
        String sqlQuery = "Insert into PerBallDetails(matchID,BattingTeamID,ballNumber,currentOver,ballOutcome,BatsmanID,BowlerID,Inning) VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sqlQuery,matchID,batTeamID,ballNumber,currentOver,ballOutcome,batsmanID,bowlerID,inning);
    }

    public List<Map<String, Object>> getBallInfo(int matchID, String innings, double overNo){
        String sqlQuery= "select BatsmanID, BowlerID, ballOutcome FROM PerBallDetails WHERE matchID = ? AND Inning = ? AND ballNumber = ? ";
        return jdbcTemplate.queryForList(sqlQuery,matchID,innings,(int)overNo*6+ (int)(overNo*10)%10 );

    }

    public List<Map<String, Object>> getOverInfo(int matchID, String innings, int overNo){
        String sqlQuery = "select BatsmanID, BowlerID, ballOutcome FROM PerBallDetails WHERE matchID = ? AND Inning = ? AND currentOver = ? ";
        return jdbcTemplate.queryForList(sqlQuery,matchID,innings,overNo);
    }

    public String getLastBallPlayedOutcome(int matchID, int BatTeamID){
        String sqlQuery = "SELECT ballOutcome FROM PerBallDetails WHERE matchID = ? AND BattingTeamID = ? ORDER BY ballID DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sqlQuery,String.class,matchID,BatTeamID);

    }

    public int getLastBallNumberPlayed(int matchID, int BatTeamID){
        String sqlQuery = "SELECT ballNumber FROM PerBallDetails WHERE matchID = ? AND BattingTeamID = ? ORDER BY ballID DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sqlQuery,Integer.class,matchID,BatTeamID);

    }

    public List<Map<String, Object>> getOverOutcome(int matchID, String innings, int overNo){
        String sqlQuery = "select ballOutcome FROM PerBallDetails WHERE matchID = ? AND Inning = ? AND currentOver = ? ";
        return jdbcTemplate.queryForList(sqlQuery,matchID,innings,overNo);
    }

    public boolean checkIfMatchFirstBall(int matchID, int teamID, String inning){
        Integer check = null;
        try {
            String sqlQuery = "SELECT Count(ballID) FROM PerBallDetails WHERE matchID = ? AND BattingTeamID = ? AND inning = ? ";
            check = jdbcTemplate.queryForObject(sqlQuery,Integer.class,matchID,teamID,inning);

        } catch (EmptyResultDataAccessException e) {
           // e.printStackTrace();
            return false;
        }
        return check != null && check > 0;
    }

}
