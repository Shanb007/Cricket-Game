package com.tekion.game.service;

import com.tekion.game.Repository.BallRepository;
import com.tekion.game.Repository.MatchRepository;
import com.tekion.game.Repository.PlayerRepository;
import com.tekion.game.Repository.TeamRepository;
import com.tekion.game.models.Ball;
import com.tekion.game.models.Player;
import com.tekion.game.models.Team;
import com.tekion.game.util.MatchUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

@Service
public class InningServiceImpl implements InningsService {

    private int currentOnStrike = 0;
    private int currentNotOnStrike = 1;
    Player bowler;
    private Team OnBatTeam;
    private Team OnBallTeam;
    ArrayList<Player> onFieldBat = new ArrayList<>();
    ArrayList<String> overStats = new ArrayList<>();
    MatchRepository matchRepository = new MatchRepository();
    TeamRepository teamRepository = new TeamRepository();
    PlayerRepository playerRepository = new PlayerRepository();
    BallRepository ballRepository = new BallRepository();
    Random randomOutcome = new Random();

    public InningServiceImpl() throws SQLException, ClassNotFoundException {
    }

    public ArrayList<String> InitiateInnings(int matchID, Team bat, Team ball, int overs, String inning){
        ArrayList<String> output = new ArrayList<>();
        this.OnBatTeam = bat;
        this.OnBallTeam = ball;
        output.add(inning+" innings: ");
        output.add("Choose Two batsman from the " +OnBatTeam.getTeam()+", on strike batsman first and then the order follows.");
        output.add("Choose the Bowler from "+ OnBallTeam.getTeam()+", to ball the very first over. ");
        return output;
        //playInnings(match,ball,inning);
    }

    public String addFirstTwoBatsman(int matchID, int BatTeamID, String Bat1, String Bat2) throws SQLException {
        onFieldBat.add(new Player(Bat1));
        onFieldBat.get(currentOnStrike).setDidBat("Y");
        playerRepository.setDataPlayerDB(BatTeamID,Bat1);
        onFieldBat.add(new Player(Bat2));
        onFieldBat.get(currentNotOnStrike).setDidBat("Y");
        playerRepository.setDataPlayerDB(BatTeamID,Bat2);
        playerRepository.setDataPlayerMatchDetailsDB(playerRepository.getPlayerIdByTeamIdAndPlayerName(BatTeamID,Bat1),BatTeamID,matchID,onFieldBat.get(currentOnStrike),"Bat");
        playerRepository.setDataPlayerMatchDetailsDB(playerRepository.getPlayerIdByTeamIdAndPlayerName(BatTeamID,Bat2),BatTeamID,matchID,onFieldBat.get(currentNotOnStrike),"Bat");
        return "OnStrike: "+onFieldBat.get(currentOnStrike).getName()+" Non Striker end: "+onFieldBat.get(currentNotOnStrike).getName();
    }

    public String addBatPlayerAfterWicket(int matchID, int teamID, String player) throws SQLException {
        OnBatTeam = teamRepository.getTeamScorecardRequested(matchID,teamID);
        if(OnBatTeam.currentOver()==matchRepository.getOversByMatchID(matchID)){
            return "Innings Finished. Can't initiate other Batsman.";
        }
        if(OnBatTeam.totalWicketsGone()==10){
            return "All OUT. Can't add more players to bat.";
        }
        Player batAfterWicket = new Player(player);
        playerRepository.setDataPlayerDB(OnBatTeam.getTeamID(),player);
        batAfterWicket.setDidBat("Y");
        playerRepository.setDataPlayerMatchDetailsDB(playerRepository.getPlayerIdByTeamIdAndPlayerName(teamID,player),teamID,matchID,batAfterWicket,"Bat");
        return batAfterWicket.getName()+" will be next up batting at "+OnBatTeam.totalWicketsGone()+" down.";
    }

    public String overBy(int matchID, int BallTeamID, String player) throws SQLException {
        // check if bowler already present
        OnBatTeam = teamRepository.getTeamScorecardRequested(matchID,matchRepository.getOtherTeamIDbyMatchIDAndOneTeamID(matchID,BallTeamID));
        if(OnBatTeam.currentOver()==matchRepository.getOversByMatchID(matchID)){
            return "Innings Finished. Can't initiate other Bowler.";
        }
        if(OnBatTeam.totalWicketsGone()==10){
            return "All OUT. Can't add more players to bowl.";
        }
        playerRepository.setDataPlayerDB(BallTeamID,player);
        int currentBowlerID = playerRepository.getPlayerIdByTeamIdAndPlayerName(BallTeamID,player);
        if(!playerRepository.checkIfBowlerAlreadyInMatch(currentBowlerID,matchID)){
            bowler = new Player(player);
            bowler.setDidBall("Y");
            playerRepository.setDataPlayerMatchDetailsDB(currentBowlerID,BallTeamID,matchID,bowler,"ball");
        }
        else {
            //get the bowler object from db.
            bowler = playerRepository.getNextOverBowlerRequested(matchID, BallTeamID, player);
        }
        return "Next Over will be bowled by: "+bowler.getName()+" Bowler ID: "+currentBowlerID;
    }

