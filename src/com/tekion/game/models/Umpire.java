package com.tekion.game.models;

import com.tekion.game.service.UmpireServiceImpl;

public class Umpire {
    private final String[] umpires = {"Suresh Shastri", "Aleem Dar", "Billy Bowden"};
    private final UmpireServiceImpl umpireService = new UmpireServiceImpl();

    public String getFirstUmpire(){
        return umpires[umpireService.SetFirstUmpire()];
    }

    public String getSecondUmpire(){
        return umpires[umpireService.SetSecondUmpire()];
    }

    public String getThirdUmpire(){
        return umpires[umpireService.SetThirdUmpire()];
    }

}
