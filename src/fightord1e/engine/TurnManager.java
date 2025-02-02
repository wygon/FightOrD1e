//Szymon Wygoński
package fightord1e.engine;

import fightord1e.championAssets.Ability;
import fightord1e.championAssets.Champion;
import fightord1e.championAssets.SpellType;
import fightord1e.main.FightOrD1e;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public class TurnManager {

    private Player cp;
    private Player np;
    private int tourPoint;
    private int totalMovesCount;
    Random rand = new Random();

    public TurnManager(Player player1, Player player2) {
        this.cp = player1;
        this.np = player2;
        this.tourPoint = 0;
        this.totalMovesCount = 0;
    }

    public TurnManager() {
        this.tourPoint = 0;
        this.totalMovesCount = 0;
    }

    public void swapPlayers() {
        Player fakePlayer = np;
        np = cp;
        cp = fakePlayer;
    }

    public Player whoStart() {
        int n = rand.nextInt(2);
        if (n == 0) {
            return cp;
        } else {
            swapPlayers();
            return cp;
        }
    }

    @Override
    public String toString() {
        String a = "";
        a += "\nStats " + getCurrentPlayer().getName() + ": " + getCurrentChampion().toString();
        a += "\n\nStats " + getNextPlayer().getName() + ": " + getNextChampion().toString();
        return a;
    }

    //Function useAbility is responsible for using right ability 
    public void useAbility(Ability ability) {
        if (ability.isAvailable()) {
            String mess = "[" + totalMovesCount + "][" + tourPoint + "/3][" + getCurrentChampion().getName() + "]";
            int unLuckyNumber = 1;
            if (unLuckyNumber == rand.nextInt(10)) {
                mess += "Missed move.";
            } else {
                if (ability.getType().startsWith("aa")) {
                    getNextChampion().addHP(-((getCurrentChampion().getAttackDamage())) * (1 - getNextChampion().getPhysicalResist() / 70));
                    mess += "Normal attack";
                } else if (ability.getType().startsWith("boost")) {
                    switch (ability.getType()) {
                        case "boostad":
                            getCurrentChampion().addAttackDamage(ability.getValue());
                            mess += "Attack damage boosted: " + getCurrentChampion().getAttackDamage();
                            break;
                        case "boostmd":
                            getCurrentChampion().addMagicDamage(ability.getValue());
                            mess += "Magic damage boosted: " + getCurrentChampion().getMagicDamage();
                            break;
                        case "boostresist":
                            getCurrentChampion().addPhysicalResist(ability.getValue());
                            getCurrentChampion().addMagicResist(ability.getValue());
                            mess += "Physical and magic resist boosted: physical: " + getCurrentChampion().getPhysicalResist() + " magic: " + getCurrentChampion().getMagicResist();
                            break;
                        case "boosthp":
                            getCurrentChampion().addHP((getCurrentChampion().getMagicDamage() * 0.003 * ability.getValue()) + ability.getValue());
                            mess += "Healed: " + String.format("%.2f", (getCurrentChampion().getMagicDamage() * 0.003 * ability.getValue()) + ability.getValue());
                            break;
                        case "boostpr":
                            getCurrentChampion().addPhysicalResist(ability.getValue());
                            mess += "Physical resist boosted: " + getCurrentChampion().getPhysicalResist();
                            break;
                        case "boostmr":
                            getCurrentChampion().addMagicResist(ability.getValue());
                            mess += "Magic resist boosted " + getCurrentChampion().getMagicResist();
                            break;
                        default:
                            break;
                    }
                } else if (ability.getType().startsWith("dmg")) {
                    switch (ability.getType()) {
                        case "dmgmd":
                            getNextChampion().addHP(-(((getCurrentChampion().getMagicDamage() * 0.005 * ability.getValue())) + ability.getValue()) * (1 - getNextChampion().getMagicResist() / 100));
                            mess += "Magic damage dealt: " + String.format("%.2f", (((getCurrentChampion().getMagicDamage() * 0.005 * ability.getValue())) + ability.getValue()) * (1 - getNextChampion().getMagicResist() / 100));
                            break;
                        case "dmgad":
                            getNextChampion().addHP(-(((getCurrentChampion().getAttackDamage() * 0.01) * ability.getValue()) + ability.getValue()) * (1 - getNextChampion().getPhysicalResist() / 100));
                            mess += "Physical damage dealt: " + String.format("%.2f", (((getCurrentChampion().getAttackDamage() * 0.01) * ability.getValue()) + ability.getValue()) * (1 - getNextChampion().getPhysicalResist() / 100));
                            break;
                        case "dmgpoison":
                            getNextChampion().setPoison(true);
                            getNextChampion().addPoisonDmg(-(((getCurrentChampion().getMagicDamage() * 0.005 * ability.getValue())) + ability.getValue()) * (1 - getNextChampion().getMagicResist() / 100));
                            getNextChampion().setPoisonMove(totalMovesCount + 11);
                            mess += "Poison applied to " + getNextChampion().getName();
                            break;
                        default:
                            break;
                    }
                } else if (ability.getType().startsWith("add")) {
                    if (ability.getType().equals("addad2")) {
                        getNextChampion().addHP(-((getCurrentChampion().getAttackDamage()) * ability.getValue()) * (1 - getNextChampion().getPhysicalResist() / 70));
                        mess += "DOUBLE ATTACK!";
                    }
                } else if (ability.getType().startsWith("turn")) {
                    if (ability.getType().equals("turn")) {
                        getCurrentChampion().addDistancePoint(1);
                        mess += "Tour point added.";
                    }
                } else if (ability.getType().startsWith("lifesteal")) {
                    if (ability.getType().equals("lifesteal")) {
                        getCurrentChampion().setSpecialSpellType(SpellType.LIFESTEAL);
                        getCurrentChampion().setSpecialSpellValue(ability.getValue());
                        if (ability.getUsesLeft() > 100) {
                            getCurrentChampion().setSpecialSpellMove(totalMovesCount + 500);
                        } else {
                            getCurrentChampion().setSpecialSpellMove(totalMovesCount + 11);
                        }
                        mess += "Lifesteal turned ON!";
                    }
                } else if (ability.getType().startsWith("thorns")) {
                    if (ability.getType().equals("thorns")) {
                        getCurrentChampion().setSpecialSpellType(SpellType.THORNS);
                        getCurrentChampion().setSpecialSpellValue(ability.getValue());
                        if (ability.getUsesLeft() > 100) {
                            getCurrentChampion().setSpecialSpellMove(totalMovesCount + 500);
                        } else {
                            getCurrentChampion().setSpecialSpellMove(totalMovesCount + 11);
                        }
                        mess += "Thorns turned ON!";
                    }
                }
                if (getCurrentChampion().getSpecialSpellType() == SpellType.LUCK) {
                    int luckyNumber = 1;
                    if (luckyNumber == rand.nextInt(5)) {
                        ability.addUsesLeft(1);
                        mess += " is LUCKY MOVE!";
                    }
                }
            }
            Fight.clearScreen();
            logMessage("=================================================", false, true);
            logMessage(mess, true, true);
            ability.addUsesLeft(-1);
        }

    }
//        enum SpellType{
//            LIFESTEAL,
//            THORNS,
//            POISON
//        }
    //Checking if any effect is applied and - if: doing it work 

    public void effectsManagement() {
        String mess = "";
        //Poison management
        if (getCurrentChampion().isPoison()) {
            if (getTotalMovesCount() >= getCurrentChampion().getPoisonMove()) {
                getCurrentChampion().setPoison(false);
                getCurrentChampion().addPoisonDmg(-getCurrentChampion().getPoisonDmg());
                mess += "[PASSIVE][" + getNextChampion().getName() + "] poison ended";
            } else {
                getCurrentChampion().addHP(getCurrentChampion().getPoisonDmg());
                mess += "[PASSIVE][" + getCurrentChampion().getName() + "] poisoned damage " + getCurrentChampion().getPoisonDmg();
            }
        }
        //Lifesteal management
        if (getNextChampion().getSpecialSpellType() == SpellType.LIFESTEAL) {
            if (!mess.equals("")) {
                mess += "\n";
            }
            if (getTotalMovesCount() >= getNextChampion().getSpecialSpellMove()) {
                getNextChampion().setSpecialSpellType(SpellType.OFF);
                mess += "[PASSIVE][" + getCurrentChampion().getName() + "]Lifesteal ended";
            } else {
                getNextChampion().addHP((getCurrentChampion().getLastRoundHP() - getCurrentChampion().getHP()) * (getNextChampion().getSpecialSpellValue()) * 0.02);
                mess += "[PASSIVE][" + getNextChampion().getName() + "]Healed for " + String.format("%.2f", (getCurrentChampion().getLastRoundHP() - getCurrentChampion().getHP()) * (getNextChampion().getSpecialSpellValue()) * 0.02);
            }
        }
        //Thorns management
        if (getCurrentChampion().getSpecialSpellType() == SpellType.THORNS) {
            if (!mess.equals("")) {
                mess += "\n";
            }
            if (getTotalMovesCount() >= getCurrentChampion().getSpecialSpellMove()) {
                getCurrentChampion().setSpecialSpellType(SpellType.OFF);
                getCurrentChampion().setSpecialSpellValue(0);
                mess += "[PASSIVE]" + "Thorns from [" + getCurrentChampion().getName() + "] to [" + getNextChampion().getName() + "] ended.";
            } else {
                getNextChampion().addHP(-(getCurrentChampion().getLastRoundHP() - getCurrentChampion().getHP()) * (getCurrentChampion().getSpecialSpellValue() * 0.015));
                mess += "[PASSIVE]Thorns hit [" + getNextChampion().getName() + "] for " + String.format("%.2f", (getCurrentChampion().getLastRoundHP() - getCurrentChampion().getHP()) * (getCurrentChampion().getSpecialSpellValue() * 0.015));
            }
        }
        if (!mess.equals("")) {
            logMessage(mess, true, true);
            logMessage("=================================================", false, true);
        }
    }

    //this function is responsible for showing information to user in terminal or/and saving info to logs.txt
    public static void logMessage(String message, boolean log, boolean terminal) {
        if (log) {
            try {
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("logs.txt", true)), true);
                writer.println(message);
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(FightOrD1e.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (terminal) {
            System.out.println(message);
        }
    }

    public void readFile(String file, boolean log, boolean terminal) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String[] dictionary = line.split(";");
            for (String word : dictionary) {
                logMessage(word, log, terminal);
            }
            //line = reader.readLine();
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(FightOrD1e.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Checking - if: range is okay to start fight - if not: ending tour
    public void rangeCheck() {
        //Decreasing range - if: champion is far away
        if (getCurrentChampion().getDistancePoint() < getNextChampion().getDistancePoint()) {
            getNextChampion().addDistancePoint(-1);
//            effectsManagement();
            String mess = "[" + getTotalMovesCount() + "][" + getTourPoint() + "/3] " + getCurrentChampion().getName() + " is losing tour caused by range difference.";
            logMessage(mess + "\n=================================================", false, true);
            mess += getNextPlayer().getName() + " [" + getNextChampion().getName() + "] ITS YOUR TURN!";
            logMessage(mess, true, false);
            endTurn();
        }
    }

    //Function endTurn() is responsible for manage things after single player tour
    public void endTurn() {
        getCurrentChampion().setLastRoundHP(getCurrentChampion().getHP());
        swapPlayers();
    }

    //Checking - if: game is over
    public boolean isGameOver() {
        return getCurrentPlayer().getChampion().getHP() <= 0 || getNextPlayer().getChampion().getHP() <= 0;
    }

    public Player getCurrentPlayer() {
        return cp;
    }

    public Player getNextPlayer() {
        return np;
    }

    public Champion getCurrentChampion() {
        return getCurrentPlayer().getChampion();
    }

    public Champion getNextChampion() {
        return getNextPlayer().getChampion();
    }

    public int getTourPoint() {
        return tourPoint;
    }

    public int getTotalMovesCount() {
        return totalMovesCount;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.cp = currentPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.np = nextPlayer;
    }

    public void setTourPoint(int tourPoint) {
        this.tourPoint = tourPoint;
    }

    public void setTotalMovesCount(int totalMovesCount) {
        this.totalMovesCount = totalMovesCount;
    }

    public void addTourPoint(int tourPoint) {
        this.tourPoint += tourPoint;
        totalMovesCount++;
    }
}
