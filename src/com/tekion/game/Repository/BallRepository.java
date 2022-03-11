package com.tekion.game.Repository;

import com.tekion.game.bean.Matches;
import com.tekion.game.dbconnector.MySQLConnector;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;


@Repository
public class BallRepository {
    Connection conn = MySQLConnector.getConnection();

    public BallRepository() throws SQLException, ClassNotFoundException {
    }

    public void setBallDetailsDB(int matchID, int batTeamID ,int ballNumber, int currentOver, String ballOutcome, int batsmanID, int bowlerID, String inning) throws SQLException {
        String sqlQuery = "Insert into PerBallDetails(matchID,BattingTeamID,ballNumber,currentOver,ballOutcome,BatsmanID,BowlerID,Inning) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        statement.setInt(2,batTeamID);
        statement.setInt(3,ballNumber);
        statement.setInt(4,currentOver);
        statement.setString(5,ballOutcome);
        statement.setInt(6,batsmanID);
        statement.setInt(7,bowlerID);
        statement.setString(8,inning);
        statement.execute();
    }

    public ArrayList<String> getBallInfo(int matchID, String innings,double overNo) throws SQLException {
        ArrayList<String> ballInfo = new ArrayList<>();
        String sqlQuery= "select BatsmanID, BowlerID, ballOutcome FROM PerBallDetails WHERE matchID = ? AND Inning = ? AND ballNumber = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        statement.setString(2,innings);
        statement.setInt(3,((int)overNo*6+ (int)(overNo*10)%10 ));
        ResultSet rs = statement.executeQuery();
        rs.next();
        ballInfo.add("Batsman id: "+rs.getInt("BatsmanID"));
        ballInfo.add("Bowler id: "+rs.getInt("BowlerID"));
        ballInfo.add("Ball Outcome: "+rs.getString("ballOutcome"));
        return ballInfo;
    }

    public ArrayList<ArrayList<String>> getOverInfo(int matchID, String innings,int overNo) throws SQLException {
        ArrayList<ArrayList<String>> overInfo = new ArrayList<>();
        String sqlQuery = "select BatsmanID, BowlerID, ballOutcome FROM PerBallDetails WHERE matchID = ? AND Inning = ? AND currentOver = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1, matchID);
        statement.setString(2, innings);
        statement.setInt(3, overNo);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            ArrayList<String> ballInfo = new ArrayList<>();
            ballInfo.add("Batsman id: "+rs.getInt("BatsmanID"));
            ballInfo.add("Bowler id: "+rs.getInt("BowlerID"));
            ballInfo.add("Ball Outcome: "+rs.getString("ballOutcome"));
            overInfo.add(ballInfo);
        }
        return overInfo;
    }

    public String getLastBallPlayedOutcome(int matchID, int BatTeamID) throws SQLException {
        String sqlQuery = "SELECT ballOutcome FROM PerBallDetails WHERE matchID = ? AND BattingTeamID = ? ORDER BY ballID DESC LIMIT 1";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        statement.setInt(2,BatTeamID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getString("ballOutcome");
    }

    public int getLastBallNumberPlayed(int matchID, int BatTeamID) throws SQLException {
        String sqlQuery = "SELECT ballNumber FROM PerBallDetails WHERE matchID = ? AND BattingTeamID = ? ORDER BY ballID DESC LIMIT 1";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        statement.setInt(2,BatTeamID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt("ballNumber");
    }

    public ArrayList<String> getOverOutcomeTillWicketFell(int matchID, int BatTeamID) throws SQLException {
        ArrayList<String> outcome = new ArrayList<>();
        String sqlQuery = "SELECT ballOutcome FROM PerBallDetails WHERE matchID = ? AND BattingTeamID = ? ORDER BY ballID DESC LIMIT ?";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        statement.setInt(2,BatTeamID);
        statement.setInt(3,getLastBallNumberPlayed(matchID,BatTeamID)%6);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            outcome.add(rs.getString("ballOutcome"));
        }
        return outcome;
    }

    public boolean checkIfMatchFirstBall(int matchID, int teamID, String inning){
        try {
            String sqlQuery = "SELECT * FROM PerBallDetails WHERE matchID = ? AND BattingTeamID = ? AND inning = ? ";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, matchID);
            statement.setInt(2,teamID);
            statement.setString(3,inning);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
