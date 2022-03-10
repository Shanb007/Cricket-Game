package com.tekion.game.Repository;

import com.tekion.game.bean.Matches;
import com.tekion.game.dbconnector.MySQLConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;


@Repository
public class MatchRepository {

    private static Connection conn;

    public MatchRepository() throws SQLException, ClassNotFoundException {
        conn = MySQLConnector.getConnection();
    }

    public boolean checkIfMatchExistsOngoing(int teamA_ID, int teamB_ID){
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT MatchID FROM Matches WHERE TeamA_ID = ? AND TeamB_ID = ? AND matchStatus = ? ");
            statement.setInt(1 , teamA_ID);
            statement.setInt(2,teamB_ID);
            statement.setString(3,"ongoing");
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setMatchDBMatchDetails(Matches match) throws SQLException {
        String sqlQuery = "INSERT INTO Matches(TeamA_ID, TeamB_ID, TotalOvers,tossWinner, tossWinnerChoice, matchStatus) VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        //statement.setInt(1,match.getMatchID());
        statement.setInt(1,match.getTeamA_ID());
        statement.setInt(2, match.getTeamB_ID());
        statement.setDouble(3,match.getTotalOvers());
        statement.setString(4, match.getTossWinner());
        statement.setString(5,match.getTossWinnerChoice());
        statement.setString(6, "ongoing");
        statement.execute();
    }

    public void setDBMatchResults(Matches match) throws SQLException {
        String sqlQuery = "INSERT INTO MatchResults(MatchID, Match_Winner) VALUES (?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,match.getMatchID());
        statement.setString(4,match.getMatch_Winner());
        statement.execute();
        String sqlQuery1 = "UPDATE Matches SET matchStatus = ? WHERE MatchID = ? ";
        PreparedStatement statement1 = conn.prepareStatement(sqlQuery1);
        statement1.setString(1,"completed");
        statement1.setInt(2,match.getMatchID());
    }
    //matchID retrieve by ------
    public int getMatchIDbyTeamsIDAndStatus(int teamA_id, int teamB_id, String status) throws SQLException {
        String sqlQuery = "Select MatchID FROM Matches WHERE TeamA_ID = (?) AND TeamB_ID = (?) AND matchStatus = (?) ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,teamA_id);
        statement.setInt(2,teamB_id);
        statement.setString(3,status);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public ArrayList<String> getMatchDetail(int matchID) throws SQLException {
        ArrayList<String> matchDetail = new ArrayList<>();
        String sqlQuery = "select Matches.MatchID, Matches.TeamA_ID, Matches.TeamB_ID, Matches.TotalOvers, Matches.tossWinner, Matches.tossWinnerChoice, MatchResults.Match_Winner from Matches INNER JOIN MatchResults ON Matches.MatchID = MatchResults.MatchID WHERE Matches.MatchID = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        matchDetail.add("Match Id: "+rs.getInt("Matches.MatchID"));
        matchDetail.add("Team A Id: "+rs.getInt("Matches.TeamA_ID"));
        matchDetail.add("Team B Id: "+rs.getInt("Matches.TeamB_ID"));
        matchDetail.add("Total Overs: "+rs.getInt("Matches.TotalOvers"));
        matchDetail.add("Toss Winner: "+rs.getString("Matches.tossWinner"));
        matchDetail.add("Toss Winner Choice: "+rs.getString("Matches.tossWinnerChoice"));
        matchDetail.add("Match Winner: "+rs.getString("MatchResults.Match_Winner"));
        return matchDetail;
    }

    public ArrayList<ArrayList<String>> getAllMatches() throws SQLException {
        ArrayList<ArrayList<String>> matches = new ArrayList<>();
        String sqlQuery = "select * from Matches";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            ArrayList<String> eachMatch = new ArrayList<>();
            eachMatch.add("Match Id: "+rs.getInt("MatchID"));
            eachMatch.add("Team A Id: "+rs.getInt("TeamA_ID"));
            eachMatch.add("Team B Id: "+rs.getInt("TeamB_ID"));
            eachMatch.add("Total Overs: "+rs.getDouble("TotalOvers"));
            eachMatch.add("Toss Winner: "+rs.getString("tossWinner"));
            eachMatch.add("Toss Winner Choice: "+rs.getString("tossWinnerChoice"));
            eachMatch.add("Status: "+rs.getString("matchStatus"));
            matches.add(eachMatch);
        }
        return matches;
    }

    public ArrayList<ArrayList<String>> getAllCompletedMatches() throws SQLException {
        ArrayList<ArrayList<String>> matches = new ArrayList<>();
        String sqlQuery = "select Matches.MatchID, Matches.TeamA_ID, Matches.TeamB_ID, Matches.TotalOvers, MatchResults.tossWinner, MatchResults.tossWinnerChoice, MatchResults.Match_Winner from Matches INNER JOIN MatchResults ON Matches.MatchID = MatchResults.MatchID";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            ArrayList<String> eachMatch = new ArrayList<>();
            eachMatch.add("Match Id: "+rs.getInt("Matches.MatchID"));
            eachMatch.add("Team A Id: "+rs.getInt("Matches.TeamA_ID"));
            eachMatch.add("Team B Id: "+rs.getInt("Matches.TeamB_ID"));
            eachMatch.add("Total Overs: "+rs.getInt("Matches.TotalOvers"));
            eachMatch.add("Toss Winner: "+rs.getString("Matches.tossWinner"));
            eachMatch.add("Toss Winner Choice: "+rs.getString("Matches.tossWinnerChoice"));
            eachMatch.add("Match Winner: "+rs.getString("MatchResults.Match_Winner"));
            matches.add(eachMatch);
        }
        return matches;
    }

