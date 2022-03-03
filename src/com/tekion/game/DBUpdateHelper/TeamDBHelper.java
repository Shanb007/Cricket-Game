package com.tekion.game.DBUpdateHelper;

import com.tekion.game.dbconnector.MySQLConnector;
import java.sql.*;

public class TeamDBHelper {
    Connection conn;

    public TeamDBHelper() throws SQLException, ClassNotFoundException {
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
}

