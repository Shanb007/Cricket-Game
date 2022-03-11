package com.tekion.game.service;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public interface MatchService {
    String matchDeclaration(int overs) throws SQLException;
    ArrayList<String> startFirstInnings(int overs, int matchID, int BatTeamID, int BallTeamID) throws SQLException, ClassNotFoundException;
    ArrayList<String> startSecondInnings(int overs, int matchID, int BatTeamID, int BallTeamID) throws SQLException, ClassNotFoundException;
    String ShowResults(int matchID, int TeamA_ID, int TeamB_ID) throws SQLException;
}
