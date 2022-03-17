package com.tekion.game.DTO;

public class PlayersDTO {
    private int playerID;
    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerID() {
        return playerID;
    }
}
