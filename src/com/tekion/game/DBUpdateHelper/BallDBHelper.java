package com.tekion.game.DBUpdateHelper;

import com.tekion.game.bean.Matches;
import com.tekion.game.dbconnector.MySQLConnector;

import java.sql.*;

public class BallDBHelper {
    Connection conn = MySQLConnector.getConnection();

    public BallDBHelper() throws SQLException, ClassNotFoundException {
    }

    public void setBallDetailsDB(Matches match, int batTeamID ,int ballNumber, int currentOver, String ballOutcome, int batsmanID, int bowlerID) throws SQLException {
        String sqlQuery = "Insert into PerBallDetails(matchID,BattingTeamID,ballNumber,currentOver,ballOutcome,BatsmanID,BowlerID) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,match.getMatchID());
        statement.setInt(2,batTeamID);
        statement.setInt(3,ballNumber);
        statement.setInt(4,currentOver);
        statement.setString(5,ballOutcome);
        statement.setInt(6,batsmanID);
        statement.setInt(7,bowlerID);
        statement.execute();
    }

}
