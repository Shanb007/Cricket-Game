package com.tekion.game.service;

import com.tekion.game.DTO.AllMatchesDTO;
import com.tekion.game.DTO.MatchDetailDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public interface MatchService {
    String matchDeclaration(String team1, String team2, int overs) throws SQLException, ClassNotFoundException;
    ArrayList<String> startFirstInnings(int overs, int matchID, int BatTeamID, int BallTeamID) throws SQLException, ClassNotFoundException;
    ArrayList<String> startSecondInnings(int overs, int matchID, int BatTeamID, int BallTeamID) throws SQLException, ClassNotFoundException;
    String ShowResults(int matchID, int TeamA_ID, int TeamB_ID) throws SQLException;
    List<MatchDetailDTO> matchDetails(int matchID) throws SQLException;
    List<AllMatchesDTO> AllMatches() throws SQLException;
    List<MatchDetailDTO> AllCompletedMatches() throws SQLException;
    List<List<String>> matchScoreBoard(int matchID,int teamID) throws SQLException, ClassNotFoundException;
    double getOversForMatch(int matchID) throws SQLException;

}
