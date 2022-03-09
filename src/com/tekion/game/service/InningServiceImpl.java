package com.tekion.game.service;

import com.tekion.game.Repository.BallRepository;
import com.tekion.game.Repository.PlayerRepository;
import com.tekion.game.Repository.TeamRepository;
import com.tekion.game.bean.Matches;
import com.tekion.game.models.Ball;
import com.tekion.game.models.Player;
import com.tekion.game.models.Team;
import com.tekion.game.util.MatchUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@Service
public class InningServiceImpl implements InningsService {

    private String inning;
    private int currentOnStrike = 0;
    private int currentNotOnStrike = 1;
    private int currentBowler = 0;
    Matches match = new Matches();
    private Team OnBatTeam = new Team();
    private Team OnBallTeam = new Team();
    private int overs;
    ArrayList<Player> playing11 = new ArrayList<>();
    ArrayList<Player> bowlers = new ArrayList<>();
    private final ArrayList<String> wicketFallTrack = new ArrayList<>();
    TeamRepository teamRepository = new TeamRepository();
    PlayerRepository playerRepository = new PlayerRepository();
    BallRepository ballDBHelper = new BallRepository();
    ScoreBoardServiceImpl scoreBoardService = new ScoreBoardServiceImpl();

    public InningServiceImpl() throws SQLException, ClassNotFoundException {
    }

    public ArrayList<String> InitiateInnings(Matches match, Team bat, Team ball, int overs, String inning) {
        ArrayList<String> output = new ArrayList<>();
        this.match = match;
        OnBatTeam = bat;
        OnBallTeam = ball;
        this.inning = inning;
        this.overs = overs;
        output.add(inning+" innings: ");
        output.add("Choose Two batsman from the batting team, on strike batsman first and then the order follows.");
        output.add("Choose the Bowler from bowling team, to ball the very first over. ");
        return output;
        //playInnings(match,ball,inning);
    }

    public String addBatPlayer(int matchID, int teamID, String player) throws SQLException {
        if(matchID!=match.getMatchID() || teamID!=OnBatTeam.getTeamID()){
            return "Not Correct match/team.";
        }
        if(playing11.size()==11){
            return "Batting line up, all added.";
        }
        if(playing11.size()==0){
            playing11.add(new Player(player));
            playing11.get(currentOnStrike).setDidBat("Y");
            playerRepository.setDataPlayerDB(OnBatTeam.getTeamID(),playing11.get(currentOnStrike).getName());
            return playing11.get(currentOnStrike).getName()+" is the opening batsman(On strike) for "+OnBatTeam.getTeam();
        }
        if(playing11.size()==1){
            playing11.add(new Player(player));
            playing11.get(currentNotOnStrike).setDidBat("Y");
            playerRepository.setDataPlayerDB(OnBatTeam.getTeamID(),playing11.get(currentNotOnStrike).getName());
            return playing11.get(currentNotOnStrike).getName()+" is the opening batsman(Non-Striker) for "+OnBatTeam.getTeam();
        }
        playing11.add(new Player(player));
        if(playing11.size()==OnBatTeam.totalWicketsGone()+2) {
            MatchUtils.changeCurrentOnStrike((OnBatTeam.totalWicketsGone() + 1));
            currentOnStrike = MatchUtils.OnStrike();
            currentNotOnStrike = MatchUtils.NotOnStrike();
            playing11.get(currentOnStrike).setDidBat("Y");
        }
        playerRepository.setDataPlayerDB(OnBatTeam.getTeamID(),playing11.get(currentOnStrike).getName());
        return playing11.get(currentOnStrike).getName()+" will be next up batting at "+OnBatTeam.totalWicketsGone()+" down.";
    }

