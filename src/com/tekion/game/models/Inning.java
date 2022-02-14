package com.tekion.game.models;

import java.util.Random;

public class Inning {

    static void firstInnings(Team FirstInningsTeam, int overs){
        Random N = new Random();
        int ball;
        for(ball=1;ball<=overs*6;ball++){
            if (ball%6 == 0){
                FirstInningsTeam.currentOverCalculator();
            }
            Ball playBall = new Ball();
            int currentBall = N.nextInt(10);
            switch (currentBall){
                case 0:
                    FirstInningsTeam.TeamScoreCalculator(playBall.wicket());
                    FirstInningsTeam.wicketGoneTracker();
                    break;
                case 2:
                    FirstInningsTeam.TeamScoreCalculator(playBall.NoBall());
                    FirstInningsTeam.NoBallTracker();
                    break;
                case 3:
                    FirstInningsTeam.TeamScoreCalculator(playBall.wideBall());
                    FirstInningsTeam.WideBallTracker();
                    break;
                default:
                    FirstInningsTeam.TeamScoreCalculator(playBall.runs());
            }
            if (FirstInningsTeam.totalWicketsGone() == 10){
                break;
            }
        }
        System.out.println(FirstInningsTeam.getTeam() + " scored " + FirstInningsTeam.TeamScore() + " with loss of " + FirstInningsTeam.totalWicketsGone()+ " wickets.");
        System.out.println("Extras: " + FirstInningsTeam.TotalExtras());
        System.out.println("NB: "+FirstInningsTeam.TotalNoBalls()+ " WB: "+ FirstInningsTeam.TotalWideBalls());

    }

    private void scoreBoardFirstInnings(){

    }

    static void secondInnings(Team SecondInningsTeam , Team FirstInningsTeam, int overs){
        Random N = new Random();
        int ball;
        for(ball=1;ball<=overs*6;ball++){
            if (ball%6 == 0){
                SecondInningsTeam.currentOverCalculator();
            }
            Ball playBall = new Ball();
            int currentBall = N.nextInt(10);
            switch (currentBall){
                case 0:
                    SecondInningsTeam.TeamScoreCalculator(playBall.wicket());
                    SecondInningsTeam.wicketGoneTracker();
                    break;
                case 2:
                    SecondInningsTeam.TeamScoreCalculator(playBall.NoBall());
                    SecondInningsTeam.NoBallTracker();
                    break;
                case 3:
                    SecondInningsTeam.TeamScoreCalculator(playBall.wideBall());
                    SecondInningsTeam.WideBallTracker();
                    break;
                default:
                    SecondInningsTeam.TeamScoreCalculator(playBall.runs());
            }
            if (SecondInningsTeam.totalWicketsGone() == 10 || SecondInningsTeam.TeamScore() > FirstInningsTeam.TeamScore()){
                break;
            }
        }
        System.out.println(SecondInningsTeam.getTeam() + " scored " + SecondInningsTeam.TeamScore() + " with loss of " + SecondInningsTeam.totalWicketsGone()+ " wickets.");
        System.out.println("Extras:" + SecondInningsTeam.TotalExtras());
        System.out.println("NB: "+SecondInningsTeam.TotalNoBalls()+ " WB: "+ SecondInningsTeam.TotalWideBalls());

    }


    private void scoreBoardSecondInnings(){

    }
}
