package com.tekion.game.controller;

import com.tekion.game.DTO.AllMatchesDTO;
import com.tekion.game.DTO.MatchDetailDTO;
import com.tekion.game.service.InningServiceImpl;
import com.tekion.game.service.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Match")

public class MatchController {

    @Autowired
    private InningServiceImpl inningService;
    @Autowired
    private MatchServiceImpl matchService;

    //match created with number of overs.
    @PostMapping("/create/{overs}")
    public String startGame(@RequestBody Map<String, String> json, @PathVariable int overs) throws SQLException, ClassNotFoundException {
        return matchService.matchDeclaration(json.get("Team1"),json.get("Team2"),overs);
    }

    //play first innings
    @GetMapping("/startFirstInnings/{matchID}/{BatTeamID}/{BallTeamID}")
    public ResponseEntity<ArrayList<String>> playFirst(@PathVariable int matchID,@PathVariable int BatTeamID, @PathVariable int BallTeamID){
        return ResponseEntity.ok(matchService.startFirstInnings((int) matchService.getOversForMatch(matchID), matchID, BatTeamID, BallTeamID));
    }

    //play second innings
    @GetMapping("/startSecondInnings/{matchID}/{BatTeamID}/{BallTeamID}")
    public ResponseEntity<ArrayList<String>> playMatch(@PathVariable int matchID,@PathVariable int BatTeamID, @PathVariable int BallTeamID){
        return ResponseEntity.ok(matchService.startSecondInnings((int) matchService.getOversForMatch(matchID), matchID, BatTeamID, BallTeamID));
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
    public String nextOverBy(@PathVariable int matchID,@PathVariable int teamID ,@RequestBody String player) throws SQLException{
        return inningService.overBy(matchID,teamID,player);
    }

    //play Over.
    @GetMapping("/playOver/{matchID}/{inning}/{BatTeamID}/{BallTeamID}/{ballingPlayerID}")
    public String playOver(@PathVariable int matchID, @PathVariable String inning, @PathVariable int BatTeamID, @PathVariable int BallTeamID, @PathVariable int ballingPlayerID) throws SQLException{
        return inningService.playOver(matchID,inning,BatTeamID,BallTeamID,ballingPlayerID);
    }

    //show results
    @GetMapping("/result/{matchID}/{TeamA_ID}/{TeamB_ID}")
    public String showResults(@PathVariable int matchID, @PathVariable int TeamA_ID, @PathVariable int TeamB_ID){
        return matchService.ShowResults(matchID,TeamA_ID,TeamB_ID);
    }

    //details of given match.
    @GetMapping("/getMatchDetails/{matchID}")
    public ResponseEntity<List<MatchDetailDTO>> getMatchDetails(@PathVariable int matchID) throws SQLException {
        List<MatchDetailDTO> matchStats = matchService.matchDetails(matchID);
        return ResponseEntity.ok(matchStats);
    }


    //returns all matches completed/ongoing.
    @GetMapping("/getAllMatches")
    public ResponseEntity<List<AllMatchesDTO>> getAllMatches(){
        List<AllMatchesDTO> allMatches = matchService.AllMatches();
        return ResponseEntity.ok(allMatches);
    }

    //returns all matches completed with their match results.
    @GetMapping("/getAllCompletedMatches")
    public ResponseEntity<List<MatchDetailDTO>> getAllCompletedMatches(){
        List<MatchDetailDTO> allCompletedMatches = matchService.AllCompletedMatches();
        return ResponseEntity.ok(allCompletedMatches);
    }

    //get ScoreBoard of the Match
    @GetMapping("/ScoreCard/{matchID}/{teamID}")
    public ResponseEntity<List<List<String>>> scoreBoard(@PathVariable int matchID, @PathVariable int teamID){
        List<List<String>> scoreCard = matchService.matchScoreBoard(matchID,teamID);
        return ResponseEntity.ok(scoreCard);
    }

}
