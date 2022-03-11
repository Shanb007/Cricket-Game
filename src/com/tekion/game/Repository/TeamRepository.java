package com.tekion.game.Repository;

import com.tekion.game.dbconnector.MySQLConnector;
import com.tekion.game.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;


@Repository
public class TeamRepository {
    Connection conn;

    public TeamRepository() throws SQLException, ClassNotFoundException {
        conn = MySQLConnector.getConnection();
    }

    public boolean checkIfTeamExists(String teamName){
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT teamId , teamName FROM Teams WHERE teamName = ?");
            statement.setString(1 , teamName);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setDataTeamDB(String teamsName) throws SQLException {
        if (!checkIfTeamExists(teamsName)) {
            //System.out.println("entered the if statement.\n");
            String sqlQuery = "Insert INTO Teams (TeamName) Values (?)";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, teamsName);
            statement.execute();
        }
    }

    public int getIdByTeamName(String teamName) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT teamId from Teams WHERE teamName = ? ");
        statement.setString(1, teamName);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public String getTeamName(int teamID) throws SQLException {
        String sqlQuery = "select teamName from Teams where teamID = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,teamID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getString("teamName");
    }

    public ArrayList<String> getAllTeams() throws SQLException {
        ArrayList<String> allTeams = new ArrayList<>();
        String sqlQuery = "select teamName from Teams";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            allTeams.add(rs.getString("teamName"));
        }
        return allTeams;
    }

    public ArrayList<String> getTeamsInMatch(int matchID) throws SQLException {
        ArrayList<String> teams = new ArrayList<>();
        String sqlQuery1 = "select Teams.teamName FROM Teams inner join Matches ON Teams.TeamID = Matches.TeamA_ID WHERE Matches.MatchID = ? ";
        String sqlQuery2 = "select Teams.teamName FROM Teams inner join Matches ON Teams.TeamID = Matches.TeamB_ID WHERE Matches.MatchID = ? ";
        PreparedStatement statement1 = conn.prepareStatement(sqlQuery1);
        statement1.setInt(1,matchID);
        ResultSet rs1 = statement1.executeQuery();
        rs1.next();
        teams.add(rs1.getString("teamName"));
        PreparedStatement statement2 = conn.prepareStatement(sqlQuery2);
        statement2.setInt(1,matchID);
        ResultSet rs2  = statement2.executeQuery();
        rs2.next();
        teams.add(rs2.getString("teamName"));
        return teams;
    }
    public void setScoreBoard(int matchID, int teamID) throws SQLException {
        String sqlQuery = "INSERT INTO TeamScorecard(matchID, teamID,TeamScore, totalWicketsGone, TotalNoBalls, TotalWideBalls, currentOver,TotalExtras,ExtraRuns) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        statement.setInt(2,teamID);
        statement.setInt(3,0);
        statement.setInt(4,0);
        statement.setInt(5,0);
        statement.setInt(6,0);
        statement.setInt(7,0);
        statement.setInt(8,0);
        statement.setInt(9,0);
        statement.execute();
    }

    public Team getTeamScorecardRequested(int matchID, int teamID) throws SQLException {
        Team BatTeam = new Team();
        String sqlQuery = "SELECT * FROM TeamScorecard WHERE matchID = ? AND teamID = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,matchID);
        statement.setInt(2,teamID);
        ResultSet rs = statement.executeQuery();
        rs.next();
        BatTeam.setTeamID(teamID);
        BatTeam.setTeamScore(rs.getInt("TeamScore"));
        BatTeam.setNoBall(rs.getInt("TotalNoBalls"));
        BatTeam.setWideBall(rs.getInt("TotalWideBalls"));
        BatTeam.setExtraScore(rs.getInt("ExtraRuns"));
        BatTeam.setTeamName(getTeamName(teamID));
        BatTeam.setCurrentOver(rs.getInt("currentOver"));
        BatTeam.setWicketsFallen(rs.getInt("totalWicketsGone"));
        return BatTeam;
    }

    public void updateTeamScorecardAfterOver(int matchID, int teamID, Team BatTeam) throws SQLException {
        String sqlQuery = "UPDATE TeamScorecard SET TeamScore = ? , totalWicketsGone = ? , TotalNoBalls = ? , TotalWideBalls = ? , currentOver = ? , TotalExtras  = ? , ExtraRuns = ? WHERE matchID = ? AND teamID = ? ";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,BatTeam.TeamScore());
        statement.setInt(2,BatTeam.totalWicketsGone());
        statement.setInt(3,BatTeam.TotalNoBalls());
        statement.setInt(4,BatTeam.TotalWideBalls());
        statement.setInt(5,BatTeam.currentOver());
        statement.setInt(6,BatTeam.TotalExtras());
        statement.setInt(7,BatTeam.getExtraScore());
        statement.setInt(8,matchID);
        statement.setInt(9,teamID);
        statement.execute();
    }
}

