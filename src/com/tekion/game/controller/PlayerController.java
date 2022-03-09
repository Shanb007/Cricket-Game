package com.tekion.game.controller;

import com.tekion.game.Repository.PlayerRepository;
import com.tekion.game.models.Player;
import com.tekion.game.service.InningServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;


@RestController
@RequestMapping("/Player")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;
    private InningServiceImpl inningService;

    //player details for given match.
    @RequestMapping("/Details/{matchID}/{playerID}")
    public ResponseEntity<String> getPlayerRequiredDetails(@PathVariable int matchID, @PathVariable int playerID) throws SQLException{
        return ResponseEntity.ok(playerRepository.getPlayerDetails(playerID,matchID));
    }

    // get player Name by ID.
    @RequestMapping("/Name/{playerID}")
    public ResponseEntity<String> getPlayerName(@PathVariable  int playerID) throws SQLException {
        return ResponseEntity.ok(playerRepository.getPlayerName(playerID));
    }

    //get all players for the team who ever played active/inactive.
    @RequestMapping("/AllWhoPlayedForTeam/{teamID}")
    public ResponseEntity<ArrayList<ArrayList<String>>> getALLPlayersTeam(@PathVariable int teamID) throws SQLException {
        ArrayList<ArrayList<String>> allPlayers = playerRepository.getAllPlayerForTeam(teamID);
        return ResponseEntity.ok(allPlayers);
    }

    //get all players played in certain match for certain team
    @RequestMapping("/playedMatch/{matchID}/{teamID}")
    public ResponseEntity<ArrayList<ArrayList<String>>> getPlayersForMatch(@PathVariable int matchID,@PathVariable int teamID) throws SQLException {
        ArrayList<ArrayList<String>> allPlayers = playerRepository.getAllPlayerInMatch(matchID,teamID);
        return ResponseEntity.ok(allPlayers);
    }

}
