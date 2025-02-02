//Szymon Wygoński
package fightord1e.main;

import fightord1e.engine.Game;
import fightord1e.engine.Player;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import textManagement.Loggers;

public class FightOrD1e{

    private static final int PLAY_GAME = 1;
    private static final int VIEW_STATS = 2;
    private static final int VIEW_CHAMPIONS = 3;
    private static final int RESET_CHAMPIONS = 9;
    private static final int EXIT_GAME = 0;

    public static void main(String[] args) {
        Map<String, Integer> fightWinners = new HashMap<>();
        Scanner input = new Scanner(System.in);
        Loggers.logMessage("Hi players! - enter your name\n[PLAYER 1]: ", false, true);
        String p1Name = input.nextLine();
        Loggers.logMessage("Hi players! - enter your name\n[PLAYER 2]: ", false, true);
        String p2Name = input.nextLine();
        Player p1 = new Player(p1Name);
        Player p2 = new Player(p2Name);
        Game g = new Game(p1, p2, fightWinners);
        g.applyChampionsList();
        int gameNumber = 0;
        int move;
        do {
            displayMainMenu();
            move = Loggers.choiceValidator(input);
            switch (move) {
                case PLAY_GAME ->
                    gameLoop(g, gameNumber, input);
                case VIEW_STATS ->
                    displayStats(p1, p2, fightWinners, gameNumber);
                case VIEW_CHAMPIONS ->
                    displaySpecifiedChampion(g, input);
                case RESET_CHAMPIONS ->
                    g.resetChampionList();
//                default ->
                //Loggers.logMessage("Invalid choice!", false, true);
            }
        } while (move != EXIT_GAME);
        end();
    }

    private static void gameLoop(Game g, int gameNumber, Scanner input) {
        boolean playAgain;// = false;
        do {
            playAgain = false;
            Loggers.logMessage("=================================================", true, false);
            Loggers.logMessage("GAME NUMBER " + ++gameNumber, true, true);
            g.start();
            g.end();
            int answear = Loggers.choiceValidator(input);
            if (answear == 1) {
                playAgain = true;
            }
        } while (playAgain);
    }

    private static void displayMainMenu() {
        Loggers.logMessage("""
                           What you want to do:
                           [1]Play a game
                           [2]See stats
                           [3]See champions
                           [9]Reset champions
                           [0]End""", false, true);
    }

    private static void displayStats(Player p1, Player p2, Map<String, Integer> fightWinners, int gameNumber) {
        Loggers.clearScreen();
        if (!fightWinners.isEmpty()) {
            Loggers.logMessage("TOTAL GAMES: [" + gameNumber + "]\n[" + p1.getName() + "]"
                    + "Score: " + p1.getWins() + "\n[" + p2.getName() + "]"
                    + "Score: " + p2.getWins() + "\n"
                    + "Winner table:", true, true);
            fightWinners.forEach((championName, winCount) -> {
                Loggers.logMessage("[" + championName + "] wins [" + winCount + "].", true, true);
            });
        } else {
            Loggers.logMessage("TOTAL GAMES: [0] *CLICK 1 TO PLAY* ", false, true);
        }
    }

    private static void displaySpecifiedChampion(Game g, Scanner input) {
        Loggers.logMessage("""       
                            \t[1]See champions - no filter
                            \t[2]See Magical champions
                            \t[3]See Physical champions
                            \t[4]See Great magic tanks
                            \t[5]See Great physical tanks
                                   """, false, true);
        int choice = Loggers.choiceValidator(input);
        Loggers.clearScreen();
        switch (choice) {
            case 2 ->
                g.showMagicChamp();
            case 3 ->
                g.showPhysicalChamp();
            case 4 ->
                g.showMResistChamp();
            case 5 ->
                g.showPResistChamp();
            default ->
                g.showChampions();
        }
    }
    private static void end()
    {
        Loggers.logMessage("Thanks for playing!\nCredits: \nTitle: FightOrD1e\nVersion: 1.0\nAuthor: Szymon Wygonski\nContact: swygonski@student.wsb-nlu.edu.pl", false, true);
    }
}