    public String overBy(int matchID, int teamID, String player) throws SQLException {
        if(matchID!=match.getMatchID() || teamID!=OnBallTeam.getTeamID()){
            return "Not Correct match/team.";
        }
        // check if bowler already present
        int flag = 0;
        for (int bowler = 0; bowler < bowlers.size(); bowler++) {
            if (player.equals(bowlers.get(bowler).getName()) && bowlers.get(bowler).getBallsBowled() <= (double) overs * 5) {
                currentBowler = bowler;
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            bowlers.add(new Player(player));
            playerRepository.setDataPlayerDB(OnBallTeam.getTeamID(),player);
            currentBowler = bowlers.size() - 1;
            bowlers.get(currentBowler).setDidBall("Y");
        }
        return (OnBatTeam.currentOver()+1)+" will be bowled by: "+bowlers.get(currentBowler).getName();
    }

    public String playOver(int matchID) throws SQLException {
        if(OnBatTeam.currentOver()==overs){
            scoreBoardService.viewScoreBoard(match,OnBatTeam, OnBallTeam, playing11, bowlers, inning, wicketFallTrack);
            return "All Over completed, begin with second innings.";
        }
        Random randomOutcome = new Random();
        ArrayList<String> overStats = new ArrayList<>();
        for (int ball = 1; ball <= 6; ball++) {
            Ball playBall = new Ball();
            int currentBall = randomOutcome.nextInt(10);
            switch (currentBall) {
                case 0:
                    overStats.add("W");
                    ballDBHelper.setBallDetailsDB(match, OnBatTeam.getTeamID(), (OnBatTeam.currentOver()+1)*ball, (OnBatTeam.currentOver() + 1), "W", playerRepository.getPlayerIdByTeamIdAndPlayerName(OnBatTeam.getTeamID(), playing11.get(currentOnStrike).getName()), playerRepository.getPlayerIdByTeamIdAndPlayerName(OnBallTeam.getTeamID(), bowlers.get(currentBowler).getName()), inning);
                    return gotWicket(playBall);
                    //break;
                case 2:
                    ballDBHelper.setBallDetailsDB(match, OnBatTeam.getTeamID(), (OnBatTeam.currentOver()+1)*ball, (OnBatTeam.currentOver() + 1), "NB", playerRepository.getPlayerIdByTeamIdAndPlayerName(OnBatTeam.getTeamID(), playing11.get(currentOnStrike).getName()), playerRepository.getPlayerIdByTeamIdAndPlayerName(OnBallTeam.getTeamID(), bowlers.get(currentBowler).getName()), inning);
                    NoBall(playBall, overStats);
                    ball--;
                    break;
                case 3:
                    ballDBHelper.setBallDetailsDB(match, OnBatTeam.getTeamID(), (OnBatTeam.currentOver()+1)*ball, (OnBatTeam.currentOver() + 1), "WB", playerRepository.getPlayerIdByTeamIdAndPlayerName(OnBatTeam.getTeamID(), playing11.get(currentOnStrike).getName()), playerRepository.getPlayerIdByTeamIdAndPlayerName(OnBallTeam.getTeamID(), bowlers.get(currentBowler).getName()), inning);
                    WideBall(playBall, overStats);
                    ball--;
                    break;
                default:
                    madeRuns(match, (OnBatTeam.currentOver()+1)*ball, playBall, overStats);
            }

            if (inning.equals("1st") && OnBatTeam.totalWicketsGone() == 10) {
                getCurrentOverDetails(overStats);
                return "This Over:- "+overStats+ " ----- "+OnBatTeam.getTeam()+" scored "+OnBatTeam.TeamScore()+" with loss of "+OnBatTeam.totalWicketsGone()+" wickets.";
            }
            if (inning.equals("2nd") && (OnBatTeam.totalWicketsGone() == 10 || OnBatTeam.TeamScore() > OnBallTeam.TeamScore())){
                getCurrentOverDetails(overStats);
                return "This Over:- "+overStats+ " ----- "+OnBatTeam.getTeam()+" scored "+OnBatTeam.TeamScore()+" with loss of "+OnBatTeam.totalWicketsGone()+" wickets.";
            }
        }
        System.out.println("\nScoreBoard after "+ (OnBatTeam.currentOver() + 1) + " overs.");
        scoreBoardService.viewScoreBoardAfterWicketOROver(playing11,bowlers);
        return "This Over:- "+overStats;
    }

//    private void playInnings() throws SQLException {
//        Random randomOutcome = new Random();
//        ArrayList<String> overStats = new ArrayList<>();
//        setUpPlayers();
//
//        System.out.println("\n"+(OnBatTeam.currentOver() + 1)+ " over begins: ");
//        System.out.println("Batsman on Strike: "+ playing11.get(currentOnStrike).getName()+"\n"+"Batsman on Non-Striker end: "+playing11.get(currentNotOnStrike).getName());
//
//        for (int ball = 1; ball <= overs * 6; ball++) {
//            Ball playBall = new Ball();
//            int currentBall = randomOutcome.nextInt(10);
//            switch (currentBall) {
//                case 0:
//                    overStats.add("W");
//                    ballDBHelper.setBallDetailsDB(match,teamRepository.getIdByTeamName(OnBatTeam.getTeam()),ball,(OnBatTeam.currentOver()+1),"W",playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(OnBatTeam.getTeam()),playing11.get(currentOnStrike).getName()),playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(OnBallTeam.getTeam()),bowlers.get(currentBowler).getName()),inning);
//                    gotWicket(playBall);
//                    break;
//                case 2:
//                    ballDBHelper.setBallDetailsDB(match,teamRepository.getIdByTeamName(OnBatTeam.getTeam()),ball,(OnBatTeam.currentOver()+1),"NB",playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(OnBatTeam.getTeam()),playing11.get(currentOnStrike).getName()),playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(OnBallTeam.getTeam()),bowlers.get(currentBowler).getName()),inning);
//                    NoBall(playBall, overStats);
//                    ball--;
//                    break;
//                case 3:
//                    ballDBHelper.setBallDetailsDB(match,teamRepository.getIdByTeamName(OnBatTeam.getTeam()),ball,(OnBatTeam.currentOver()+1),"WB",playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(OnBatTeam.getTeam()),playing11.get(currentOnStrike).getName()),playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(OnBallTeam.getTeam()),bowlers.get(currentBowler).getName()),inning);
//                    WideBall(playBall,overStats);
//                    ball--;
//                    break;
//                default:
//                    madeRuns(match,ball,playBall,overStats,inning);
//            }
//            if (bowlers.get(currentBowler).getBallsBowled() % 6 == 0 && ball != overs * 6 && bowlers.get(currentBowler).getBallsBowled()!=0 && currentBall != 3 && currentBall != 2) {
//                getCurrentOverDetails(overStats);
//                System.out.println("\nScoreBoard after "+ (OnBatTeam.currentOver() + 1) + " overs.");
//                scoreBoardService.viewScoreBoardAfterWicketOROver(playing11,bowlers);
//                overChange();
//                overStats = new ArrayList<>();
//                System.out.println("\n"+(OnBatTeam.currentOver() + 1)+ " over begins: ");
//                System.out.println("Batsman on Strike: "+ playing11.get(currentOnStrike).getName()+"\n"+"Batsman on Non-Striker end: "+playing11.get(currentNotOnStrike).getName());
//            }
//            if (ball == overs*6){
//                getCurrentOverDetails(overStats);
//                System.out.println("\nScoreBoard after "+ (OnBatTeam.currentOver()+1) + " overs.");
//                scoreBoardService.viewScoreBoardAfterWicketOROver(playing11,bowlers);
//            }
//            if (inning.equals("1st") && OnBatTeam.totalWicketsGone() == 10) {
//                getCurrentOverDetails(overStats);
//                break;
//            }
//            if (inning.equals("2nd") && (OnBatTeam.totalWicketsGone() == 10 || OnBatTeam.TeamScore() > OnBallTeam.TeamScore())){
//                getCurrentOverDetails(overStats);
//                break;
//            }
//        }
//        System.out.println(playing11.size());
//        scoreBoardService.viewScoreBoard(match,OnBatTeam, OnBallTeam, playing11, bowlers, inning, wicketFallTrack);
//    }

//    private void setUpPlayers() throws SQLException {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter the player name to bat (on strike): ");
//        String batsman1 = sc.next();
//        playing11.add(new Player(batsman1));
//        playing11.get(0).setDidBat("Y");
//        playerRepository.setDataPlayerDB(teamRepository.getIdByTeamName(OnBatTeam.getTeam()),playing11.get(0).getName());
//        System.out.print("Enter the player name to bat second (Non-Striker end): ");
//       String batsman2 = sc.next();
//        playing11.add(new Player(batsman2));
//        playing11.get(1).setDidBat("Y");
//        playerRepository.setDataPlayerDB(teamRepository.getIdByTeamName(OnBatTeam.getTeam()),playing11.get(1).getName());
//        System.out.print("1st over will be bowled by: ");
//        String bowlerName = sc.next();
//       bowlers.add(new Player(bowlerName));
//        bowlers.get(0).setDidBall("Y");
//        playerRepository.setDataPlayerDB(teamRepository.getIdByTeamName(OnBallTeam.getTeam()),bowlers.get(0).getName());
//    }

    private String gotWicket(Ball playBall) {
        bowlers.get(currentBowler).BallsBowledTracker();
        System.out.println("\nWICKET !!!! and it's a wicket " + playing11.get(currentOnStrike).getName() + " has to make his way back to pavilion.");
        playing11.get(currentOnStrike).setWicketTakenBy(bowlers.get(currentBowler).getName());
        //        Scanner sc = new Scanner(System.in);
        playing11.get(currentOnStrike).BallsPlayedTracker();
        OnBatTeam.TeamScoreCalculator(playBall.wicket());
        OnBatTeam.wicketGoneTracker();
        bowlers.get(currentBowler).wicketTracker();
        wicketFallTrack.add(OnBatTeam.TeamScore() +"-"+OnBatTeam.totalWicketsGone());
        System.out.println("\nScoreboard after the fall of " + OnBatTeam.totalWicketsGone()+" wickets: ");
        scoreBoardService.viewScoreBoardAfterWicketOROver(playing11,bowlers);
//        System.out.print("\nEnter the " + OnBatTeam.totalWicketsGone() + " wicket down player, to bat: ");
        if(playing11.size()>=OnBatTeam.totalWicketsGone()+2){
            MatchUtils.changeCurrentOnStrike((OnBatTeam.totalWicketsGone()+1));
            currentOnStrike = MatchUtils.OnStrike();
            currentNotOnStrike = MatchUtils.NotOnStrike();
            playing11.get(currentOnStrike).setDidBat("Y");
            return playing11.get(currentOnStrike).getName()+" next on Strike.";
        }
        return "It's a wicket. "+playing11.get(currentOnStrike)+ " has to make hs way back to pavilion. Kindly Enter the next batsman.";
//        String newOnField = sc.next();
//        playing11.add(new Player(newOnField));
//        playerRepository.setDataPlayerDB(teamRepository.getIdByTeamName(OnBatTeam.getTeam()),playing11.get(currentOnStrike).getName());
       // MatchUtils.changeCurrentOnStrike((OnBatTeam.totalWicketsGone()+1));
    }


    private void madeRuns(Matches match, int ball,Ball playBall,ArrayList<String> overStats) throws SQLException {
        bowlers.get(currentBowler).BallsBowledTracker();
        //System.out.println("debug size, currentOnStrike: "+currentOnStrike);
        playing11.get(currentOnStrike).BallsPlayedTracker();
        int score = playBall.runs();
        ballDBHelper.setBallDetailsDB(match,teamRepository.getIdByTeamName(OnBatTeam.getTeam()),ball,(OnBatTeam.currentOver()+1), String.valueOf(score),playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(OnBatTeam.getTeam()),playing11.get(currentOnStrike).getName()),playerRepository.getPlayerIdByTeamIdAndPlayerName(teamRepository.getIdByTeamName(OnBallTeam.getTeam()),bowlers.get(currentBowler).getName()),inning);
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
            MatchUtils.changeStrike(currentOnStrike, currentNotOnStrike);
        }
        currentOnStrike = MatchUtils.OnStrike();
        currentNotOnStrike = MatchUtils.NotOnStrike();
        bowlers.get(currentBowler).runsGivenTracker(score);
        OnBatTeam.TeamScoreCalculator(score);
        overStats.add(String.valueOf(score));
    }


    private void WideBall(Ball playBall, ArrayList<String> overStats){
        int wideB = playBall.wideBall();
        bowlers.get(currentBowler).WideBallsBowledTracker();
        bowlers.get(currentBowler).runsGivenTracker(wideB);
        OnBatTeam.TeamScoreCalculator(wideB);
        OnBatTeam.ExtraScoreTracker(wideB);
        OnBatTeam.WideBallTracker();
        if(wideB>1){
            overStats.add("WB+"+(wideB-1));
        }
        if (wideB % 2 == 0) {
            MatchUtils.changeStrike(currentOnStrike, currentNotOnStrike);
        }
    }


    private void NoBall(Ball playBall,ArrayList<String> overStats){
        int noBall = playBall.NoBall();
        bowlers.get(currentBowler).NoBallsBowledTracker();
        bowlers.get(currentBowler).runsGivenTracker(noBall);
        OnBatTeam.TeamScoreCalculator(noBall);
        OnBatTeam.ExtraScoreTracker(noBall);
        OnBatTeam.NoBallTracker();
        if(noBall>1){
            overStats.add("NB+"+(noBall-1));
        }
        if (noBall % 2 == 0) {
            MatchUtils.changeStrike(currentOnStrike, currentNotOnStrike);
        }
    }

