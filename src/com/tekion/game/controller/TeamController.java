package com.tekion.game.controller;


import com.tekion.game.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/Teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    // teamName by teamID
    @RequestMapping("/Name/{teamID}")
    public ResponseEntity<String> teamName(@PathVariable int teamID) throws SQLException {
        return ResponseEntity.ok(teamRepository.getTeamName(teamID));
    }

    // all teams
    @RequestMapping("/AllTeams")
    public ResponseEntity<ArrayList<String>> allTeams() throws SQLException {
        return ResponseEntity.ok(teamRepository.getAllTeams());
    }

    //teams with matchID
    @RequestMapping("/PlayingMatch/{matchID}")
    public ResponseEntity<ArrayList<String>> teamPlayingMatch(@PathVariable int matchID) throws SQLException {
        return ResponseEntity.ok(teamRepository.getTeamsInMatch(matchID));
    }



}
