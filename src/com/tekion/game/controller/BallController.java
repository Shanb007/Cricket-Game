package com.tekion.game.controller;


import com.tekion.game.Repository.BallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/Ball")
public class BallController {

    @Autowired
    private BallRepository ballRepository;

    //one ball outcome
    @RequestMapping("/ballInfo/{matchID}/{innings}/{overNo}")
    public ResponseEntity<ArrayList<String>> ballInfo(@PathVariable int matchID, @PathVariable String innings, @PathVariable double overNo) throws SQLException {
        ArrayList<String> ballEventResult = ballRepository.getBallInfo(matchID,innings,overNo);
        return ResponseEntity.ok(ballEventResult);
    }

    //whole  over outcome
    @RequestMapping("/overOutcome/{matchID}/{innings}/{overNo}")
    public ResponseEntity<ArrayList<ArrayList<String>>> overInfo(@PathVariable int matchID, @PathVariable String innings, @PathVariable int overNo) throws SQLException {
        ArrayList<ArrayList<String>> overEventResult = ballRepository.getOverInfo(matchID,innings,overNo);
        return ResponseEntity.ok(overEventResult);
    }

}
