package com.tekion.game.Repository;

import com.tekion.game.dbconnector.MySQLConnector;
import com.tekion.game.models.Player;
import com.tekion.game.util.MatchUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;


@Repository
public class PlayerRepository {
    Connection conn;

    public PlayerRepository() throws SQLException, ClassNotFoundException {
        conn = MySQLConnector.getConnection();
    }

    public Boolean checkIfPlayerExists(int teamID , String playerName){
        try {
            String sqlQuery = "SELECT playerId , teamId , playerName FROM Players WHERE teamId = ? AND playerName = ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1 , teamID);
            statement.setString(2 , playerName);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setDataPlayerDB(int teamID, String playerName) throws SQLException {
        if (!checkIfPlayerExists(teamID, playerName)) {
            //System.out.println("entered the if statement.\n");
            String sqlQuery = "Insert INTO Players (TeamID, PlayerName) Values (?,?)";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, teamID);
            statement.setString(2, playerName);
            statement.execute();
        }
    }

    public int getPlayerIdByTeamIdAndPlayerName(int teamID , String playerName) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT playerId FROM Players WHERE teamId = ? AND playerName = ? ");
        statement.setInt(1 , teamID);
        statement.setString(2 , playerName);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public void setDataPlayerMatchDetailsDB(int playerID, int matchID, Player player) throws SQLException {
        String sqlQuery = "INSERT INTO PlayersMatchDetails (playerID,matchID,runsScored,ballsPlayed,numberOf4s,numberOf6s,wicketTakenBy,oversBowled,wicketsTaken,noBallsBowled,wideBallsBowled,runsGiven,DidBat,DidBall) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,playerID);
        statement.setInt(2,matchID);
        statement.setInt(3,player.getRunsScored());
        statement.setInt(4,player.TotalBallsPlayed());
        statement.setInt(5,player.getNumberOf4s());
        statement.setInt(6,player.getNumberOf6s());
        statement.setString(7,player.getWicketTakenBy());
        statement.setDouble(8, MatchUtils.oversBowled(player.getBallsBowled()));
        statement.setInt(9,player.getWicketsTaken());
        statement.setInt(10,player.getNoBallsBowled());
        statement.setInt(11,player.getWideBallsBowled());
        statement.setInt(12,player.getRunsGiven());
        statement.setString(13,player.getDidBat());
        statement.setString(14,player.getDidBall());
        statement.execute();
    }

    public String getPlayerDetails(int playerID,int matchID) throws SQLException {
        String sqlQuery = "SELECT * FROM PlayersMatchDetails WHERE playerID = ? AND matchID = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,playerID);
        statement.setInt(2,matchID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return "Runs Scored: "+rs.getInt("runsScored")+"\n"+"Balls Played: "+rs.getInt("ballsPlayed")+"\n"+"Wicket Taken By: "+rs.getString("wicketTakenBy")+"\n"+"Overs Bowled: "+rs.getDouble("oversBowled")+"\n"+"Wicket Taken: "+rs.getInt("wicketsTaken")+"\n"+"Runs Given: "+rs.getInt("runsGiven")+"\n";
    }

    public String getPlayerName(int playerID) throws SQLException {
        String sqlQuery = "SELECT playerName FROM Players WHERE playerId = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,playerID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getString("playerName");
    }

    public ArrayList<ArrayList<String>> getAllPlayerForTeam(int teamID) throws SQLException {
        ArrayList<ArrayList<String>> allPlayers = new ArrayList<>();
        String sqlQuery = "select playerId, playerName from Players where teamId = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,teamID);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            ArrayList<String> players = new ArrayList<>();
            players.add("Player ID: "+rs.getInt("playerID"));
            players.add("Player Name: "+rs.getString("playerName"));
            allPlayers.add(players);
        }
        return allPlayers;
    }

    public ArrayList<ArrayList<String>> getAllPlayerInMatch(int matchID, int teamID) throws SQLException {
        ArrayList<ArrayList<String>> allPlayers = new ArrayList<>();
        String sqlQuery = "select DISTINCT Players.playerID,Players.playerName from Players INNER JOIN PlayersMatchDetails ON Players.playerID = PlayersMatchDetails.playerID where Players.teamId = ? AND PlayersMatchDetails.matchID = ?";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,teamID);
        statement.setInt(2,matchID);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            ArrayList<String> players = new ArrayList<>();
            players.add("Player ID: "+rs.getInt("playerID"));
            players.add("Player Name: "+rs.getString("playerName"));
            allPlayers.add(players);
        }
        return allPlayers;
    }
}
