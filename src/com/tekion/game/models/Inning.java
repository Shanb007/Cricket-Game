package com.tekion.game.models;

import com.tekion.game.service.ScoreBoardService;
import com.tekion.game.service.StrikeService;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Inning {
    private int currentOnStrike = 0;
    private int currentNotOnStrike = 1;
    private int currentBowler = 0;
    private final ArrayList<String> wicketFallTrack = new ArrayList<>();

    public void InitiateInnings(Team bat, Team ball, int overs, String inning){
        playInnings(bat,ball,overs,inning);
    }

    private void playInnings(Team BatTeam, Team BowlTeam, int overs, String inning) {
        Random randomOutcome = new Random();
        ArrayList<Player> playing11 = new ArrayList<>();
        ArrayList<Player> bowlers = new ArrayList<>();

        setUpPlayers(playing11,bowlers);

        System.out.println("\n"+(BatTeam.currentOver() + 1)+ " over begins: ");
        System.out.println("Batsman on Strike: "+ playing11.get(currentOnStrike).getName()+"\n"+"Batsman on Non-Striker end: "+playing11.get(currentNotOnStrike).getName());

        for (int ball = 1; ball <= overs * 6; ball++) {
            Ball playBall = new Ball();
            int currentBall = randomOutcome.nextInt(10);
            switch (currentBall) {
                case 0:
                    gotWicket(BatTeam,playing11,bowlers,playBall);
                    break;
                case 2:
                    NoBall(BatTeam,bowlers,playBall);
                    ball--;
                    break;
                case 3:
                    WideBall(BatTeam,bowlers,playBall);
                    ball--;
                    break;
                default:
                    madeRuns(BatTeam,playing11,bowlers,playBall);
            }
            if (bowlers.get(currentBowler).getBallsBowled() % 6 == 0 && ball != overs * 6 && bowlers.get(currentBowler).getBallsBowled()!=0 && currentBall != 3 && currentBall != 2) {
                //System.out.println(ball);
                System.out.println("\nScoreBoard after "+ (BatTeam.currentOver() + 1) + " overs.");
                ScoreBoardService.showScoreBoardAfterWicketOROver(playing11,bowlers);
                overChange(BatTeam,bowlers,overs);
                System.out.println("\n"+(BatTeam.currentOver() + 1)+ " over begins: ");
                System.out.println("Batsman on Strike: "+ playing11.get(currentOnStrike).getName()+"\n"+"Batsman on Non-Striker end: "+playing11.get(currentNotOnStrike).getName());
            }
            if (ball == overs*6){
                System.out.println("ScoreBoard after "+ BatTeam.currentOver() + " overs.");
                ScoreBoardService.showScoreBoardAfterWicketOROver(playing11,bowlers);
            }
            if (inning.equals("1st") && BatTeam.totalWicketsGone() == 10) {
                break;
            }
            if (inning.equals("2nd") && (BatTeam.totalWicketsGone() == 10 || BatTeam.TeamScore() > BowlTeam.TeamScore())){
                break;
            }
        }
        ScoreBoardService.showScoreBoard(BatTeam, playing11, bowlers, inning, wicketFallTrack);
    }

    private void setUpPlayers(ArrayList<Player> playing11, ArrayList<Player> bowlers){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the player name to bat (on strike): ");
        String batsman1 = sc.next();
        playing11.add(new Player(batsman1));
        System.out.print("Enter the player name to bat second (Non-Striker end): ");
        String batsman2 = sc.next();
        playing11.add(new Player(batsman2));
        System.out.print("1st over will be bowled by: ");
        String bowlerName = sc.next();
        bowlers.add(new Player(bowlerName));
    }

    private void gotWicket(Team team, ArrayList<Player> battingTeam,ArrayList<Player> bowlingTeam,Ball playBall){
        bowlingTeam.get(currentBowler).BallsBowledTracker();
        System.out.println("\nWICKET !!!! and it's a wicket " + battingTeam.get(currentOnStrike).getName() + " has to make his way back to pavilion.");
        battingTeam.get(currentOnStrike).setWicketTakenBy(bowlingTeam.get(currentBowler).getName());
        Scanner sc = new Scanner(System.in);
        battingTeam.get(currentOnStrike).BallsPlayedTracker();
        team.TeamScoreCalculator(playBall.wicket());
        team.wicketGoneTracker();
        bowlingTeam.get(currentBowler).wicketTracker();
        wicketFallTrack.add(team.TeamScore() +"-"+team.totalWicketsGone());
        System.out.println("\nScoreboard after the fall of " + team.totalWicketsGone()+" wickets: ");
        ScoreBoardService.showScoreBoardAfterWicketOROver(battingTeam,bowlingTeam);
        System.out.print("\nEnter the " + team.totalWicketsGone() + " wicket down player, to bat: ");
        String newOnField = sc.next();
        battingTeam.add(new Player(newOnField));
        StrikeService.changeCurrentOnStrike((team.totalWicketsGone()+1));
        currentOnStrike = StrikeService.OnStrike();
        currentNotOnStrike = StrikeService.NotOnStrike();
    }


    private void madeRuns(Team team, ArrayList<Player> battingTeam,ArrayList<Player> bowlingTeam,Ball playBall){
        bowlingTeam.get(currentBowler).BallsBowledTracker();
        battingTeam.get(currentOnStrike).BallsPlayedTracker();
        int score = playBall.runs();
        if (score % 2 == 0) {
            battingTeam.get(currentOnStrike).runTracker(score);
            if (score == 4) {
                battingTeam.get(currentOnStrike).trackNoOf4s();
            }
            if (score == 6) {
                battingTeam.get(currentOnStrike).trackNoOf6s();
            }
        } else {
            battingTeam.get(currentOnStrike).runTracker(score);
            StrikeService.changeStrike(currentOnStrike, currentNotOnStrike);
        }
        currentOnStrike = StrikeService.OnStrike();
        currentNotOnStrike = StrikeService.NotOnStrike();
        bowlingTeam.get(currentBowler).runsGivenTracker(score);
        team.TeamScoreCalculator(score);
    }


    private void WideBall(Team team, ArrayList<Player> bowlingTeam,Ball playBall){
        int wideB = playBall.wideBall();
        bowlingTeam.get(currentBowler).WideBallsBowledTracker();
        bowlingTeam.get(currentBowler).runsGivenTracker(wideB);
        team.TeamScoreCalculator(wideB);
        team.ExtraScoreTracker(wideB);
        team.WideBallTracker();
        if (wideB % 2 == 0) {
            StrikeService.changeStrike(currentOnStrike, currentNotOnStrike);
        }
    }


    private void NoBall(Team team, ArrayList<Player> bowlingTeam,Ball playBall){
        int noBall = playBall.NoBall();
        bowlingTeam.get(currentBowler).NoBallsBowledTracker();
        bowlingTeam.get(currentBowler).runsGivenTracker(noBall);
        team.TeamScoreCalculator(noBall);
        team.ExtraScoreTracker(noBall);
        team.NoBallTracker();
        if (noBall % 2 == 0) {
            StrikeService.changeStrike(currentOnStrike, currentNotOnStrike);
        }
    }


    private void overChange(Team team, ArrayList<Player> bowlingTeam, int overs){
        Scanner sc = new Scanner(System.in);
        team.currentOverCalculator();
        StrikeService.changeStrike(currentOnStrike, currentNotOnStrike);
        currentOnStrike = StrikeService.OnStrike();
        currentNotOnStrike = StrikeService.NotOnStrike();
        if ((team.currentOver()+1)==2) {
            System.out.print("\n"+(team.currentOver() + 1) + "nd over will be bowled by: ");
        }
        else if ((team.currentOver()+1)==3) {
            System.out.print("\n"+(team.currentOver() + 1) + "rd over will be bowled by: ");
        }
        else{
            System.out.print("\n"+(team.currentOver() + 1) + "th over will be bowled by: ");
        }
        String newBowlerName = sc.next();
        // check if bowler already present
        int flag = 0;
        for (int bowler = 0; bowler < bowlingTeam.size(); bowler++) {
            if (newBowlerName.equals(bowlingTeam.get(bowler).getName()) && bowlingTeam.get(bowler).getBallsBowled() <= (double) overs * 5) {
                currentBowler = bowler;
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            bowlingTeam.add(new Player(newBowlerName));
            currentBowler = bowlingTeam.size() - 1;
        }
    }

}
