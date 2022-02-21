package com.tekion.game.models;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Inning {

    static void firstInnings(Team FirstInningsTeam, int overs) {
        int currentOnStrike = 0;
        int currentNotOnStrike = 1;
        int currentBowler = 0;
        Scanner sc = new Scanner(System.in);
        Random N = new Random();
        ArrayList<Player> playing11 = new ArrayList<>();
        System.out.print("Enter the player name to bat (on strike): ");
        String name1 = sc.next();
        playing11.add(new Player(name1));

        System.out.print("Enter the player name to bat second (on non-strike end): ");
        String name2 = sc.next();
        playing11.add(new Player(name2));
        Strike strike = new Strike();
        ScoreBoard scoreBoard = new ScoreBoard();
        ArrayList<Player> bowlers = new ArrayList<>();
        System.out.print("Enter the bowler's name: ");
        String bowlerName = sc.next();
        bowlers.add(new Player(bowlerName));

        for (int ball = 1; ball <= overs * 6; ball++) {
            bowlers.get(currentBowler).OversBowledTracker();
            if (ball % 6 == 0 && ball != overs * 6) {
                FirstInningsTeam.currentOverCalculator();
                strike.changeStrike(currentOnStrike, currentNotOnStrike);
                currentOnStrike = strike.OnStrike();
                currentNotOnStrike = strike.NotOnStrike();
                System.out.print("Enter the bowler's name: ");
                String newBowlerName = sc.next();
                // check if bowler already present
                int flag = 0;
                for (int bowler = 0; bowler < bowlers.size(); bowler++) {
                    if (newBowlerName.equals(bowlers.get(bowler).getName()) && bowlers.get(bowler).getOversBowled() <= (double) overs / 5) {
                        currentBowler = bowler;
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    bowlers.add(new Player(newBowlerName));
                    currentBowler = bowlers.size() - 1;
                }
            }
            Ball playBall = new Ball();
            int currentBall = N.nextInt(10);
            switch (currentBall) {
                case 0:
                    playing11.get(currentOnStrike).BallsPlayedTracker();
                    FirstInningsTeam.TeamScoreCalculator(playBall.wicket());
                    FirstInningsTeam.wicketGoneTracker();
                    bowlers.get(currentBowler).wicketTracker();
                    System.out.print("Enter the " + FirstInningsTeam.totalWicketsGone() + " wicket down player, to bat: ");
                    String newOnField = sc.next();
                    playing11.add(new Player(newOnField));
                    strike.changeCurrentOnStrike(FirstInningsTeam.totalWicketsGone());
                    currentOnStrike = strike.OnStrike();
                    currentNotOnStrike = strike.NotOnStrike();
                    break;
                case 2:
                    int noBall = playBall.NoBall();
                    bowlers.get(currentBowler).NoBallsBowledTracker();
                    bowlers.get(currentBowler).runsGivenTracker(noBall);
                    FirstInningsTeam.TeamScoreCalculator(noBall);
                    FirstInningsTeam.ExtraScoreTracker(noBall);
                    FirstInningsTeam.NoBallTracker();
                    if (noBall % 2 == 0) {
                        strike.changeStrike(currentOnStrike, currentNotOnStrike);
                    }
                    break;
                case 3:
                    int wideB = playBall.wideBall();
                    bowlers.get(currentBowler).WideBallsBowledTracker();
                    bowlers.get(currentBowler).runsGivenTracker(wideB);
                    FirstInningsTeam.TeamScoreCalculator(wideB);
                    FirstInningsTeam.ExtraScoreTracker(wideB);
                    FirstInningsTeam.WideBallTracker();
                    if (wideB % 2 == 0) {
                        strike.changeStrike(currentOnStrike, currentNotOnStrike);
                    }
                    break;
                default:
                    playing11.get(currentOnStrike).BallsPlayedTracker();
                    int score = playBall.runs();
                    if (score % 2 == 0) {
                        playing11.get(currentOnStrike).runTracker(score);
                        if (score == 4) {
                            playing11.get(currentOnStrike).trackNoOf4s();
                        }
                        if (score == 6) {
                            playing11.get(currentOnStrike).trackNoOf6s();
                        }
                    } else {
                        playing11.get(currentOnStrike).runTracker(score);
                        strike.changeStrike(currentOnStrike, currentNotOnStrike);
                    }
                    currentOnStrike = strike.OnStrike();
                    currentNotOnStrike = strike.NotOnStrike();
                    FirstInningsTeam.TeamScoreCalculator(score);
                    bowlers.get(currentBowler).runsGivenTracker(score);
            }
            if (FirstInningsTeam.totalWicketsGone() == 10) {
                break;
            }
        }
            //System.out.println(FirstInningsTeam.getTeam() + " scored " + FirstInningsTeam.TeamScore() + " with loss of " + FirstInningsTeam.totalWicketsGone()+ " wickets.");
            scoreBoard.viewScoreBoard(FirstInningsTeam, playing11, bowlers, "1st");
    }

    static void secondInnings(Team SecondInningsTeam , Team FirstInningsTeam, int overs){
        int currentOnStrike = 0;
        int currentNotOnStrike = 1;
        int currentBowler = 0;
        Scanner sc = new Scanner(System.in);
        Random N = new Random();
        ArrayList<Player> playing11 = new ArrayList<>();
        System.out.print("Enter the player name to bat (on strike): ");
        String name1 = sc.next();
        playing11.add(new Player(name1));

        System.out.print("Enter the player name to bat second (on non-strike end): ");
        String name2 = sc.next();
        playing11.add(new Player(name2));
        Strike strike = new Strike();
        ScoreBoard scoreBoard = new ScoreBoard();
        ArrayList<Player> bowlers = new ArrayList<>();
        System.out.print("Enter the bowler's name: ");
        String bowlerName = sc.next();
        bowlers.add(new Player(bowlerName));
        for(int ball=1;ball<=overs*6;ball++){
            if (ball%6 == 0 && ball != overs*6){
                SecondInningsTeam.currentOverCalculator();
                strike.changeStrike(currentOnStrike, currentNotOnStrike);
                currentOnStrike = strike.OnStrike();
                currentNotOnStrike = strike.NotOnStrike();
                System.out.print("Enter the bowler's name: ");
                String newBowlerName = sc.next();
                // check if bowler already present
                int flag = 0;
                for(int bowler=0;bowler<bowlers.size();bowler++){
                    if (newBowlerName.equals(bowlers.get(bowler).getName()) && bowlers.get(bowler).getOversBowled() <= (double) overs/5){
                            currentBowler = bowler;
                            flag = 1;
                            break;
                        }
                }
                if (flag==0){
                    bowlers.add(new Player(newBowlerName));
                    currentBowler = bowlers.size() - 1 ;
                }
            }
            Ball playBall = new Ball();
            int currentBall = N.nextInt(10);
            switch (currentBall){
                case 0:
                    playing11.get(currentOnStrike).BallsPlayedTracker();
                    SecondInningsTeam.TeamScoreCalculator(playBall.wicket());
                    SecondInningsTeam.wicketGoneTracker();
                    bowlers.get(currentBowler).wicketTracker();
                    System.out.print("Enter the "+ SecondInningsTeam.totalWicketsGone() + " wicket down player, to bat: ");
                    String newOnField = sc.next();
                    playing11.add(new Player(newOnField));
                    strike.changeCurrentOnStrike(FirstInningsTeam.totalWicketsGone());
                    currentOnStrike = strike.OnStrike();
                    currentNotOnStrike = strike.NotOnStrike();
                    break;
                case 2:
                    int noBall = playBall.NoBall();
                    bowlers.get(currentBowler).NoBallsBowledTracker();
                    bowlers.get(currentBowler).runsGivenTracker(noBall);
                    SecondInningsTeam.TeamScoreCalculator(noBall);
                    SecondInningsTeam.ExtraScoreTracker(noBall);
                    SecondInningsTeam.NoBallTracker();
                    if (noBall%2==0){
                        strike.changeStrike(currentOnStrike,currentNotOnStrike);
                    }
                    break;
                case 3:
                    int wideB = playBall.wideBall();
                    bowlers.get(currentBowler).WideBallsBowledTracker();
                    bowlers.get(currentBowler).runsGivenTracker(wideB);
                    SecondInningsTeam.TeamScoreCalculator(wideB);
                    SecondInningsTeam.ExtraScoreTracker(wideB);
                    SecondInningsTeam.WideBallTracker();
                    if (wideB%2==0){
                        strike.changeStrike(currentOnStrike,currentNotOnStrike);
                    }
                    break;
                default:
                    playing11.get(currentOnStrike).BallsPlayedTracker();
                    int score = playBall.runs();
                    if (score%2 == 0){
                        playing11.get(currentOnStrike).runTracker(score);
                        if (score == 4){
                            playing11.get(currentOnStrike).trackNoOf4s();
                        }
                        if (score == 6){
                            playing11.get(currentOnStrike).trackNoOf6s();
                        }
                    }
                    else {
                        playing11.get(currentOnStrike).runTracker(score);
                        strike.changeStrike(currentOnStrike, currentNotOnStrike);
                    }
                    currentOnStrike = strike.OnStrike();
                    currentNotOnStrike = strike.NotOnStrike();
                    SecondInningsTeam.TeamScoreCalculator(score);
                    bowlers.get(currentBowler).runsGivenTracker(score);
            }
            if (SecondInningsTeam.totalWicketsGone() == 10 || SecondInningsTeam.TeamScore() > FirstInningsTeam.TeamScore()){
                break;
            }
        }
        scoreBoard.viewScoreBoard(SecondInningsTeam,playing11, bowlers, "2nd");
    }

}
