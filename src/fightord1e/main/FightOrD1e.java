//Szymon Wygoński
package fightord1e.main;

import fightord1e.engine.Game;
import fightord1e.engine.Player;
import fightord1e.engine.TurnManager;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

public class FightOrD1e {

    public static void main(String[] args) {
        //SORTOWANIE TREE MAP KURWA BOMBA KLAT
        Map<String, Integer> fightWinners = new TreeMap<>();
        Scanner input = new Scanner(System.in);
        System.out.println("Hi players! - enter your name\n[PLAYER 1]: ");
        String p1Name = input.nextLine();
        System.out.println("[Player 2]: ");
        String p2Name = input.nextLine();
        Player p1 = new Player(p1Name);
        Player p2 = new Player(p2Name);
        Game g;
        int gameNumber = 0;
        int move;
        boolean playAgain;
        do {
            g = new Game(p1, p2, fightWinners);
            System.out.println("""
                               What you want to do:
                               [1]Play a game
                               [2]See stats
                               [3]See champions
                               [0]End""");
            move = input.nextInt();
            switch (move) {
                case 1:
                    do {
                        playAgain = false;
                        TurnManager.logMessage("=================================================", true, false);
                        TurnManager.logMessage("GAME NUMBER " + ++gameNumber, true, true);
                        g.start();
                        int answear = input.nextInt();
                        if (answear == 1) {
                            playAgain = true;
                        }
                    } while (playAgain);
                    break;
                case 2:
                    if (!fightWinners.isEmpty()) {
                        TurnManager.logMessage("TOTAL GAMES: [" + gameNumber + "]\n[" + p1.getName() + "]Score: " + p1.getWins() + "\n[" + p2.getName() + "]Score: " + p2.getWins() + "\nWinner table:", true, true);
                        //TurnManager.logMessage("Winner table:", true, true);
                        fightWinners.forEach((championName, winCount) -> {
                            TurnManager.logMessage("[" + championName + "] wins [" + winCount + "].", true, true);
                        });
                    } else {
                        TurnManager.logMessage("TOTAL GAMES: [0] *CLICK 1 TO PLAY* ", false, true);
                    }
                    break;
                case 3:
                    g.readChampions();
                    break;
                default:
                    break;
            }
        } while (move > 0);
    }
}
