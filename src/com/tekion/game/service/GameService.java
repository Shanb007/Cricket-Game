package com.tekion.game.service;

import com.tekion.game.models.Match;
import java.sql.SQLException;
import java.util.Scanner;

public class GameService {
    public void PlayTheGame() throws SQLException, ClassNotFoundException {
        String wantMatch = "Y";
        while (wantMatch.equals("Y")) {
            int overs;
            Match NewMatch = new Match();
            NewMatch.matchDeclaration();
            Scanner sc = new Scanner(System.in);
            System.out.print("\nEnter The number of overs: ");
            overs = sc.nextInt();
            NewMatch.startTheMatch(overs);
            System.out.println("Want another match ? (Y/N)");
            wantMatch = sc.next();
        }
        System.out.println("All Matches are done for the Day, ThankYou!");
    }
}
