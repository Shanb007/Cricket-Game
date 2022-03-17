package com.tekion.game.service;

import com.tekion.game.Repository.BallRepository;
import com.tekion.game.Repository.MatchRepository;
import com.tekion.game.Repository.PlayerRepository;
import com.tekion.game.Repository.TeamRepository;
import com.tekion.game.models.Ball;
import com.tekion.game.models.Player;
import com.tekion.game.models.Team;
import com.tekion.game.util.MatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InningServiceImpl implements InningsService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private BallRepository ballRepository;
    Random randomOutcome = new Random();

    public InningServiceImpl(){
    }

    public ArrayList<String> InitiateInnings(String bat, String ball, int overs, String inning){
        ArrayList<String> output = new ArrayList<>();
        output.add(inning+" innings: ");
        output.add("Choose Two batsman from the " +  bat + ", on strike batsman first and then the order follows.");
        output.add("Choose the Bowler from "+ ball +", to ball the very first over. ");
        return output;
        //playInnings(match,ball,inning);
    }

    public String addFirstTwoBatsman(int matchID, int BatTeamID, String Bat1, String Bat2) throws SQLException {
        List<Player> onFieldBat = new ArrayList<>();
        onFieldBat.add(new Player(Bat1));
        onFieldBat.get(0).setDidBat("Y");
        playerRepository.setDataPlayerDB(BatTeamID,Bat1);
        onFieldBat.add(new Player(Bat2));
        onFieldBat.get(1).setDidBat("Y");
        playerRepository.setDataPlayerDB(BatTeamID,Bat2);
        playerRepository.setDataPlayerMatchDetailsDB(playerRepository.getPlayerIdByTeamIdAndPlayerName(BatTeamID,Bat1),BatTeamID,matchID,onFieldBat.get(0),"Bat");
        playerRepository.setDataPlayerMatchDetailsDB(playerRepository.getPlayerIdByTeamIdAndPlayerName(BatTeamID,Bat2),BatTeamID,matchID,onFieldBat.get(1),"Bat");
        return "OnStrike: "+onFieldBat.get(0).getName()+" Non Striker end: "+onFieldBat.get(1).getName();
    }

    public String addBatPlayerAfterWicket(int matchID, int teamID, String player) throws SQLException {
        Team OnBatTeam;
        OnBatTeam = teamRepository.getTeamScorecardRequested(matchID,teamID);
        System.out.println(OnBatTeam.getTeamID()+"    "+OnBatTeam.totalWicketsGone()+"   "+OnBatTeam.TeamScore());
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

    public String overBy(int matchID, int BallTeamID, String player) throws SQLException{
        Player bowler;
        // check if bowler already present
        Team OnBatTeam;
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
        }
        else {
            //get the bowler object from db.
            bowler = playerRepository.getNextOverBowlerRequested(matchID, BallTeamID, player);
        }
        bowler.setDidBall("Y");
        playerRepository.setDataPlayerMatchDetailsDB(currentBowlerID,BallTeamID,matchID,bowler,"Ball");
        return "Next Over will be bowled by: "+bowler.getName()+" Bowler ID: "+currentBowlerID;
    }

    public String playOver(int matchID, String inning, int BatTeamID, int BallTeamID, int ballingPlayerID) throws SQLException{
        List<Player> onFieldBat;
        int currentOnStrike = 0;
        int currentNotOnStrike = 1;
        Team OnBatTeam;
        Team OnBallTeam = null;
        Player bowler;
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
                startBall = ballRepository.getLastBallNumberPlayed(matchID,BatTeamID)%6+1;
            }
            if(ballRepository.getLastBallPlayedOutcome(matchID,BatTeamID).equals("W") && ballRepository.getLastBallNumberPlayed(matchID,BatTeamID)%6 == 0 && bowler.getBallsBowled()%6==0 && bowler.getBallsBowled()!=0){
                startBall = 7;
            }
        }

        for (int ball = startBall; ball <= 6; ball++) {
            Ball playBall = new Ball();
            OnBatTeam = teamRepository.getTeamScorecardRequested(matchID,BatTeamID);
            int currentBall = randomOutcome.nextInt(10);
            switch (currentBall) {
                case 0:
                    ballRepository.setBallDetailsDB(matchID, OnBatTeam.getTeamID(), ((OnBatTeam.currentOver())*6)+ball, (OnBatTeam.currentOver() + 1), "W", onFieldBat.get(currentOnStrike).getPlayerID(), bowler.getPlayerID(),inning);
                    return gotWicket(matchID,playBall,BallTeamID, ball, OnBatTeam,bowler,currentOnStrike,currentNotOnStrike,onFieldBat);
                //break;
                case 2:
                    int noBall = playBall.NoBall();
                    ballRepository.setBallDetailsDB(matchID, OnBatTeam.getTeamID(), ((OnBatTeam.currentOver())*6)+ball, (OnBatTeam.currentOver() + 1), "NB+"+(noBall-1), onFieldBat.get(currentOnStrike).getPlayerID(), bowler.getPlayerID(), inning);
                    NoBall(matchID,OnBatTeam,BatTeamID,BallTeamID,bowler,noBall,onFieldBat);
                    if (noBall % 2 == 0) {
                        MatchUtils.changeStrike(currentOnStrike, currentNotOnStrike);
                        currentOnStrike = MatchUtils.OnStrike();
                        currentNotOnStrike = MatchUtils.NotOnStrike();
                    }
                    ball--;
                    break;
                case 3:
                    int wideB = playBall.wideBall();
                    ballRepository.setBallDetailsDB(matchID, OnBatTeam.getTeamID(), ((OnBatTeam.currentOver())*6)+ball, (OnBatTeam.currentOver() + 1), "WB+"+(wideB-1), onFieldBat.get(currentOnStrike).getPlayerID(), bowler.getPlayerID(),inning);
                    WideBall(matchID,OnBatTeam,BatTeamID,BallTeamID,bowler,wideB,onFieldBat);
                    if (wideB % 2 == 0) {
                        MatchUtils.changeStrike(currentOnStrike, currentNotOnStrike);
                        currentOnStrike = MatchUtils.OnStrike();
                        currentNotOnStrike = MatchUtils.NotOnStrike();
                    }
                    ball--;
                    break;
                default:
                    int score = playBall.runs();
                    onFieldBat.get(currentOnStrike).BallsPlayedTracker();
                    ballRepository.setBallDetailsDB(matchID,OnBatTeam.getTeamID(),((OnBatTeam.currentOver())*6)+ball,(OnBatTeam.currentOver()+1), String.valueOf(score),onFieldBat.get(currentOnStrike).getPlayerID(), bowler.getPlayerID(),inning);
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
                    madeRuns(matchID,OnBatTeam ,score,BatTeamID,BallTeamID,bowler,onFieldBat);
            }

            if (inning.equals("1st") && OnBatTeam.totalWicketsGone() == 10) {
                return "This Over:- "+ballRepository.getOverInfo(matchID,inning,OnBatTeam.currentOver()+1)+ " ----- "+OnBatTeam.getTeam()+" scored "+OnBatTeam.TeamScore()+" with loss of "+OnBatTeam.totalWicketsGone()+" wickets.";
            }
            if (inning.equals("2nd") && (OnBatTeam.totalWicketsGone() == 10 || OnBatTeam.TeamScore() > OnBallTeam.TeamScore())){
                return "This Over:- "+ballRepository.getOverInfo(matchID,inning,OnBatTeam.currentOver()+1)+ " ----- "+OnBatTeam.getTeam()+" scored "+OnBatTeam.TeamScore()+" with loss of "+OnBatTeam.totalWicketsGone()+" wickets.";
            }
        }
        System.out.println("\nScoreBoard after "+ (OnBatTeam.currentOver() + 1) + " overs.");

        OnBatTeam = teamRepository.getTeamScorecardRequested(matchID,BatTeamID);
        OnBatTeam.currentOverCalculator();
        //update db's.
        teamRepository.updateTeamScorecardAfterOver(matchID,BatTeamID,OnBatTeam);
        if(OnBatTeam.currentOver()==matchRepository.getOversByMatchID(matchID)){
            return "This Over:- "+ballRepository.getOverInfo(matchID,inning,OnBatTeam.currentOver())+". "+inning+" inning is over. "+teamRepository.getTeamName(OnBatTeam.getTeamID())+" scored "+OnBatTeam.TeamScore()+" with loss of "+OnBatTeam.totalWicketsGone()+" wickets.";
        }
        return "This Over:- "+ballRepository.getOverInfo(matchID,inning,OnBatTeam.currentOver())+". Bowl next over.";
    }


    private String gotWicket(int matchID, Ball playBall,int BallTeamID, int ball, Team OnBatTeam, Player bowler, int currentOnStrike, int currentNotOnStrike, List<Player>onFieldBat){
        bowler.BallsBowledTracker();
        System.out.println("\nWICKET !!!! and it's a wicket " + onFieldBat.get(currentOnStrike).getName() + " has to make his way back to pavilion.");
        onFieldBat.get(currentOnStrike).setWicketTakenBy(bowler.getName());
        onFieldBat.get(currentOnStrike).BallsPlayedTracker();
        OnBatTeam.TeamScoreCalculator(playBall.wicket());
        OnBatTeam.wicketGoneTracker();
        bowler.wicketTracker();
        teamRepository.updateTeamScorecardAfterOver(matchID,OnBatTeam.getTeamID(),OnBatTeam);
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
        return "It's a wicket at "+ball+" ball of this over. "+onFieldBat.get(currentOnStrike).getName()+ " has to make his way back to pavilion. Kindly Enter the next batsman and then continue the over.";
    }


    private void madeRuns(int matchID,Team OnBatTeam ,int score,int BatTeamID, int BallTeamID, Player bowler, List<Player>onFieldBat){
        bowler.BallsBowledTracker();
        OnBatTeam.TeamScoreCalculator(score);
        bowler.runsGivenTracker(score);
        teamRepository.updateTeamScorecardAfterOver(matchID,BatTeamID,OnBatTeam);
        playerRepository.updateBowlerStatsInDB(matchID,BallTeamID,bowler);
        playerRepository.updateOnFieldBatsmanStatsIDB(matchID,BatTeamID,onFieldBat);
    }


    private void WideBall(int matchID, Team OnBatTeam,int BatTeamID, int BallTeamID, Player bowler ,int wideB, List<Player>onFieldBat){
        bowler.WideBallsBowledTracker();
        bowler.runsGivenTracker(wideB);
        OnBatTeam.TeamScoreCalculator(wideB);
        OnBatTeam.ExtraScoreTracker(wideB);
        OnBatTeam.WideBallTracker();
        teamRepository.updateTeamScorecardAfterOver(matchID,BatTeamID,OnBatTeam);
        playerRepository.updateBowlerStatsInDB(matchID,BallTeamID,bowler);
        playerRepository.updateOnFieldBatsmanStatsIDB(matchID,BatTeamID,onFieldBat);
    }


    private void NoBall(int matchID, Team OnBatTeam,int BatTeamID, int BallTeamID, Player bowler ,int noBall, List<Player>onFieldBat){
        bowler.NoBallsBowledTracker();
        bowler.runsGivenTracker(noBall);
        OnBatTeam.TeamScoreCalculator(noBall);
        OnBatTeam.ExtraScoreTracker(noBall);
        OnBatTeam.NoBallTracker();
        teamRepository.updateTeamScorecardAfterOver(matchID,BatTeamID,OnBatTeam);
        playerRepository.updateBowlerStatsInDB(matchID,BallTeamID,bowler);
        playerRepository.updateOnFieldBatsmanStatsIDB(matchID,BatTeamID,onFieldBat);
    }

}



