package com.tekion.game.controller;

import com.tekion.game.DTO.PlayersDTO;
import com.tekion.game.models.Player;
import com.tekion.game.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Player")
public class PlayerController {

    @Autowired
    private PlayerServiceImpl playerService;

    //player details for given match.
    @GetMapping("/Details/{matchID}/{playerID}")
    public ResponseEntity<List<Player>> getPlayerRequiredDetails(@PathVariable int matchID, @PathVariable int playerID){
        List<Player> players = playerService.playerRequiredDetails(playerID,matchID);
        return ResponseEntity.ok(players);
    }

    // get player Name by ID.
    @GetMapping("/Name/{playerID}")
    public ResponseEntity<String> getPlayerName(@PathVariable  int playerID){
        return ResponseEntity.ok(playerService.playerName(playerID));
    }

    //get all players for the team who ever played active/inactive.
    @GetMapping("/AllWhoPlayedForTeam/{teamID}")
    public ResponseEntity<List<PlayersDTO>> getALLPlayersTeam(@PathVariable int teamID){
        List<PlayersDTO> allPlayers = playerService.allPlayersTeam(teamID);
        return ResponseEntity.ok(allPlayers);
    }

    //get all players played in certain match for certain team
    @GetMapping("/playedMatch/{matchID}/{teamID}")
    public ResponseEntity<List<PlayersDTO>> getPlayersForMatch(@PathVariable int matchID,@PathVariable int teamID){
        List<PlayersDTO> allPlayers = playerService.allPlayerInMatch(matchID,teamID);
        return ResponseEntity.ok(allPlayers);
    }

}
