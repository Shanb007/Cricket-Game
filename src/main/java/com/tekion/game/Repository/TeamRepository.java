package com.tekion.game.Repository;

import com.tekion.game.DTO.TeamScoreCardDTO;
import com.tekion.game.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class TeamRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public TeamRepository(){
    }

    public boolean checkIfTeamExists(String teamName){
        Integer check = null;
        try {
            String sqlQuery = "SELECT teamId FROM Teams WHERE teamName = ?";
            check = jdbcTemplate.queryForObject(sqlQuery,Integer.class,teamName);
        } catch (EmptyResultDataAccessException e) {
            //e.printStackTrace();
            return false;
        }
        return check != null && check > 0;
    }

    public void setDataTeamDB(String teamsName){
        if (!checkIfTeamExists(teamsName)) {
            String sqlQuery = "Insert INTO Teams (TeamName) Values (?)";
            jdbcTemplate.update(sqlQuery,teamsName);
        }
    }

    public int getIdByTeamName(String teamName){
        String sql = "SELECT teamId from Teams WHERE teamName = ? ";
        return jdbcTemplate.queryForObject(sql,Integer.class,teamName);
    }

    public String getTeamName(int teamID){
        String sqlQuery = "select teamName from Teams where teamID = ? ";
        return jdbcTemplate.queryForObject(sqlQuery,String.class,teamID);
    }

    public List<Map<String, Object>> getAllTeams() {
        String sqlQuery = "select teamName from Teams";
        return jdbcTemplate.queryForList(sqlQuery);
    }

    public ArrayList<String> getTeamsInMatch(int matchID){
        ArrayList<String> teams = new ArrayList<>();
        String sqlQueryForTeam1 = "select Teams.teamName FROM Teams inner join Matches ON Teams.TeamID = Matches.TeamA_ID WHERE Matches.MatchID = ? ";
        String sqlQueryForTeam2 = "select Teams.teamName FROM Teams inner join Matches ON Teams.TeamID = Matches.TeamB_ID WHERE Matches.MatchID = ? ";
        String team1 = jdbcTemplate.queryForObject(sqlQueryForTeam1,String.class,matchID);
        teams.add(team1);
        String team2 = jdbcTemplate.queryForObject(sqlQueryForTeam2,String.class,matchID);
        teams.add(team2);

        return teams;
    }
    public void setScoreBoard(int matchID, int teamID){
        String sqlQuery = "INSERT INTO TeamScorecard(matchID, teamID,TeamScore, totalWicketsGone, TotalNoBalls, TotalWideBalls, currentOver,TotalExtras,ExtraRuns) VALUES (?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sqlQuery,matchID,teamID,0,0,0,0,0,0,0);
    }

    public Team getTeamScorecardRequested(int matchID, int teamID){
        RowMapper<TeamScoreCardDTO> teamRowMapper = new BeanPropertyRowMapper<>(TeamScoreCardDTO.class);
        Team BatTeam = new Team();
        String sqlQuery = "SELECT * FROM TeamScorecard WHERE matchID = ? AND teamID = ? ";
        TeamScoreCardDTO teamScoreCardDTO =  jdbcTemplate.query(sqlQuery,teamRowMapper,matchID,teamID).get(0);
        BatTeam.setTeamID(teamID);
        BatTeam.setTeamScore(teamScoreCardDTO.getTeamScore());
        BatTeam.setNoBall(teamScoreCardDTO.getTotalNoBalls());
        BatTeam.setWideBall(teamScoreCardDTO.getTotalWideBalls());
        BatTeam.setExtraScore(teamScoreCardDTO.getExtraRuns());
        BatTeam.setTeamName(getTeamName(teamID));
        BatTeam.setCurrentOver(teamScoreCardDTO.getCurrentOver());
        BatTeam.setWicketsFallen(teamScoreCardDTO.getTotalWicketsGone());
        return BatTeam;
    }

    public void updateTeamScorecardAfterOver(int matchID, int teamID, Team BatTeam){
        String sqlQuery = "UPDATE TeamScorecard SET TeamScore = ? , totalWicketsGone = ? , TotalNoBalls = ? , TotalWideBalls = ? , currentOver = ? , TotalExtras  = ? , ExtraRuns = ? WHERE matchID = ? AND teamID = ? ";
        jdbcTemplate.update(sqlQuery,BatTeam.TeamScore(),BatTeam.totalWicketsGone(),BatTeam.TotalNoBalls(),BatTeam.TotalWideBalls(),BatTeam.currentOver(),BatTeam.TotalExtras(),BatTeam.getExtraScore(),matchID,teamID);
    }
}

