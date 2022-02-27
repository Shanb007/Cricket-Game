package com.tekion.game.models;

import com.tekion.game.service.UmpireService;

public class Umpire {
    private final String[] umpires = {"Suresh Shastri", "Aleem Dar", "Billy Bowden"};

    String getFirstUmpire(){
        return umpires[UmpireService.SetFirstUmpire()];
    }

    String getSecondUmpire(){
        return umpires[UmpireService.SetSecondUmpire()];
    }

    String getThirdUmpire(){
        return umpires[UmpireService.SetThirdUmpire()];
    }

}
