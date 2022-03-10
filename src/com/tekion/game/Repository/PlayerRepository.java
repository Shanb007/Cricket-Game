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

    public boolean checkIfBowlerAlreadyInMatch(int playerID, int matchID){
        try {
            String sqlQuery = "Select * from PlayersMatchDetails where playerID=? and matchID=? and DidBall=?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, playerID);
            statement.setInt(2, matchID);
            statement.setString(3, "Y");
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Player getNextOverBowlerRequested(int matchID, int teamID, String playerName) throws SQLException {
        int playerID = getPlayerIdByTeamIdAndPlayerName(teamID,playerName);
        Player currentBowlerRequested = new Player(playerName);
        String sqlQuery = "SELECT * FROM PlayersMatchDetails playerID=? and matchID=? and DidBall=? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,playerID);
        statement.setInt(2,matchID);
        statement.setString(3,"Y");
        ResultSet rs = statement.executeQuery();
        rs.next();
        currentBowlerRequested.setRunsGiven(rs.getInt("runsGiven"));
        currentBowlerRequested.setOversBowled(rs.getDouble("oversBowled"));
        currentBowlerRequested.setDidBall(rs.getString("DidBall"));
        currentBowlerRequested.setNoBallsBowled(rs.getInt("noBallsBowled"));
        currentBowlerRequested.setPlayerID(rs.getInt("playerID"));
        currentBowlerRequested.setWicketsTaken(rs.getInt("wicketsTaken"));
        currentBowlerRequested.setWideBallsBowled(rs.getInt("wideBallsBowled"));
        return currentBowlerRequested;
    }

    public Player getNextOverBowlerRequested(int matchID, int playerID) throws SQLException {
       // int playerID = getPlayerIdByTeamIdAndPlayerName(teamID,playerName);
        Player currentBowlerRequested = new Player(getPlayerName(playerID));
        String sqlQuery = "SELECT * FROM PlayersMatchDetails playerID=? and matchID=? and DidBall=? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,playerID);
        statement.setInt(2,matchID);
        statement.setString(3,"Y");
        ResultSet rs = statement.executeQuery();
        rs.next();
        currentBowlerRequested.setRunsGiven(rs.getInt("runsGiven"));
        currentBowlerRequested.setOversBowled(rs.getDouble("oversBowled"));
        currentBowlerRequested.setDidBall(rs.getString("DidBall"));
        currentBowlerRequested.setNoBallsBowled(rs.getInt("noBallsBowled"));
        currentBowlerRequested.setPlayerID(rs.getInt("playerID"));
        currentBowlerRequested.setWicketsTaken(rs.getInt("wicketsTaken"));
        currentBowlerRequested.setWideBallsBowled(rs.getInt("wideBallsBowled"));
        return currentBowlerRequested;
    }

    public ArrayList<Player> getBatsmanOnField(int matchID, int teamID) throws SQLException {
        ArrayList<Player> batsmanOnField = new ArrayList<>();
        String sqlQuery = "SELECT * FROM PlayersMatchDetails where matchID = ? and teamID = ? AND wicketTakenBy = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        statement.setInt(2,teamID);
        //statement.setString(3,"Y");
        statement.setString(4,"-");
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            Player player = new Player(getPlayerName(rs.getInt("playerID")));
            player.setPlayerID(rs.getInt("playerID"));
            player.setRunsScored(rs.getInt("runsScored"));
            player.setBallsPlayed(rs.getInt("ballsPlayed"));
            player.setNumberOf4s(rs.getInt("numberOf4s"));
            player.setNumberOf6s(rs.getInt("numberOf6s"));
            batsmanOnField.add(player);
        }
        return batsmanOnField;
    }

    public void updateBowlerStatsInDB(int matchID, int teamID, Player bowler) throws SQLException {
        String sqlQuery = "UPDATE PlayersMatchDetails SET oversBowled = ? , wicketsTaken = ? , noBallsBowled = ? , wideBallsBowled = ? , runsGiven = ? WHERE playerID = ? AND matchID = ? AND teamID = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setDouble(1,MatchUtils.oversBowled(bowler.getBallsBowled()));
        statement.setInt(2,bowler.getWicketsTaken());
        statement.setInt(3, bowler.getNoBallsBowled());
        statement.setInt(4,bowler.getWideBallsBowled());
        statement.setInt(5,bowler.getRunsGiven());
        statement.setInt(6,bowler.getPlayerID());
        statement.setInt(7,matchID);
        statement.setInt(8,teamID);
        statement.execute();
    }

    public void updateOnFieldBatsmanStatsIDB(int matchID, int teamID, ArrayList<Player> onFieldBat) throws SQLException {
        for (Player value: onFieldBat){
            String sqlQuery = "UPDATE PlayersMatchDetails SET runsScored = ? , ballsPlayed = ? , numberOf4s = ? , numberOf6s = ? , wicketTakenBy = ? WHERE playerID = ? AND matchID = ? AND teamID = ? ";
            PreparedStatement statement  = conn.prepareStatement(sqlQuery);
            statement.setInt(1,value.getRunsScored());
            statement.setInt(2,value.TotalBallsPlayed());
            statement.setInt(3,value.getNumberOf4s());
            statement.setInt(4,value.getNumberOf6s());
            statement.setString(5,value.getWicketTakenBy());
            statement.setInt(6,value.getPlayerID());
            statement.setInt(7,matchID);
            statement.setInt(8,teamID);
            statement.execute();
        }
    }
}
