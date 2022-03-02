package com.tekion.game.DBUpdateHelper;

import com.tekion.game.dbconnector.MySQLConnector;
import com.tekion.game.models.Player;
import com.tekion.game.service.ballsToOvers;

import java.sql.*;

public class PlayerDBHelper {
    Connection conn;

    public PlayerDBHelper() throws SQLException, ClassNotFoundException {
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
        String sqlQuery = "INSERT INTO PlayersMatchDetails (playerID,matchID,runsScored,ballsPlayed,numberOf4s,numberOf6s,wicketTakenBy,oversBowled,wicketsTaken,noBallsBowled,wideBallsBowled,runsGiven) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,playerID);
        statement.setInt(2,matchID);
        statement.setInt(3,player.getRunsScored());
        statement.setInt(4,player.TotalBallsPlayed());
        statement.setInt(5,player.getNumberOf4s());
        statement.setInt(6,player.getNumberOf6s());
        statement.setString(7,player.getWicketTakenBy());
        statement.setDouble(8, ballsToOvers.oversBowled(player.getBallsBowled()));
        statement.setInt(9,player.getWicketsTaken());
        statement.setInt(10,player.getNoBallsBowled());
        statement.setInt(11,player.getWideBallsBowled());
        statement.setInt(12,player.getRunsGiven());
        statement.execute();
    }
}
