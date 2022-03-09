package com.tekion.game.service;

import com.tekion.game.bean.Matches;
import com.tekion.game.models.Player;
import com.tekion.game.models.Team;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public interface InningsService {
    ArrayList<String> InitiateInnings(Matches match, Team bat, Team ball, int overs, String inning);
    String addBatPlayer(int matchID, int teamID, String player) throws SQLException;
    String overBy(int matchID, int teamID, String player) throws SQLException;
    String playOver(int matchID) throws SQLException;
}
