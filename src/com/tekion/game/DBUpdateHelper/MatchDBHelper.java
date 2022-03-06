package com.tekion.game.DBUpdateHelper;

import com.tekion.game.bean.Matches;
import com.tekion.game.dbconnector.MySQLConnector;

import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<String> getMatchDetail(int matchID) throws SQLException {
        ArrayList<String> matchDetail = new ArrayList<>();
        String sqlQuery = "select Matches.MatchID, Matches.TeamA_ID, Matches.TeamB_ID, Matches.TotalOvers, MatchResults.tossWinner, MatchResults.tossWinnerChoice, MatchResults.Match_Winner from Matches INNER JOIN MatchResults ON Matches.MatchID = MatchResults.MatchID WHERE Matches.MatchID = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        matchDetail.add("Match Id: "+rs.getInt("Matches.MatchID"));
        matchDetail.add("Team A Id: "+rs.getInt("Matches.TeamA_ID"));
        matchDetail.add("Team B Id: "+rs.getInt("Matches.TeamB_ID"));
        matchDetail.add("Total Overs: "+rs.getInt("Matches.TotalOvers"));
        matchDetail.add("Toss Winner: "+rs.getString("MatchResults.tossWinner"));
        matchDetail.add("Toss Winner Choice: "+rs.getString("MatchResults.tossWinnerChoice"));
        matchDetail.add("Match Winner: "+rs.getString("MatchResults.Match_Winner"));
        return matchDetail;
    }
}
