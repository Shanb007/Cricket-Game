package com.tekion.game.models;

import com.tekion.game.service.InningsService;
import com.tekion.game.service.TossService;

import java.util.*;

public class Match {
    // two teams to be declared for a match
    private final Team FirstInningsTeam  = new Team();
    private final Team SecondInningsTeam = new Team();

    public void matchDeclaration() {
        String[] teamName = {"India", "Australia", "New Zealand", "South Africa", "West Indies", "Pakistan", "Sri Lanka"};
        Random randomPick = new Random();
        int team1 = randomPick.nextInt(teamName.length);
        int team2 = randomPick.nextInt(teamName.length);

        while(team1 == team2){
            team2 = randomPick.nextInt(teamName.length);
        }
        Umpire umpire = new Umpire();
        System.out.println("\nThe Match: " + teamName[team1] + " V/S " + teamName[team2]);
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("The Umpire behind the stumps for the match is: "+ umpire.getFirstUmpire()+".");
        System.out.println("The Leg Side Umpire for the match is: "+ umpire.getSecondUmpire()+".");
        System.out.println("The Third Umpire for the match is: "+ umpire.getThirdUmpire()+".");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nBoth the Captains are on the field for the Toss.");
        int won = TossService.tossWinner(team1, team2);
        int lost = TossService.tossLoser();
        int choiceMade = randomPick.nextInt(2);
        if (choiceMade==1){
            FirstInningsTeam.setTeamName(teamName[won]);
            SecondInningsTeam.setTeamName(teamName[lost]);
            System.out.println(FirstInningsTeam.getTeam() + " won the toss, and chose to Bat first.");
        }
        else{
            FirstInningsTeam.setTeamName(teamName[lost]);
            SecondInningsTeam.setTeamName(teamName[won]);
            System.out.println(SecondInningsTeam.getTeam()+" won the toss, and chose to ball first.");
        }
    }

    public void startTheMatch(int overs) {
        InningsService.InningsStart(FirstInningsTeam,SecondInningsTeam,overs);
        ShowResults();
    }

    private void ShowResults(){
        System.out.println();
        if (FirstInningsTeam.TeamScore() == SecondInningsTeam.TeamScore()){
            System.out.println("Match Tied.\n\n");
        }
        if (FirstInningsTeam.TeamScore() > SecondInningsTeam.TeamScore()){
            System.out.println(FirstInningsTeam.getTeam() + " won by " + (FirstInningsTeam.TeamScore()-SecondInningsTeam.TeamScore()) + " runs.\n\n");
        }
        if (FirstInningsTeam.TeamScore() < SecondInningsTeam.TeamScore()){
            System.out.println(SecondInningsTeam.getTeam() + " won by " + (10-SecondInningsTeam.totalWicketsGone() + " wickets.\n\n"));
        }
    }
}
