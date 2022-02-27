package com.tekion.game.controller;

import com.tekion.game.service.GameService;

public class GameBegins {
    public static void main(String[] args) {
        GameService game = new GameService();
        game.PlayTheGame();
    }
}
