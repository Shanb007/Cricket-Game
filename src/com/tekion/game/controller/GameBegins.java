package com.tekion.game.controller;

import com.tekion.game.service.GameService;

import java.sql.SQLException;

public class GameBegins {
    public void beginMatch(int overs) throws SQLException, ClassNotFoundException {
        GameService game = new GameService();
        game.PlayTheGame(overs);
    }
}
