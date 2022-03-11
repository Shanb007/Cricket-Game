package com.tekion.game.controller;

import com.tekion.game.Repository.MatchRepository;
import com.tekion.game.service.InningServiceImpl;
import com.tekion.game.service.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

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
    @RequestMapping("/startFirstInnings/{matchID}/{BatTeamID}/{BallTeamID}")
    public ResponseEntity<ArrayList<String>> playFirst(@PathVariable int matchID,@PathVariable int BatTeamID, @PathVariable int BallTeamID) throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok(matchService.startFirstInnings((int) matchRepository.getOversByMatchID(matchID), matchID, BatTeamID, BallTeamID));
    }

    //play second innings
    @RequestMapping("/startSecondInnings/{matchID}/{BatTeamID}/{BallTeamID}")
    public ResponseEntity<ArrayList<String>> playMatch(@PathVariable int matchID,@PathVariable int BatTeamID, @PathVariable int BallTeamID) throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok(matchService.startSecondInnings((int) matchRepository.getOversByMatchID(matchID), matchID, BatTeamID, BallTeamID));
    }

    //add first two batsman, to begin innings.
    @PostMapping("/initialBatPlayers/{matchID}/{teamID}")
    public ResponseEntity<String> initiateBatsman(@PathVariable int matchID, @PathVariable int teamID, @RequestBody Map<String, String> json ) throws SQLException {
        return ResponseEntity.ok(inningService.addFirstTwoBatsman(matchID,teamID,json.get("Bat1"),json.get("Bat2")));
    }

    //add batsman, after wicket.
    @PostMapping("/addBatsmanAfterWicket/{matchID}/{BatTeamID}")
    public String addBatsman(@PathVariable int matchID,@PathVariable int BatTeamID ,@RequestBody String player) throws SQLException {
        return inningService.addBatPlayerAfterWicket(matchID,BatTeamID,player);
    }

    //next bowler
    @PostMapping("/overChange/{matchID}/{teamID}")
    public String nextOverBy(@PathVariable int matchID,@PathVariable int teamID ,@RequestBody String player) throws SQLException {
        return inningService.overBy(matchID,teamID,player);
    }

    //play Over.
    @RequestMapping("/playOver/{matchID}/{inning}/{BatTeamID}/{BallTeamID}/{ballingPlayerID}")
    public String playOver(@PathVariable int matchID, @PathVariable String inning, @PathVariable int BatTeamID, @PathVariable int BallTeamID, @PathVariable int ballingPlayerID) throws SQLException {
        return inningService.playOver(matchID,inning,BatTeamID,BallTeamID,ballingPlayerID);
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
