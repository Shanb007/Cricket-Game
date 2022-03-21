package com.tekion.game.service;

import com.tekion.game.DTO.PlayersDTO;
import com.tekion.game.models.Player;

import java.sql.SQLException;
import java.util.List;

public interface PlayerService {
    List<Player> playerRequiredDetails(int matchID, int playerID) throws SQLException;
    String playerName(int playerID) throws SQLException;
    List<PlayersDTO> allPlayersTeam(int teamID) throws SQLException;
    List<PlayersDTO> allPlayerInMatch(int matchID, int teamID) throws SQLException;
}
