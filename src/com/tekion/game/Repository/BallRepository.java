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

    public void setBallDetailsDB(Matches match, int batTeamID ,int ballNumber, int currentOver, String ballOutcome, int batsmanID, int bowlerID, String inning) throws SQLException {
        String sqlQuery = "Insert into PerBallDetails(matchID,BattingTeamID,ballNumber,currentOver,ballOutcome,BatsmanID,BowlerID,Inning) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,match.getMatchID());
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

}
