package com.tekion.game.service;

import com.tekion.game.models.Player;
import com.tekion.game.models.ScoreBoard;
import com.tekion.game.models.Team;
import java.util.ArrayList;


public class ScoreBoardService {
    public static void displayScoreCard(Team team, ArrayList<Player> players, ArrayList<Player> bowl, String inning) {
        ScoreBoard scoreCard = new ScoreBoard();
        scoreCard.showScoreBoard(team,players,bowl,inning);
    }
}
