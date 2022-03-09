package com.tekion.game.controller;

import com.tekion.game.Repository.MatchRepository;
import com.tekion.game.models.Player;
import com.tekion.game.service.InningServiceImpl;
import com.tekion.game.service.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/Match")

public class MatchController {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private InningServiceImpl inningService;
    @Autowired
    private MatchServiceImpl matchService;

    //match created with number of overs.
    @RequestMapping("/create/{overs}")
    public String startGame(@PathVariable int overs) throws SQLException{
    return matchService.matchDeclaration(overs);
    }

    //play first innings
    @RequestMapping("/startFirstInnings/{matchID}")
    public ResponseEntity<ArrayList<String>> playFirst(@PathVariable int matchID) throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok(matchService.startFirstInnings((int) matchRepository.getOversByMatchID(matchID)));
    }

    //play second innings
    @RequestMapping("/startSecondInnings/{matchID}/{BatTeamID}")
    public ResponseEntity<ArrayList<String>> playMatch(@PathVariable int matchID) throws SQLException, ClassNotFoundException {
       return ResponseEntity.ok(matchService.startSecondInnings((int) matchRepository.getOversByMatchID(matchID)));
    }



    //creating batting line up
    @PostMapping("/addBatsman/{matchID}/{teamID}")
    public String addBatsman(@PathVariable int matchID,@PathVariable int teamID ,@RequestBody String player) throws SQLException {
        return inningService.addBatPlayer(matchID,teamID,player);
    }

    //next bowler
    @PostMapping("/overChange/{matchID}/{teamID}")
    public String nextOverBy(@PathVariable int matchID,@PathVariable int teamID ,@RequestBody String player) throws SQLException {
           return inningService.overBy(matchID,teamID,player);
    }

    //play Over.
    @RequestMapping("/playOver/{matchID}")
    public String playOver(@PathVariable int matchID) throws SQLException {
        return inningService.playOver(matchID);
    }


    //details of given match.
    @RequestMapping("/getMatchDetails/{matchID}")
    public ResponseEntity<ArrayList<String>> getMatchDetails(@PathVariable int matchID) throws SQLException {
        ArrayList<String> matchStats = matchRepository.getMatchDetail(matchID);
        return ResponseEntity.ok(matchStats);
    }


    //returns all matches completed/ongoing.
    @RequestMapping("/getAllMatches")
    public ResponseEntity<ArrayList<ArrayList<String>>> getAllMatches() throws SQLException {
        ArrayList<ArrayList<String>> allMatches = matchRepository.getAllMatches();
        return ResponseEntity.ok(allMatches);
    }

    //returns all matches completed with their match results.
    @RequestMapping("/getAllCompletedMatches")
    public ResponseEntity<ArrayList<ArrayList<String>>> getAllCompletedMatches() throws SQLException {
        ArrayList<ArrayList<String>> allCompletedMatches = matchRepository.getAllCompletedMatches();
        return ResponseEntity.ok(allCompletedMatches);
    }

    //get ScoreBoard of the Match
    @RequestMapping("/ScoreCard/{matchID}/{teamID}")
    public ResponseEntity<ArrayList<ArrayList<String>>> scoreBoard(@PathVariable int matchID, @PathVariable int teamID) throws SQLException {
        ArrayList<ArrayList<String>> scoreCard = matchRepository.getBattingScoreBoard(matchID,teamID);
        return ResponseEntity.ok(scoreCard);
    }

}
