package com.tekion.game;
import java.util.*;

public class Match {
    // two teams to be declared for a match
    Team FirstTeam  = new Team();
    Team SecondTeam = new Team();
    Umpire U = new Umpire();

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
    U.SetFirstUmpire();
    U.SetSecondUmpire();
    U.SetThirdUmpire();
    System.out.println("------------------------------------------------------------------------");
    System.out.println("The Umpire behind the stumps for the match is: "+ U.getFirstUmpire()+".");
    System.out.println("The Leg Side Umpire for the match is: "+ U.getSecondUmpire()+".");
    System.out.println("The Third Umpire for the match is: "+ U.getThirdUmpire()+".");
    System.out.println("------------------------------------------------------------------------");
    System.out.println();
}
public void startTheMatch(int overs) {
    Random N = new Random();
    int toss = N.nextInt(2);
   // System.out.println("Toss Results: " + toss);
    int choiceMade = N.nextInt(2);
    if (toss == 1) {
        System.out.println(SecondTeam.getTeam() + " won the toss and chose to " + choice[choiceMade] + ".");
    } else {
        System.out.println(FirstTeam.getTeam() + " won the toss and chose to " + choice[choiceMade] + ".");
    }
    System.out.println();
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
    //Over o = new Over();
    //Ball b = new Ball();
    int i ;
    //int W =0;
    for(i=1;i<=overs*6;i++){
        if (i%6 == 0){
            TeamBatFirst.currentOverCalculator();
        }
        int currentBall = TeamBatFirst.currentBallOutcome();
        if (currentBall <= 6){
            TeamBatFirst.TeamScoreCalculator(currentBall);
        }
        else if (currentBall == 7){
            TeamBatFirst.wicketGone();
            //new player to enter
        }
        else if (currentBall == 8){
            TeamBatFirst.WideBallTracker();
            TeamBatFirst.TeamScoreCalculator(1);
            i--;
        }
        else {
            TeamBatFirst.NoBallTracker();
            TeamBatFirst.TeamScoreCalculator(1);
            i--;
        }
        if (TeamBatFirst.totalWicketsGone() == 10){
            break;
        }
    }
    System.out.println(TeamBatFirst.getTeam() + " scored " + TeamBatFirst.TeamScore() + " with loss of " + TeamBatFirst.totalWicketsGone()+ " wickets.");
    System.out.println("Extras: " + TeamBatFirst.TotalExtras());
    System.out.println("NB: "+TeamBatFirst.TotalNoBalls()+ " WB: "+ TeamBatFirst.TotalWideBalls());
}

    private void secondInnings(Team TeamBatSecond, Team TeamBatFirst, int overs){
    System.out.println();
    System.out.print("2nd Innings: ");
        //Over o = new Over();
        int i ;
        //int W =0;
        for(i=1;i<=overs*6;i++){
            if (i%6 == 0){
                TeamBatSecond.currentOverCalculator();
            }
            int currentBall = TeamBatSecond.currentBallOutcome();
            if (currentBall <= 6){
                TeamBatSecond.TeamScoreCalculator(currentBall);
            }
            else if (currentBall == 7){
                TeamBatSecond.wicketGone();
                //new player to enter
            }
            else if (currentBall == 8){
                TeamBatSecond.WideBallTracker();
                TeamBatFirst.TeamScoreCalculator(1);
                i--;
            }
            else {
                TeamBatSecond.NoBallTracker();
                TeamBatFirst.TeamScoreCalculator(1);
                i--;
            }

            if (TeamBatSecond.totalWicketsGone() == 10 || TeamBatSecond.TeamScore() > TeamBatFirst.TeamScore()){
                break;
            }
        }
        System.out.println(TeamBatSecond.getTeam() + " scored " + TeamBatSecond.TeamScore() + " with loss of " + TeamBatSecond.totalWicketsGone()+ " wickets.");
        System.out.println("Extras:" + TeamBatSecond.TotalExtras());
        System.out.println("NB: "+TeamBatSecond.TotalNoBalls()+ " WB: "+ TeamBatSecond.TotalWideBalls());

    }



public void ShowResults(){
    System.out.println();
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
