//Szymon Wygoński
package fightord1e.engine;

import fightord1e.championAssets.Ability;
import fightord1e.championAssets.Champion;
import java.util.Scanner;
import textManagement.Loggers;

public class Fight implements GameActions {

    private final TurnManager tm;
    private final StatisticTable st;
    private Champion winner;
    Scanner input = new Scanner(System.in);

    public Fight(TurnManager tm, StatisticTable st) {
        this.tm = tm;
        this.st = st;
    }

    //Start fight 
    @Override
    public void start() {
        tm.setTotalMovesCount(0);
        fightLoop();
        end();
    }

    @Override
    public void end() {
        st.displayBattleStatistics();
        String mess = "";
        if (tm.getCurrentChampion().getHP() <= 0) {
            mess = "[" + tm.getCurrentChampion().getName() + "]Died... [" + tm.getNextChampion().getName() + "]is a WINNER ";
            tm.getNextPlayer().addWin();
            //Not working wgile first argument is Champion(and not String)
            setWinner(tm.getNextChampion());
        } else if (tm.getNextPlayer().getChampion().getHP() <= 0) {
            mess = "[" + tm.getNextChampion().getName() + "]Died... [" + tm.getCurrentChampion().getName() + "]is a WINNER ";
            tm.getCurrentPlayer().addWin();
            setWinner(tm.getCurrentChampion());
        } else {
            mess = "[ALMOST IMPOSSIBLE]TIE[" + tm.getNextChampion().getName() + "] and [" + tm.getCurrentChampion().getName() + "]DIED!";
        }
        if (!mess.equals("")) {
            Loggers.logMessage(mess, true, true);
        }
    }

    //Main fight file
    private void fightLoop() {
        do {
            int wybor;
            Champion ally = tm.getCurrentChampion();
            Champion enemy = tm.getNextChampion();
            tm.setTourPoint(0);
            tm.effectsManagement();
            tm.rangeCheck();
            if (tm.getTourPoint() <= 2) {
                Loggers.logMessage("[" + tm.getCurrentPlayer().getName() + "][" + ally.getName() + "] ITS YOUR TURN!", false, true);
            }
            while (!tm.isGameOver() && tm.getTourPoint() <= 2) {
                String hp1 = String.format("%.2f", ally.getHP());
                String hp2 = String.format("%.2f", enemy.getHP());
                Loggers.logMessage(
                        "[" + ally.getName() + "] HP: " + hp1 + "\n"
                        + "[" + enemy.getName() + "] HP: " + hp2 + "\n\n"
                        + "Move: " + (tm.getTourPoint() + 1) + "/3\n"
                        + "[1]Attack [" + ally.getAttackDamage() + "]" , false, true);
                int i = 0;
                for (Ability a : ally.getAbilities()) {
                    Loggers.logMessage("[" + (i++ + 2) + "]" + a.getName() + " [" + a.getValue() + "]" + "[Uses " + a.getUsesLeft() + "]", false, true);
                }
                Loggers.logMessage("""
                                [10]See stats
                                [99]End move
                                [135]Forfeit
                                [0]Wyczysc""", false, true);
                wybor = Loggers.choiceValidator(input);
                switch (wybor) {
                    case 1:
                        tm.addTourPoint(1);
                        tm.useAbility(new Ability("Auto attack", "auto attack", ally.getAttackDamage(), "aa", 112));
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        int abilityIndex = wybor - 2;
                        if (ally.getAbilities().length > abilityIndex) {
                            Ability ability = ally.getAbilities()[abilityIndex];
                            if (ability.getUsesLeft() > 0) {
                                tm.addTourPoint(1);
                                tm.useAbility(ability);
                            } else {
                                Loggers.logMessage("Ability " + ability.getName() + " has no uses left!", false, true);
                            }
                        } else {
                            Loggers.logMessage(ally.getName() + " does not have " + (abilityIndex + 2) + " abilities!", false, true);
                        }
                        break;
                    case 10:
                        Loggers.clearScreen();
                        Loggers.logMessage(ally.getName() + ":\n" + ally.lessStats(), false, true);
                    case 99:
                        Loggers.clearScreen();
                        tm.setTourPoint(3);
                        break;
                    case 135:
                        ally.setHP(0);
                    case 0:
                        Loggers.clearScreen();
                        break;
                    default:
                        Loggers.logMessage("Invalid choice!", false, true);
                        break;
                }
                Loggers.logMessage("=================================================", false, true);
            }
            tm.endTurn();
        } while (!tm.isGameOver());
    }

    //Setting winner
    private void setWinner(Champion winner) {
        this.winner = winner;
    }

    public Champion getWinner() {
        return winner;
    }
}
