import com.tekion.game.Match;

import java.util.Scanner;

public class GameBegins {
    public static void main(String[] args) {
        String wantMatch = "Y";
        while(wantMatch.equals("Y")){
            int overs;
            Match m = new Match();
            m.matchDeclaration();
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter The number of overs: ");
            overs = sc.nextInt();
            m.startTheMatch(overs);
            // show result
            m.ShowResults();
            System.out.println("Want another match ? (Y/N)");
            wantMatch = sc.next();
            }
        System.out.println("All Matches are done for the Day, ThankYou!");
        }
    }
