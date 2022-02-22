package com.tekion.game.models;

import com.tekion.game.service.InningsService;

import java.util.*;

public class Match {
    // two teams to be declared for a match
    private final Team FirstInningsTeam  = new Team();
    private final Team SecondInningsTeam = new Team();
    private final Umpire umpire = new Umpire();


public void matchDeclaration() {
    String[] teamName = {"India", "Australia", "New Zealand", "South Africa", "West Indies", "Pakistan", "Sri Lanka"};
    Random N = new Random();
    int team1 = N.nextInt(teamName.length);
    int team2 = N.nextInt(teamName.length);

    while(team1 == team2){
        team2 = N.nextInt(teamName.length);
    }
    System.out.println("The Match: " + teamName[team1] + " V/S " + teamName[team2]);
    umpire.SetFirstUmpire();
    umpire.SetSecondUmpire();
    umpire.SetThirdUmpire();
    System.out.println("------------------------------------------------------------------------");
    System.out.println("The Umpire behind the stumps for the match is: "+ umpire.getFirstUmpire()+".");
    System.out.println("The Leg Side Umpire for the match is: "+ umpire.getSecondUmpire()+".");
    System.out.println("The Third Umpire for the match is: "+ umpire.getThirdUmpire()+".");
    System.out.println("------------------------------------------------------------------------");
    System.out.println("Both the Captains are on the field for the Toss.");
    Toss tossResult = new Toss();
    int won = tossResult.tossWinner(team1, team2);
    int lost = tossResult.tossLoser();
    int choiceMade = N.nextInt(2);
    if (choiceMade==1){
        FirstInningsTeam.setTeamName(teamName[won]);
        SecondInningsTeam.setTeamName(teamName[lost]);
        System.out.println(FirstInningsTeam.getTeam() + " won the toss, and chose to Bat first.");
    }
    else{
        FirstInningsTeam.setTeamName(teamName[lost]);
        SecondInningsTeam.setTeamName(teamName[won]);
        System.out.println(SecondInningsTeam.getTeam()+" won the toss, and chose ot ball first.");
    }
}

public void startTheMatch(int overs) {
    inningsStarts(overs);
ShowResults();
}

    private void inningsStarts(int overs){
        InningsService StartInnings = new InningsService();
        StartInnings.InningsStart(FirstInningsTeam,SecondInningsTeam,overs);
    }

private void ShowResults(){
    System.out.println();
if (FirstInningsTeam.TeamScore() == SecondInningsTeam.TeamScore()){
    System.out.println("Match Tied");
}
if (FirstInningsTeam.TeamScore() > SecondInningsTeam.TeamScore()){
    System.out.println(FirstInningsTeam.getTeam() + " won by " + (FirstInningsTeam.TeamScore()-SecondInningsTeam.TeamScore()) + " runs.");
}
if (FirstInningsTeam.TeamScore() < SecondInningsTeam.TeamScore()){
    System.out.println(SecondInningsTeam.getTeam() + " won by " + (10-SecondInningsTeam.totalWicketsGone() + " wickets."));
}
}
}
