package com.tekion.game.service;

import com.tekion.game.DTO.PlayersDTO;
import com.tekion.game.Repository.PlayerRepository;
import com.tekion.game.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> playerRequiredDetails(int matchID, int playerID){
        return playerRepository.getPlayerDetails(playerID,matchID);
    }

    public String playerName(int playerID){
        return playerRepository.getPlayerName(playerID);
    }

    public List<PlayersDTO> allPlayersTeam(int teamID){
        return playerRepository.getAllPlayerForTeam(teamID);
    }

    public List<PlayersDTO> allPlayerInMatch(int matchID, int teamID){
        return playerRepository.getAllPlayerInMatch(matchID,teamID);
    }
}
