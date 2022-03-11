package com.tekion.game.service;


import com.tekion.game.bean.Matches;
import com.tekion.game.models.Player;
import com.tekion.game.models.Team;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public interface ScoreBoardService {
    void viewScoreBoardAfterWicketOROver(ArrayList<Player> bat, ArrayList<Player> bowl );
    void viewScoreBoard(Matches match, Team BatTeam, Team BowlTeam, ArrayList<Player> players, ArrayList<Player> bowl, String inning, ArrayList<String> wicketsFallen);
}
