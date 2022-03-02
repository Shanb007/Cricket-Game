package com.tekion.game.DBUpdateHelper;

import com.tekion.game.bean.Matches;
import com.tekion.game.dbconnector.MySQLConnector;

import java.sql.*;

public class MatchDBHelper {
    Connection conn = MySQLConnector.getConnection();

    public MatchDBHelper() throws SQLException, ClassNotFoundException {
    }

    public void setMatchDBMatchDetails(Matches match) throws SQLException {
        String sqlQuery = "INSERT INTO Matches(TeamA_ID, TeamB_ID, TotalOvers) VALUES (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        //statement.setInt(1,match.getMatchID());
        statement.setInt(1,match.getTeamA_ID());
        statement.setInt(2, match.getTeamB_ID());
        statement.setDouble(3,match.getTotalOvers());
        statement.execute();
    }

    public void setDBMatchResults(Matches match) throws SQLException {
        String sqlQuery = "INSERT INTO MatchResults(MatchID, tossWinner, tossWinnerChoice, Match_Winner) VALUES (?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,match.getMatchID());
        statement.setString(2, match.getTossWinner());
        statement.setString(3,match.getTossWinnerChoice());
        statement.setString(4,match.getMatch_Winner());
        statement.execute();
    }
    //matchID retrieve by ------
    public int getMatchIDbyTeamsID(int teamA_id, int teamB_id) throws SQLException {
        String sqlQuery = "Select MatchID FROM Matches WHERE TeamA_ID = (?) AND TeamB_ID = (?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,teamA_id);
        statement.setInt(2,teamB_id);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}

// create different mat details... result set different and details different.