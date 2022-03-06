package com.tekion.game.service;

import com.tekion.game.DBUpdateHelper.MatchDBHelper;
import com.tekion.game.DBUpdateHelper.PlayerDBHelper;
import com.tekion.game.controller.GameBegins;

import java.sql.SQLException;
import java.util.ArrayList;

public class SBservice {

    public static void playMatch(int overs) throws SQLException, ClassNotFoundException {
        GameBegins gameBegins  =new GameBegins();
        gameBegins.beginMatch(overs);
    }

    public static String getPlayersDetails(int playerID, int matchID) throws SQLException, ClassNotFoundException {
        PlayerDBHelper playerDBHelper = new PlayerDBHelper();
        return playerDBHelper.getPlayerDetails(playerID,matchID);
    }

    public static ArrayList<String> getMatchStats(int matchID) throws SQLException, ClassNotFoundException {
        MatchDBHelper matchDBHelper = new MatchDBHelper();
        return matchDBHelper.getMatchDetail(matchID);
    }
}
