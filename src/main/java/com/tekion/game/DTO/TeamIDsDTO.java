package com.tekion.game.DTO;

public class TeamIDsDTO {
    private int teamA_ID;
    private int teamB_ID;

    public void setTeamB_ID(int teamB_ID) {
        this.teamB_ID = teamB_ID;
    }

    public void setTeamA_ID(int teamA_ID) {
        this.teamA_ID = teamA_ID;
    }

    public int getTeamB_ID() {
        return teamB_ID;
    }

    public int getTeamA_ID() {
        return teamA_ID;
    }
}
