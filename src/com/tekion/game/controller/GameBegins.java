package com.tekion.game.controller;

import com.tekion.game.service.GameService;

import java.sql.SQLException;

public class GameBegins {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        GameService game = new GameService();
        game.PlayTheGame();
    }
}