//
//    private void overChange() throws SQLException {
//        OnBatTeam.currentOverCalculator();
//        MatchUtils.changeStrike(currentOnStrike, currentNotOnStrike);
//        currentOnStrike = MatchUtils.OnStrike();
//        currentNotOnStrike = MatchUtils.NotOnStrike();
//        if ((OnBatTeam.currentOver()+1)==2) {
//            System.out.print("\n"+(OnBatTeam.currentOver() + 1) + "nd over will be bowled by: ");
//        }
//        else if ((OnBatTeam.currentOver()+1)==3) {
//            System.out.print("\n"+(OnBatTeam.currentOver() + 1) + "rd over will be bowled by: ");
//        }
//        else{
//            System.out.print("\n"+(OnBatTeam.currentOver() + 1) + "th over will be bowled by: ");
//        }
//        Scanner sc  =new Scanner(System.in);
//        String player = sc.next();
//        int flag = 0;
//        for (int bowler = 0; bowler < bowlers.size(); bowler++) {
//            if (player.equals(bowlers.get(bowler).getName()) && bowlers.get(bowler).getBallsBowled() <= (double) overs * 5) {
//                currentBowler = bowler;
//                flag = 1;
//                break;
//            }
//        }
//        if (flag == 0) {
//            bowlers.add(new Player(player));
//            playerRepository.setDataPlayerDB(teamRepository.getIdByTeamName(OnBallTeam.getTeam()),player);
//            currentBowler = bowlers.size() - 1;
//            bowlers.get(currentBowler).setDidBall("Y");
//        }
//    }

    private void getCurrentOverDetails(ArrayList<String> overStats){
        System.out.print("\nThis Over: \t");
        for (String overStat : overStats) {
            System.out.print(overStat+"\t\t");
        }

    }

}
