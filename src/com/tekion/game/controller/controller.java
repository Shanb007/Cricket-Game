package com.tekion.game.controller;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.tekion.game.service.SBservice.*;

@RestController

public class controller {

    //match start with number of overs.
    @RequestMapping("/startMatch/{overs}")
    public String startGame(@PathVariable int overs) throws SQLException, ClassNotFoundException {
    playMatch(overs);
    return "Game over.";
    }

    //player details for given match.
    @GetMapping("/getPlayerDetails/{matchID}/{playerID}")
    public String getPlayerRequiredDetails(@PathVariable int matchID,@PathVariable int playerID) throws SQLException, ClassNotFoundException {
        return getPlayersDetails(playerID,matchID);
    }

    //details of given match.
    @GetMapping("/getMatchDetails/{matchID}")
    public ArrayList<String> getMatchDetails(@PathVariable int matchID) throws SQLException, ClassNotFoundException {
        return getMatchStats(matchID);
    }
}
