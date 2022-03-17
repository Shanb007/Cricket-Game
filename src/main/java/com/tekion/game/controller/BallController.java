package com.tekion.game.controller;

import com.tekion.game.service.BallServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Ball")
public class BallController {

    @Autowired
    private BallServiceImpl ballService;

    //one ball outcome
    @RequestMapping("/ballInfo/{matchID}/{innings}/{overNo}")
    public ResponseEntity<List<Map<String, Object>>> ballInfo(@PathVariable int matchID, @PathVariable String innings, @PathVariable double overNo) throws SQLException {
        List<Map<String, Object>> ballEventResult = ballService.ballInfoService(matchID,innings,overNo);
        return ResponseEntity.ok(ballEventResult);
    }

    //whole  over outcome
    @RequestMapping("/overOutcome/{matchID}/{innings}/{overNo}")
    public ResponseEntity<List<Map<String, Object>>> overInfo(@PathVariable int matchID, @PathVariable String innings, @PathVariable int overNo) throws SQLException {
        List<Map<String, Object>> overEventResult = ballService.overInfoService(matchID,innings,overNo);
        return ResponseEntity.ok(overEventResult);
    }

}
