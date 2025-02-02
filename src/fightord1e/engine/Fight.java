//Szymon Wygoński
package fightord1e.engine;

import fightord1e.championAssets.Ability;
import fightord1e.championAssets.Champion;
import java.util.Scanner;

public class Fight implements GameActions{

    private final TurnManager tm;
    private final StatisticTable st;
    private Champion winner;
    Scanner input = new Scanner(System.in);

    public Fight(TurnManager tm, StatisticTable st) {
        this.tm = tm;
        this.st = st;
    }
    @Override
    public void start() {
        tm.setTotalMovesCount(0);
        do {
            int wybor;
            tm.setTourPoint(0);
            tm.effectsManagement();
            tm.rangeCheck();
            System.out.println("[" + tm.getCurrentPlayer().getName() + "][" + tm.getCurrentChampion().getName() + "] ITS YOUR TURN!");
            while (!tm.isGameOver() && tm.getTourPoint() <= 2) {
                String hp1 = String.format("%.2f", tm.getCurrentChampion().getHP());
                String hp2 = String.format("%.2f", tm.getNextChampion().getHP());
                System.out.println(tm.getCurrentChampion().getName() + " hp: " + hp1 + "\n"
                        + tm.getNextChampion().getName() + " hp: " + hp2);
                System.out.println("\n\nMove: " + (tm.getTourPoint() + 1) + "/3"
                        + "\n[1]Attack");
                //CO TO JEST DO PANA WAFLA
                for (int i = 0; i < tm.getCurrentChampion().getAbilities().length; i++) {
                    System.out.print("[" + (i + 2) + "]" + tm.getCurrentChampion().getAbilities()[i].getName() + " [Value " + tm.getCurrentChampion().getAbilities()[i].getValue() + "]" + "[Uses " + tm.getCurrentChampion().getAbilities()[i].getUsesLeft() + "]\n");
                }
                System.out.println("""
                                [10]See stats
                                [99]End move
                                [0]Wyczysc""");
                wybor = input.nextInt();
                switch (wybor) {
                    case 1:
                        tm.addTourPoint(1);
                        tm.useAbility(new Ability("Auto attack", "auto attack", tm.getCurrentChampion().getAttackDamage(), "aa", 112));
                        break;
                    case 2:
                        if (tm.getCurrentChampion().getAbilities().length >= 1) {
                            if (tm.getCurrentChampion().getAbilities()[0].getUsesLeft() > 0) {
                                tm.addTourPoint(1);
                            }
                            tm.useAbility(tm.getCurrentChampion().getAbilities()[0]);
                        } else {
                            System.out.println(tm.getCurrentChampion().getName() + " does not have 2 abilities!");
                        }
                        break;
                    case 3:
                        if (tm.getCurrentChampion().getAbilities().length >= 2) {
                            if (tm.getCurrentChampion().getAbilities()[1].getUsesLeft() > 0) {
                                tm.addTourPoint(1);
                            }
                            tm.useAbility(tm.getCurrentChampion().getAbilities()[1]);
                        } else {
                            System.out.println(tm.getCurrentChampion().getName() + " does not have 3 abilities!");
                        }
                        break;
                    case 4:
                        if (tm.getCurrentChampion().getAbilities().length >= 3) {
                            if (tm.getCurrentChampion().getAbilities()[2].getUsesLeft() > 0) {
                                tm.addTourPoint(1);
                            }
                            tm.useAbility(tm.getCurrentChampion().getAbilities()[2]);
                        } else {
                            System.out.println(tm.getCurrentChampion().getName() + " does not have 4 abilities!");
                        }
                        break;
                    case 5:
                        if (tm.getCurrentChampion().getAbilities().length >= 4) {
                            if (tm.getCurrentChampion().getAbilities()[3].getUsesLeft() > 0) {
                                tm.addTourPoint(1);
                            }
                            tm.useAbility(tm.getCurrentChampion().getAbilities()[3]);
                        } else {
                            System.out.println(tm.getCurrentChampion().getName() + " does not have 5 abilities!");
                        }
                        break;
                    case 6:
                        if (tm.getCurrentChampion().getAbilities().length >= 5) {
                            if (tm.getCurrentChampion().getAbilities()[4].getUsesLeft() > 0) {
                                tm.addTourPoint(1);
                            }
                            tm.useAbility(tm.getCurrentChampion().getAbilities()[4]);
                        } else {
                            System.out.println(tm.getCurrentChampion().getName() + " does not have 6 abilities!");
                        }
                        break;
                    case 7:
                        if (tm.getCurrentChampion().getAbilities().length >= 6) {
                            if (tm.getCurrentChampion().getAbilities()[5].getUsesLeft() > 0) {
                                tm.addTourPoint(1);
                            }
                            tm.useAbility(tm.getCurrentChampion().getAbilities()[6]);
                        } else {
                            System.out.println(tm.getCurrentChampion().getName() + " does not have 7 abilities!");
                        }
                        break;
                    case 10:
                        System.out.println(tm.getCurrentChampion().lessStats());
                    case 99:
                        tm.setTourPoint(3);
                        break;
                    case 0:
                        clearScreen();
                        break;
                    default:
                        break;
                }
                TurnManager.logMessage("=================================================", false, true);
            }
            tm.endTurn();
        } while (!tm.isGameOver());
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
            TurnManager.logMessage(mess, true, true);
        }
    }

    private void setWinner(Champion winner) {
        this.winner = winner;
    }

    public Champion getWinner() {
        return winner;
    }

    public static void clearScreen() {
        //there should be console cleaing function
        //simmilar to windows cmd 'system("cls");'
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