    public ArrayList<ArrayList<String>> getBattingScoreBoard(int matchID, int teamID) throws SQLException {
        ArrayList<ArrayList<String>> ScoreCard = new ArrayList<>();
        String sqlQuery = "select PlayersMatchDetails.playerID, PlayersMatchDetails.runsScored, PlayersMatchDetails.ballsPlayed,PlayersMatchDetails.numberOf4s, PlayersMatchDetails.numberOf6s,PlayersMatchDetails.wicketTakenBy FROM PlayersMatchDetails inner join Players ON Players.PlayerID = PlayersMatchDetails.playerID WHERE matchID = ? AND teamID = ? AND DidBat = ?";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        statement.setInt(2,teamID);
        statement.setString(3,"Y");
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            ArrayList<String> batsman = new ArrayList<>();
            batsman.add("Batsman ID: "+rs.getInt("playerID"));
            batsman.add("Runs Scored: "+rs.getInt("runsScored"));
            batsman.add("Balls Played: "+rs.getInt("ballsPlayed"));
            batsman.add("4s: "+rs.getInt("numberOf4s"));
            batsman.add("6s: "+rs.getInt("numberOf6s"));
            batsman.add("Wicket Taken By: "+rs.getString("wicketTakenBy"));
            ScoreCard.add(batsman);
        }
        // bowlers of 2nd team.
        String sqlQuery1 = "select PlayersMatchDetails.playerID, PlayersMatchDetails.oversBowled, PlayersMatchDetails.wicketsTaken, PlayersMatchDetails.noBallsBowled, PlayersMatchDetails.wideBallsBowled, PlayersMatchDetails.runsGiven FROM PlayersMatchDetails inner join Players ON Players.PlayerID = PlayersMatchDetails.playerID WHERE matchID = ? AND teamID = ? AND DidBall = ? ";
        PreparedStatement statement1 = conn.prepareStatement(sqlQuery1);
        statement1.setInt(1,matchID);
        statement1.setInt(2,getBowlingTeamIDbyBatTeamAndMatchID(matchID,teamID));
        statement1.setString(3,"Y");
        ResultSet rs1 = statement1.executeQuery();
        while (rs1.next()){
            ArrayList<String> bowler = new ArrayList<>();
            bowler.add("Bowler ID: "+rs1.getInt("playerID"));
            bowler.add("Overs Bowled: "+rs1.getDouble("oversBowled"));
            bowler.add("Wickets Taken: "+rs1.getInt("wicketsTaken"));
            bowler.add("No Balls Bowled: "+rs1.getInt("noBallsBowled"));
            bowler.add("Wide Balls Bowled: "+rs1.getInt("wideBallsBowled"));
            bowler.add("Runs Given: "+rs1.getInt("runsGiven"));
            ScoreCard.add(bowler);
        }

        return ScoreCard;
    }

    private int getBowlingTeamIDbyBatTeamAndMatchID(int matchID,int teamID) throws SQLException {
        String sqlQuery = "select TeamA_ID, TeamB_ID FROM Matches where MatchID = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        if(rs.getInt("TeamA_ID")==teamID){
            return rs.getInt("TeamB_ID");
        }
        return rs.getInt("TeamA_ID");
    }

    public double getOversByMatchID(int matchID) throws SQLException {
        String sqlQuery = "select TotalOvers FROM Matches where MatchID = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getDouble("TotalOvers");
    }

}
