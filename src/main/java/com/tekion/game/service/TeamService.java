package com.tekion.game.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TeamService {

    String teamName(int teamID) throws SQLException;
    List<Map<String, Object>> allTeams() throws SQLException;
    List<String> teamPlayingMatch(int matchID) throws SQLException, ClassNotFoundException;
}
