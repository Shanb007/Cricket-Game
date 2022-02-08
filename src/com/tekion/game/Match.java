package com.tekion.game;

public class Match {
    // two teams to be declared for a match
    Team t = new Team();
private final String FirstTeam  = t.selectFirstTeam();
private final String SecondTeam = t.selectSecondTeam();


public void matchDeclaration() {
    System.out.println("The Match: " + FirstTeam + " V/S " + SecondTeam);
}


}
