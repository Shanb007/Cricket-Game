package com.tekion.game.service;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public interface InningsService {
    ArrayList<String> InitiateInnings(String bat, String ball, int overs, String inning) throws SQLException;
    String addBatPlayerAfterWicket(int matchID, int teamID, String player) throws SQLException;
    String overBy(int matchID, int teamID, String player) throws SQLException, ClassNotFoundException;
    String playOver(int matchID, String inning, int BatTeamID, int BallTeamID, int ballingPlayerID) throws SQLException, ClassNotFoundException;
    String addFirstTwoBatsman(int matchID, int BatTeamID, String Bat1, String Bat2) throws SQLException;

}
