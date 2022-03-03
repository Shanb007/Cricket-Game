package com.tekion.game.models;

import com.tekion.game.DBUpdateHelper.BallDBHelper;
import com.tekion.game.DBUpdateHelper.PlayerDBHelper;
import com.tekion.game.DBUpdateHelper.TeamDBHelper;
import com.tekion.game.bean.Matches;
import com.tekion.game.service.ScoreBoardService;
import com.tekion.game.service.StrikeService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Inning {
    private int currentOnStrike = 0;
    private int currentNotOnStrike = 1;
    private int currentBowler = 0;
    private final ArrayList<String> wicketFallTrack = new ArrayList<>();
    TeamDBHelper teamDBHelper = new TeamDBHelper();
    PlayerDBHelper playerDBHelper = new PlayerDBHelper();
    BallDBHelper ballDBHelper = new BallDBHelper();

    public Inning() throws SQLException, ClassNotFoundException {
    }

    public void InitiateInnings(Matches match, Team bat, Team ball, int overs, String inning) throws SQLException {
        playInnings(match,bat,ball,overs,inning);
    }

    private void playInnings(Matches match,Team BatTeam, Team BowlTeam, int overs, String inning) throws SQLException {
        Random randomOutcome = new Random();
        ArrayList<Player> playing11 = new ArrayList<>();
        ArrayList<Player> bowlers = new ArrayList<>();
        ArrayList<String> overStats = new ArrayList<>();

        setUpPlayers(BatTeam, BowlTeam, playing11,bowlers);

        System.out.println("\n"+(BatTeam.currentOver() + 1)+ " over begins: ");
        System.out.println("Batsman on Strike: "+ playing11.get(currentOnStrike).getName()+"\n"+"Batsman on Non-Striker end: "+playing11.get(currentNotOnStrike).getName());

        for (int ball = 1; ball <= overs * 6; ball++) {
            Ball playBall = new Ball();
            int currentBall = randomOutcome.nextInt(10);
            switch (currentBall) {
                case 0:
                    overStats.add("W");
                    ballDBHelper.setBallDetailsDB(match,teamDBHelper.getIdByTeamName(BatTeam.getTeam()),ball,(BatTeam.currentOver()+1),"W",playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BatTeam.getTeam()),playing11.get(currentOnStrike).getName()),playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BowlTeam.getTeam()),bowlers.get(currentBowler).getName()));
                    gotWicket(BatTeam,playing11,bowlers,playBall,overStats);
                    break;
                case 2:
                    ballDBHelper.setBallDetailsDB(match,teamDBHelper.getIdByTeamName(BatTeam.getTeam()),ball,(BatTeam.currentOver()+1),"NB",playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BatTeam.getTeam()),playing11.get(currentOnStrike).getName()),playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BowlTeam.getTeam()),bowlers.get(currentBowler).getName()));
                    NoBall(BatTeam,bowlers,playBall, overStats);
                    ball--;
                    break;
                case 3:
                    ballDBHelper.setBallDetailsDB(match,teamDBHelper.getIdByTeamName(BatTeam.getTeam()),ball,(BatTeam.currentOver()+1),"WB",playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BatTeam.getTeam()),playing11.get(currentOnStrike).getName()),playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BowlTeam.getTeam()),bowlers.get(currentBowler).getName()));
                    WideBall(BatTeam,bowlers,playBall,overStats);
                    ball--;
                    break;
                default:
                    madeRuns(match,ball,BatTeam,BowlTeam,playing11,bowlers,playBall,overStats);
            }
            if (bowlers.get(currentBowler).getBallsBowled() % 6 == 0 && ball != overs * 6 && bowlers.get(currentBowler).getBallsBowled()!=0 && currentBall != 3 && currentBall != 2) {
                getCurrentOverDetails(overStats);
                System.out.println("\nScoreBoard after "+ (BatTeam.currentOver() + 1) + " overs.");
                ScoreBoardService.showScoreBoardAfterWicketOROver(playing11,bowlers);
                overChange(BatTeam,BowlTeam,bowlers,overs);
                overStats = new ArrayList<>();
                System.out.println("\n"+(BatTeam.currentOver() + 1)+ " over begins: ");
                System.out.println("Batsman on Strike: "+ playing11.get(currentOnStrike).getName()+"\n"+"Batsman on Non-Striker end: "+playing11.get(currentNotOnStrike).getName());
            }
            if (ball == overs*6){
                getCurrentOverDetails(overStats);
                System.out.println("\nScoreBoard after "+ (BatTeam.currentOver()+1) + " overs.");
                ScoreBoardService.showScoreBoardAfterWicketOROver(playing11,bowlers);
            }
            if (inning.equals("1st") && BatTeam.totalWicketsGone() == 10) {
                getCurrentOverDetails(overStats);
                break;
            }
            if (inning.equals("2nd") && (BatTeam.totalWicketsGone() == 10 || BatTeam.TeamScore() > BowlTeam.TeamScore())){
                getCurrentOverDetails(overStats);
                break;
            }
        }
        ScoreBoardService.showScoreBoard(match,BatTeam, BowlTeam, playing11, bowlers, inning, wicketFallTrack);
    }

    private void setUpPlayers(Team BatTeam, Team BowlTeam, ArrayList<Player> playing11, ArrayList<Player> bowlers) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the player name to bat (on strike): ");
        String batsman1 = sc.next();
        playing11.add(new Player(batsman1));
        playerDBHelper.setDataPlayerDB(teamDBHelper.getIdByTeamName(BatTeam.getTeam()),batsman1);
        System.out.print("Enter the player name to bat second (Non-Striker end): ");
        String batsman2 = sc.next();
        playing11.add(new Player(batsman2));
        playerDBHelper.setDataPlayerDB(teamDBHelper.getIdByTeamName(BatTeam.getTeam()),batsman2);
        System.out.print("1st over will be bowled by: ");
        String bowlerName = sc.next();
        bowlers.add(new Player(bowlerName));
        playerDBHelper.setDataPlayerDB(teamDBHelper.getIdByTeamName(BowlTeam.getTeam()),bowlerName);
    }

    private void gotWicket(Team team, ArrayList<Player> battingTeam,ArrayList<Player> bowlingTeam,Ball playBall,ArrayList<String> overStats) throws SQLException {
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
        playerDBHelper.setDataPlayerDB(teamDBHelper.getIdByTeamName(team.getTeam()),newOnField);
        StrikeService.changeCurrentOnStrike((team.totalWicketsGone()+1));
        currentOnStrike = StrikeService.OnStrike();
        currentNotOnStrike = StrikeService.NotOnStrike();
    }


    private void madeRuns(Matches match, int ball,Team BatTeam,Team BowlTeam ,ArrayList<Player> battingTeam,ArrayList<Player> bowlingTeam,Ball playBall,ArrayList<String> overStats) throws SQLException {
        bowlingTeam.get(currentBowler).BallsBowledTracker();
        battingTeam.get(currentOnStrike).BallsPlayedTracker();
        int score = playBall.runs();
        ballDBHelper.setBallDetailsDB(match,teamDBHelper.getIdByTeamName(BatTeam.getTeam()),ball,(BatTeam.currentOver()+1), String.valueOf(score),playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BatTeam.getTeam()),battingTeam.get(currentOnStrike).getName()),playerDBHelper.getPlayerIdByTeamIdAndPlayerName(teamDBHelper.getIdByTeamName(BowlTeam.getTeam()),bowlingTeam.get(currentBowler).getName()));
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
        BatTeam.TeamScoreCalculator(score);
        overStats.add(String.valueOf(score));
    }


    private void WideBall(Team team, ArrayList<Player> bowlingTeam,Ball playBall, ArrayList<String> overStats){
        int wideB = playBall.wideBall();
        bowlingTeam.get(currentBowler).WideBallsBowledTracker();
        bowlingTeam.get(currentBowler).runsGivenTracker(wideB);
        team.TeamScoreCalculator(wideB);
        team.ExtraScoreTracker(wideB);
        team.WideBallTracker();
        if(wideB>1){
            overStats.add("WB+"+(wideB-1));
        }
        if (wideB % 2 == 0) {
            StrikeService.changeStrike(currentOnStrike, currentNotOnStrike);
        }
    }


    private void NoBall(Team team, ArrayList<Player> bowlingTeam,Ball playBall,ArrayList<String> overStats){
        int noBall = playBall.NoBall();
        bowlingTeam.get(currentBowler).NoBallsBowledTracker();
        bowlingTeam.get(currentBowler).runsGivenTracker(noBall);
        team.TeamScoreCalculator(noBall);
        team.ExtraScoreTracker(noBall);
        team.NoBallTracker();
        if(noBall>1){
            overStats.add("NB+"+(noBall-1));
        }
        if (noBall % 2 == 0) {
            StrikeService.changeStrike(currentOnStrike, currentNotOnStrike);
        }
    }


    private void overChange(Team BatTeam, Team BowlTeam, ArrayList<Player> bowlingTeam, int overs) throws SQLException {
        Scanner sc = new Scanner(System.in);
        BatTeam.currentOverCalculator();
        StrikeService.changeStrike(currentOnStrike, currentNotOnStrike);
        currentOnStrike = StrikeService.OnStrike();
        currentNotOnStrike = StrikeService.NotOnStrike();
        if ((BatTeam.currentOver()+1)==2) {
            System.out.print("\n"+(BatTeam.currentOver() + 1) + "nd over will be bowled by: ");
        }
        else if ((BatTeam.currentOver()+1)==3) {
            System.out.print("\n"+(BatTeam.currentOver() + 1) + "rd over will be bowled by: ");
        }
        else{
            System.out.print("\n"+(BatTeam.currentOver() + 1) + "th over will be bowled by: ");
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
            playerDBHelper.setDataPlayerDB(teamDBHelper.getIdByTeamName(BowlTeam.getTeam()),newBowlerName);
            currentBowler = bowlingTeam.size() - 1;
        }
    }

    private void getCurrentOverDetails(ArrayList<String> overStats){
        System.out.print("\nThis Over: \t");
        for (String overStat : overStats) {
            System.out.print(overStat+"\t\t");
        }
    }

}
