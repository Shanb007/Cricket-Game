//package com.tekion.game.service;
//
//import java.sql.SQLException;
//import java.util.Scanner;
//
//public class GameService {
//    public void PlayTheGame(int overs) throws SQLException, ClassNotFoundException {
//        String wantMatch = "Y";
//        while (wantMatch.equals("Y")) {
//            MatchServiceImpl match = new MatchServiceImpl();
//           // int overs;
//            match.matchDeclaration();
//            Scanner sc = new Scanner(System.in);
//           // System.out.print("\nEnter The number of overs: ");
//           // overs = sc.nextInt();
//            match.startTheMatch(overs);
//            System.out.println("Want another match ? (Y/N)");
//            wantMatch = sc.next();
//        }
//        System.out.println("All Matches are done for the Day, ThankYou!");
//    }
//}
