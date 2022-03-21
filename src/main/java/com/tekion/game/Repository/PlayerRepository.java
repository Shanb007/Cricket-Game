package com.tekion.game.Repository;

import com.tekion.game.DTO.BatsmanDTO;
import com.tekion.game.DTO.BowlerDTO;
import com.tekion.game.DTO.PlayersDTO;
import com.tekion.game.models.Player;
import com.tekion.game.util.MatchUtils;
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
public class PlayerRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public PlayerRepository(){
    }

    public Boolean checkIfPlayerExists(int teamID , String playerName){
        Integer check = null;
        try {
            String sqlQuery = "SELECT playerId FROM Players WHERE teamId = ? AND playerName = ?";
            check = jdbcTemplate.queryForObject(sqlQuery,Integer.class,teamID,playerName);
        } catch (EmptyResultDataAccessException e) {
           // e.printStackTrace();
            return false;
        }
        return check != null && check > 0;
    }

    public void setDataPlayerDB(int teamID, String playerName){
        if (!checkIfPlayerExists(teamID, playerName)) {
            //System.out.println("entered the if statement.\n");
            String sqlQuery = "Insert INTO Players (TeamID, PlayerName) Values (?,?)";
            jdbcTemplate.update(sqlQuery,teamID,playerName);
        }
    }

    public int getPlayerIdByTeamIdAndPlayerName(int teamID , String playerName){
        String sqlQuery = "SELECT playerId FROM Players WHERE teamId = ? AND playerName = ? ";
        return jdbcTemplate.queryForObject(sqlQuery,Integer.class,new Object[]{teamID,playerName});
    }

    private boolean checkIfPlayerAlreadyPlayingTheMatch(int matchID, int teamID, int playerID){
        Integer check = null;
        try {
            String sqlQuery = "SELECT playerId FROM PlayersMatchDetails WHERE matchID = ? AND teamId = ? AND playerID = ?";
            check = jdbcTemplate.queryForObject(sqlQuery,Integer.class,matchID,teamID,playerID);
        } catch (EmptyResultDataAccessException e) {
           // e.printStackTrace();
            return false;
        }
        return check != null && check > 0;
    }


    public void setDataPlayerMatchDetailsDB(int playerID, int teamID , int matchID, Player player, String role){
        if(!checkIfPlayerAlreadyPlayingTheMatch(matchID,teamID,playerID)) {
            String sqlQuery = "INSERT INTO PlayersMatchDetails (playerID,matchID,teamID,runsScored,ballsPlayed,numberOf4s,numberOf6s,wicketTakenBy,oversBowled,wicketsTaken,noBallsBowled,wideBallsBowled,runsGiven,DidBat,DidBall) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sqlQuery,playerID,matchID,teamID,player.getRunsScored(),player.TotalBallsPlayed(),player.getNumberOf4s(),player.getNumberOf6s(),player.getWicketTakenBy(),MatchUtils.oversBowled(player.getBallsBowled()),player.getWicketsTaken(),player.getNoBallsBowled(),player.getWideBallsBowled(),player.getRunsGiven(),player.getDidBat(),player.getDidBall());
        }
        else if (role.equals("Bat")){
            String sqlQuery = "UPDATE PlayersMatchDetails SET DidBat = ? WHERE matchID = ? AND playerID = ? AND teamID = ? ";
            jdbcTemplate.update(sqlQuery,player.getDidBat(),matchID,playerID,teamID);
        }
        else if(role.equals("Ball")){
            System.out.println("entered to ball");
            String sqlQuery = "UPDATE PlayersMatchDetails SET DidBall = ? WHERE matchID = ? AND playerID = ? AND teamID = ? ";
            jdbcTemplate.update(sqlQuery,player.getDidBall(),matchID,playerID,teamID);
        }
    }

    public List<Player> getPlayerDetails(int playerID,int matchID){
        String sqlQuery = "SELECT * FROM PlayersMatchDetails WHERE playerID = ? AND matchID = ? ";
        RowMapper<Player> playerDetails = new BeanPropertyRowMapper<>(Player.class);
        return jdbcTemplate.query(sqlQuery,playerDetails,playerID,matchID);
    }

    public String getPlayerName(int playerID){
        String sqlQuery = "SELECT playerName FROM Players WHERE playerId = ? ";
        return jdbcTemplate.queryForObject(sqlQuery,String.class,playerID);
    }

    public List<PlayersDTO> getAllPlayerForTeam(int teamID){
        RowMapper<PlayersDTO> allPlayers = new BeanPropertyRowMapper<>(PlayersDTO.class);
        String sqlQuery = "select playerId, playerName from Players where teamId = ? ";
        return jdbcTemplate.query(sqlQuery,allPlayers,teamID);
    }

    public List<PlayersDTO> getAllPlayerInMatch(int matchID, int teamID){
        RowMapper<PlayersDTO> allPlayers = new BeanPropertyRowMapper<>(PlayersDTO.class);
        String sqlQuery = "select DISTINCT Players.playerID,Players.playerName from Players INNER JOIN PlayersMatchDetails ON Players.playerID = PlayersMatchDetails.playerID where Players.teamId = ? AND PlayersMatchDetails.matchID = ?";
        return jdbcTemplate.query(sqlQuery,allPlayers,teamID,matchID);
    }

    public boolean checkIfBowlerAlreadyInMatch(int playerID, int matchID){
        Integer check = null;
        try {
            String sqlQuery = "Select * from PlayersMatchDetails where playerID=? and matchID=? and DidBall=?";
            check = jdbcTemplate.queryForObject(sqlQuery,Integer.class,playerID,matchID,"Y");
        }
        catch (EmptyResultDataAccessException e) {
          //  e.printStackTrace();
            return false;
        }
        return check!=null && check>0;
    }

    public Player getNextOverBowlerRequested(int matchID, int teamID, String playerName){
        RowMapper<BowlerDTO> bowler = new BeanPropertyRowMapper<>(BowlerDTO.class);
        int playerID = getPlayerIdByTeamIdAndPlayerName(teamID,playerName);
       Player currentBowlerRequested = new Player(playerName);
        String DidBall = "Y";
        String sqlQuery = "SELECT * FROM PlayersMatchDetails where playerID=? and matchID=? and DidBall=? ";
       BowlerDTO currentBowler = jdbcTemplate.query(sqlQuery,bowler, playerID,matchID,DidBall).get(0);
        currentBowlerRequested.setRunsGiven(currentBowler.getRunsGiven());
        currentBowlerRequested.setOversBowled(currentBowler.getOversBowled());
        currentBowlerRequested.setDidBall(currentBowler.getDidBall());
        currentBowlerRequested.setNoBallsBowled(currentBowler.getNoBallsBowled());
        currentBowlerRequested.setPlayerID(currentBowler.getPlayerID());
        currentBowlerRequested.setWicketsTaken(currentBowler.getWicketsTaken());
        currentBowlerRequested.setWideBallsBowled(currentBowler.getWideBallsBowled());
        return currentBowlerRequested;
    }

    public Player getNextOverBowlerRequested(int matchID, int playerID){
       RowMapper<BowlerDTO> bowler = new BeanPropertyRowMapper<>(BowlerDTO.class);
       Player currentBowlerRequested = new Player(getPlayerName(playerID));
        String sqlQuery = "SELECT * FROM PlayersMatchDetails WHERE playerID=? and matchID=? and DidBall=? ";
        String DidBall = "Y";
        BowlerDTO currentBowler = jdbcTemplate.query(sqlQuery,bowler, playerID,matchID,DidBall).get(0);
        currentBowlerRequested.setRunsGiven(currentBowler.getRunsGiven());
        currentBowlerRequested.setOversBowled(currentBowler.getOversBowled());
        currentBowlerRequested.setDidBall(currentBowler.getDidBall());
        currentBowlerRequested.setNoBallsBowled(currentBowler.getNoBallsBowled());
        currentBowlerRequested.setPlayerID(currentBowler.getPlayerID());
        currentBowlerRequested.setWicketsTaken(currentBowler.getWicketsTaken());
        currentBowlerRequested.setWideBallsBowled(currentBowler.getWideBallsBowled());
        return currentBowlerRequested;
    }

    public List<Player> getBatsmanOnField(int matchID, int teamID){
        List<Player> batsmanOnField = new ArrayList<>();

        RowMapper<BatsmanDTO> batmanOnField = new BeanPropertyRowMapper<>(BatsmanDTO.class);
        String wicketTakenBy= "-";
        String DidBat = "Y";
        String sqlQuery = "SELECT * FROM PlayersMatchDetails where matchID = ? and teamID = ? AND wicketTakenBy = ? AND DidBat = ? ";
        List<BatsmanDTO> batDTO =  jdbcTemplate.query(sqlQuery,batmanOnField,matchID,teamID,wicketTakenBy,DidBat);
        for (BatsmanDTO value: batDTO){
            Player player = new Player(getPlayerName(value.getPlayerID()));
            player.setPlayerID(value.getPlayerID());
            player.setRunsScored(value.getRunsScored());
            player.setBallsPlayed(value.getBallsPlayed());
            player.setNumberOf4s(value.getNumberOf4s());
            player.setNumberOf6s(value.getNumberOf6s());
            batsmanOnField.add(player);
        }
        return batsmanOnField;
    }

    public void updateBowlerStatsInDB(int matchID, int teamID, Player bowler){
        String sqlQuery = "UPDATE PlayersMatchDetails SET oversBowled = ? , wicketsTaken = ? , noBallsBowled = ? , wideBallsBowled = ? , runsGiven = ? WHERE playerID = ? AND matchID = ? AND teamID = ? ";
        jdbcTemplate.update(sqlQuery,MatchUtils.oversBowled(bowler.getBallsBowled()),bowler.getWicketsTaken(),bowler.getNoBallsBowled(),bowler.getWideBallsBowled(),bowler.getRunsGiven(),bowler.getPlayerID(),matchID,teamID);
    }

    public void updateOnFieldBatsmanStatsIDB(int matchID, int teamID, List<Player> onFieldBat){
        for (Player value: onFieldBat){
            String sqlQuery = "UPDATE PlayersMatchDetails SET runsScored = ? , ballsPlayed = ? , numberOf4s = ? , numberOf6s = ? , wicketTakenBy = ?, DidBat = ? WHERE playerID = ? AND matchID = ? AND teamID = ? ";
            jdbcTemplate.update(sqlQuery,value.getRunsScored(),value.TotalBallsPlayed(),value.getNumberOf4s(),value.getNumberOf6s(),value.getWicketTakenBy(),value.getDidBat(),value.getPlayerID(),matchID,teamID);
        }
    }
}
