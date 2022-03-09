package com.tekion.game.Repository;

import com.tekion.game.bean.Matches;
import com.tekion.game.dbconnector.MySQLConnector;
import org.springframework.stereotype.Repository;

import java.sql.*;


@Repository
public class ScoreBoardRepository {
    Connection conn = MySQLConnector.getConnection();

    public ScoreBoardRepository() throws SQLException, ClassNotFoundException {
    }

    public void setScoreBoardDBDetails(Matches match, String FirstInningTeam, String SecondInningTeam) throws SQLException {
        String sqlQuery = "Insert INTO ScoreBoard(matchID,FirstInningTeam,FirstInningTeam_Score,FirstInningTeam_WicketsFallen,SecondInningTeam,SecondInningTeam_Score,SecondInningTeam_WicketsFallen) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        statement.setInt(1,match.getMatchID());
        statement.setString(2,FirstInningTeam);
        statement.setInt(3,match.getTeamA_Score());
        statement.setInt(4,match.getTeamA_WicketsFallen());
        statement.setString(5,SecondInningTeam);
        statement.setInt(6,match.getTeamB_Score());
        statement.setInt(7,match.getTeamB_WicketsFallen());
        statement.execute();
    }
}
