package com.tekion.game.controller;

import com.tekion.game.service.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Teams")
public class TeamController {

    @Autowired
    private TeamServiceImpl teamService;

    // teamName by teamID
    @GetMapping("/Name/{teamID}")
    public ResponseEntity<String> teamName(@PathVariable int teamID){
        return ResponseEntity.ok(teamService.teamName(teamID));
    }

    // all teams
    @GetMapping("/AllTeams")
    public ResponseEntity<List<Map<String, Object>>> allTeams(){
        return ResponseEntity.ok(teamService.allTeams());
    }

    //teams with matchID
    @GetMapping("/PlayingMatch/{matchID}")
    public ResponseEntity<List<String>> teamPlayingMatch(@PathVariable int matchID){
        return ResponseEntity.ok(teamService.teamPlayingMatch(matchID));
    }

}
