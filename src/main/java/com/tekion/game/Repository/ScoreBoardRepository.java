package com.tekion.game.Repository;

import com.tekion.game.bean.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;




@Repository
public class ScoreBoardRepository {

    @Autowired
    JdbcTemplate jdbcTemplate ;

    public void setScoreBoardDBDetails(Matches match, String FirstInningTeam, String SecondInningTeam){
        String sqlQuery = "Insert INTO ScoreBoard(matchID,FirstInningTeam,FirstInningTeam_Score,FirstInningTeam_WicketsFallen,SecondInningTeam,SecondInningTeam_Score,SecondInningTeam_WicketsFallen) VALUES (?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(sqlQuery,match.getMatchID(),FirstInningTeam,match.getTeamA_Score(),match.getTeamA_WicketsFallen(),SecondInningTeam,match.getTeamB_Score(),match.getTeamB_WicketsFallen());
        if(status != 0){
            System.out.println("ScoreBoard data Inserted.");
        }else{
            System.out.println("Failed to insert the data.");
        }
    }
}