    public String playOver(int matchID, String inning, int BatTeamID, int BallTeamID, int ballingPlayerID) throws SQLException {
        int startBall = 1;
        if(inning.equals("2nd")){
            OnBallTeam = teamRepository.getTeamScorecardRequested(matchID,BallTeamID);
        }
        OnBatTeam = teamRepository.getTeamScorecardRequested(matchID,BatTeamID);
        bowler = playerRepository.getNextOverBowlerRequested(matchID,ballingPlayerID);
        onFieldBat = playerRepository.getBatsmanOnField(matchID,BatTeamID);
        onFieldBat.get(currentOnStrike).setDidBat("Y");
        onFieldBat.get(currentNotOnStrike).setDidBat("Y");

        if(OnBatTeam.currentOver()==matchRepository.getOversByMatchID(matchID)){
            //scoreBoardService.viewScoreBoard(match,OnBatTeam, OnBallTeam, playing11, bowlers, inning, wicketFallTrack);
            return "All Over completed, begin with second innings.";
        }
        //from per ball details outcome, check outcome. if W cast overStats and set startBall to last ball number.
        //reform this part of the code.
        if(ballRepository.checkIfMatchFirstBall(matchID,BatTeamID,inning)){
            if(ballRepository.getLastBallPlayedOutcome(matchID,BatTeamID).equals("W") && ballRepository.getLastBallNumberPlayed(matchID,BatTeamID)%6 != 0){
                overStats = ballRepository.getOverOutcomeTillWicketFell(matchID,BatTeamID);
                startBall = ballRepository.getLastBallNumberPlayed(matchID,BatTeamID)%6+1;
            }
            if(ballRepository.getLastBallPlayedOutcome(matchID,BatTeamID).equals("W") && ballRepository.getLastBallNumberPlayed(matchID,BatTeamID)%6 == 0 && bowler.getBallsBowled()%6==0 && bowler.getBallsBowled()!=0){
                overStats = ballRepository.getOverOutcomeTillWicketFell(matchID,BatTeamID);
                startBall = 7;
            }
        }

        for (int ball = startBall; ball <= 6; ball++) {
            Ball playBall = new Ball();
            int currentBall = randomOutcome.nextInt(10);
            switch (currentBall) {
                case 0:
                    overStats.add("W");
                    ballRepository.setBallDetailsDB(matchID, OnBatTeam.getTeamID(), ((OnBatTeam.currentOver())*ball)+ball, (OnBatTeam.currentOver() + 1), "W", onFieldBat.get(currentOnStrike).getPlayerID(), bowler.getPlayerID(),inning);
                    return gotWicket(matchID,playBall,BallTeamID, ((OnBatTeam.currentOver())*ball)+ball);
                //break;
                case 2:
                    ballRepository.setBallDetailsDB(matchID, OnBatTeam.getTeamID(), ((OnBatTeam.currentOver())*ball)+ball, (OnBatTeam.currentOver() + 1), "NB", onFieldBat.get(currentOnStrike).getPlayerID(), bowler.getPlayerID(), inning);
                    NoBall(playBall, overStats);
                    ball--;
                    break;
                case 3:
                    ballRepository.setBallDetailsDB(matchID, OnBatTeam.getTeamID(), ((OnBatTeam.currentOver())*ball)+ball, (OnBatTeam.currentOver() + 1), "WB", onFieldBat.get(currentOnStrike).getPlayerID(), bowler.getPlayerID(),inning);
                    WideBall(playBall, overStats);
                    ball--;
                    break;
                default:
                    madeRuns(matchID, (OnBatTeam.currentOver())*ball+ball, playBall, overStats, inning);
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
        //scoreBoardService.viewScoreBoardAfterWicketOROver(playing11,bowlers);
        OnBatTeam.currentOverCalculator();
        //update db's.
        teamRepository.updateTeamScorecardAfterOver(matchID,BatTeamID,OnBatTeam);
        playerRepository.updateBowlerStatsInDB(matchID,BallTeamID,bowler);
        playerRepository.updateOnFieldBatsmanStatsIDB(matchID,BatTeamID,onFieldBat);
        if(OnBatTeam.currentOver()==matchRepository.getOversByMatchID(matchID)){
            return "This Over:- "+overStats+". "+inning+" is over."+OnBatTeam.getTeam()+" scored "+OnBatTeam.TeamScore()+" with loss of "+OnBatTeam.totalWicketsGone()+" wickets.";
        }
        return "This Over:- "+overStats;
    }


    private String gotWicket(int matchID, Ball playBall,int BallTeamID, int ball) throws SQLException {
        bowler.BallsBowledTracker();
        System.out.println("\nWICKET !!!! and it's a wicket " + onFieldBat.get(currentOnStrike).getName() + " has to make his way back to pavilion.");
        onFieldBat.get(currentOnStrike).setWicketTakenBy(bowler.getName());
        //        Scanner sc = new Scanner(System.in);
        onFieldBat.get(currentOnStrike).BallsPlayedTracker();
        OnBatTeam.TeamScoreCalculator(playBall.wicket());
        OnBatTeam.wicketGoneTracker();
        bowler.wicketTracker();
        teamRepository.updateTeamScorecardAfterOver(matchID,OnBatTeam.getTeamID(),OnBatTeam);
        //wicketFallTrack.add(OnBatTeam.TeamScore() +"-"+OnBatTeam.totalWicketsGone());
        // System.out.println("\nScoreboard after the fall of " + OnBatTeam.totalWicketsGone()+" wickets: ");
        // scoreBoardService.viewScoreBoardAfterWicketOROver(playing11,bowlers);
//        System.out.print("\nEnter the " + OnBatTeam.totalWicketsGone() + " wicket down player, to bat: ");
        if(onFieldBat.size()>=OnBatTeam.totalWicketsGone()+2){
            MatchUtils.changeCurrentOnStrike((OnBatTeam.totalWicketsGone()+1));
            currentOnStrike = MatchUtils.OnStrike();
            currentNotOnStrike = MatchUtils.NotOnStrike();
            onFieldBat.get(currentOnStrike).setDidBat("Y");
            playerRepository.updateBowlerStatsInDB(matchID,BallTeamID,bowler);
            playerRepository.updateOnFieldBatsmanStatsIDB(matchID,OnBatTeam.getTeamID(),onFieldBat);
            return  "It's a Wicket. "+onFieldBat.get(currentOnStrike).getName()+" next on Strike. Continue to play over.";
        }
        playerRepository.updateBowlerStatsInDB(matchID,BallTeamID,bowler);
        playerRepository.updateOnFieldBatsmanStatsIDB(matchID,OnBatTeam.getTeamID(),onFieldBat);
        return "It's a wicket at "+ball+" ball of this over. "+onFieldBat.get(currentOnStrike).getName()+ " has to make hs way back to pavilion. Kindly Enter the next batsman and then continue the over.";
//        String newOnField = sc.next();
//        playing11.add(new Player(newOnField));
//        playerRepository.setDataPlayerDB(teamRepository.getIdByTeamName(OnBatTeam.getTeam()),playing11.get(currentOnStrike).getName());
        // MatchUtils.changeCurrentOnStrike((OnBatTeam.totalWicketsGone()+1));
    }


    private void madeRuns(int matchID, int ball,Ball playBall,ArrayList<String> overStats, String inning) throws SQLException {
        bowler.BallsBowledTracker();
        //System.out.println("debug size, currentOnStrike: "+currentOnStrike);
        onFieldBat.get(currentOnStrike).BallsPlayedTracker();
        int score = playBall.runs();
        ballRepository.setBallDetailsDB(matchID,OnBatTeam.getTeamID(),ball,(OnBatTeam.currentOver()+1), String.valueOf(score),onFieldBat.get(currentOnStrike).getPlayerID(), bowler.getPlayerID(),inning);
        if (score % 2 == 0) {
            onFieldBat.get(currentOnStrike).runTracker(score);
            if (score == 4) {
                onFieldBat.get(currentOnStrike).trackNoOf4s();
            }
            if (score == 6) {
                onFieldBat.get(currentOnStrike).trackNoOf6s();
            }
        } else {
            onFieldBat.get(currentOnStrike).runTracker(score);
            MatchUtils.changeStrike(currentOnStrike, currentNotOnStrike);
        }
        currentOnStrike = MatchUtils.OnStrike();
        currentNotOnStrike = MatchUtils.NotOnStrike();
        bowler.runsGivenTracker(score);
        OnBatTeam.TeamScoreCalculator(score);
        overStats.add(String.valueOf(score));
    }


    private void WideBall(Ball playBall, ArrayList<String> overStats){
        int wideB = playBall.wideBall();
        bowler.WideBallsBowledTracker();
        bowler.runsGivenTracker(wideB);
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
        bowler.NoBallsBowledTracker();
        bowler.runsGivenTracker(noBall);
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

    private void getCurrentOverDetails(ArrayList<String> overStats){
        System.out.print("\nThis Over: \t");
        for (String overStat : overStats) {
            System.out.print(overStat+"\t\t");
        }

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


