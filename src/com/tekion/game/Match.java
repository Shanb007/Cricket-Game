package com.tekion.game;
import java.util.*;

public class Match {
    // two teams to be declared for a match
    Team FirstTeam  = new Team();
    Team SecondTeam = new Team();

String[] choice = {"Bat", "Ball"};

public void matchDeclaration() {
    String[] teamName = {"India", "Australia", "New Zealand", "South Africa", "West Indies", "Pakistan", "Sri Lanka"};
    Random N = new Random();
    int t1 = N.nextInt(teamName.length);
    int t2 = N.nextInt(teamName.length);

    while(t1 == t2){
        t2 = N.nextInt(teamName.length);
    }
    FirstTeam.setTeamName(teamName[t1]);
    SecondTeam.setTeamName(teamName[t2]);
    System.out.println("The Match: " + FirstTeam.getTeam() + " V/S " + SecondTeam.getTeam());
}
public void startTheMatch(int overs) {
    Random N = new Random();
    int toss = N.nextInt(2);
    System.out.println("Toss Results: " + toss);
    int choiceMade = N.nextInt(2);
    if (toss == 1) {
        System.out.println(SecondTeam.getTeam() + " won the toss and chose to " + choice[choiceMade] + ".");
    } else {
        System.out.println(FirstTeam.getTeam() + " won the toss and chose to " + choice[choiceMade] + ".");
    }

    if (toss == 1) {
        if (choiceMade == 0) {
            firstInnings(SecondTeam, overs);
            secondInnings(FirstTeam,SecondTeam,overs);
        } else {
            firstInnings(FirstTeam, overs);
            secondInnings(SecondTeam,FirstTeam,overs);
        }
    }
    if (toss == 0) {
        if (choiceMade == 0) {
            firstInnings(FirstTeam, overs);
            secondInnings(SecondTeam,FirstTeam,overs);
        } else {
            firstInnings(SecondTeam, overs);
            secondInnings(FirstTeam,SecondTeam,overs);
        }
    }
}

private void firstInnings(Team TeamBatFirst, int overs){
    System.out.print("1st Innings: ");
    Over o = new Over();
    int i ;
    //int W =0;
    for(i=1;i<=overs*6;i++){
        if (i%6 == 0){
            o.currentOverCalculator();
        }
        int currentBall = o.currentBallOutcome();
        if (currentBall <= 6){
            TeamBatFirst.TeamScoreCalculator(currentBall);
        }
        else {
            TeamBatFirst.wicketGone();
            //new player to enter
        }
        if (TeamBatFirst.totalWicketsGone() == 10){
            break;
        }
    }
    System.out.println(TeamBatFirst.getTeam() + " scored " + TeamBatFirst.TeamScore() + " with loss of " + TeamBatFirst.totalWicketsGone()+ " wickets.");
}

    private void secondInnings(Team TeamBatSecond, Team TeamBatFirst, int overs){
        System.out.print("2nd Innings: ");
        Over o = new Over();
        int i ;
        //int W =0;
        for(i=1;i<=overs*6;i++){
            if (i%6 == 0){
                o.currentOverCalculator();
            }
            int currentBall = o.currentBallOutcome();
            if (currentBall <= 6){
                TeamBatSecond.TeamScoreCalculator(currentBall);
            }
            else {
                TeamBatSecond.wicketGone();
                //new player to enter
            }
            if (TeamBatSecond.totalWicketsGone() == 10 || TeamBatSecond.TeamScore() > TeamBatFirst.TeamScore()){
                break;
            }
        }
        System.out.println(TeamBatSecond.getTeam() + " scored " + TeamBatSecond.TeamScore() + " with loss of " + TeamBatSecond.totalWicketsGone()+ " wickets.");
    }



public void ShowResults(){
if (FirstTeam.TeamScore() == SecondTeam.TeamScore()){
    System.out.println("Match Tied");
}
if (FirstTeam.TeamScore() > SecondTeam.TeamScore()){
    System.out.println(FirstTeam.getTeam() + " Won.");
}
if (FirstTeam.TeamScore() < SecondTeam.TeamScore()){
    System.out.println(SecondTeam.getTeam() + " Won.");
}
}
}
