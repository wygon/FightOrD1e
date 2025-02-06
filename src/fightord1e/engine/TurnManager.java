//Szymon Wygoński
package fightord1e.engine;

import fightord1e.championAssets.Ability;
import fightord1e.championAssets.Champion;
import fightord1e.championAssets.SpellType;
import java.util.Random;
import textManagement.Loggers;

public class TurnManager {

    private Player cp;
    private Player np;
    private int tourPoint;
    private int totalMovesCount;
    private Random rand;

    public TurnManager(Player player1, Player player2) {
        this.rand = new Random();
        this.cp = player1;
        this.np = player2;
        this.tourPoint = 0;
        this.totalMovesCount = 0;
    }

    public TurnManager() {
        this.rand = new Random();
        this.tourPoint = 0;
        this.totalMovesCount = 0;
    }

    //Swapping players to enable tour gameplay.
    public void swapPlayers() {
        Player fakePlayer = np;
        np = cp;
        cp = fakePlayer;
    }

    //Function whoStart is returning Player who is starting game.
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

    //Function useAbility is responsible for using right ability and applaying spells
    public void useAbility(Ability ability) {
        if (ability.isAvailable()) {
            double multipilier = 1;
            int unLuckyNumber = 1;
            String mess = "[" + totalMovesCount + "][" + tourPoint + "/3][" + getCurrentChampion().getName() + "]";
            if (unLuckyNumber == rand.nextInt(10)) {
                mess += "Missed move.";
            } else {
                if (getCurrentChampion().getSpecialSpellType() == SpellType.CRIT) {
                    if (1 == rand.nextDouble(5)) {
                        multipilier = 1.25;
                        mess += "[CRIT]";
                    }
                }
                if (ability.getType().startsWith("aa")) {
                    getNextChampion().addHP((-((getCurrentChampion().getAttackDamage())) * (1 - getNextChampion().getPhysicalResist() / 70)) * multipilier);
                    mess += "Normal attack";
                } else if (ability.getType().startsWith("boost")) {
                    switch (ability.getType()) {
                        case "boostad":
                            getCurrentChampion().addAttackDamage(ability.getValue() * multipilier);
                            mess += "Attack damage boosted: " + getCurrentChampion().getAttackDamage() * multipilier;
                            break;
                        case "boostmd":
                            getCurrentChampion().addMagicDamage(ability.getValue() * multipilier);
                            mess += "Magic damage boosted: " + getCurrentChampion().getMagicDamage() * multipilier;
                            break;
                        case "boostresist":
                            getCurrentChampion().addPhysicalResist(ability.getValue() * multipilier);
                            getCurrentChampion().addMagicResist(ability.getValue() * multipilier);
                            mess += "Physical and magic resist boosted: physical: " + getCurrentChampion().getPhysicalResist() * multipilier + " magic: " + getCurrentChampion().getMagicResist() * multipilier;
                            break;
                        case "boosthp":
                            getCurrentChampion().addHP(((getCurrentChampion().getMagicDamage() * 0.003 * ability.getValue()) + ability.getValue()) * multipilier);
                            mess += "Healed: " + String.format("%.2f", ((getCurrentChampion().getMagicDamage() * 0.003 * ability.getValue()) + ability.getValue()) * multipilier);
                            break;
                        case "boostpr":
                            getCurrentChampion().addPhysicalResist(ability.getValue() * multipilier);
                            mess += "Physical resist boosted: " + getCurrentChampion().getPhysicalResist() * multipilier;
                            break;
                        case "boostmr":
                            getCurrentChampion().addMagicResist(ability.getValue() * multipilier);
                            mess += "Magic resist boosted " + getCurrentChampion().getMagicResist() * multipilier;
                            break;
                        default:
                            break;
                    }
                } else if (ability.getType().startsWith("dmg")) {
                    switch (ability.getType()) {
                        case "dmgmd":
                            getNextChampion().addHP((-(((getCurrentChampion().getMagicDamage() * 0.005 * ability.getValue())) + ability.getValue()) * (1 - getNextChampion().getMagicResist() / 100)) * multipilier);
                            mess += "Magic damage dealt: " + String.format("%.2f", ((((getCurrentChampion().getMagicDamage() * 0.005 * ability.getValue())) + ability.getValue()) * (1 - getNextChampion().getMagicResist() / 100)) * multipilier);
                            break;
                        case "dmgad":
                            getNextChampion().addHP((-(((getCurrentChampion().getAttackDamage() * 0.01) * ability.getValue()) + ability.getValue()) * (1 - getNextChampion().getPhysicalResist() / 100)) * multipilier);
                            mess += "Physical damage dealt: " + String.format("%.2f", ((((getCurrentChampion().getAttackDamage() * 0.01) * ability.getValue()) + ability.getValue()) * (1 - getNextChampion().getPhysicalResist() / 100)) * multipilier);
                            break;
                        case "dmgpoison":
                            getNextChampion().setPoison(true);
                            getNextChampion().addPoisonDmg((-(((getCurrentChampion().getMagicDamage() * 0.005 * ability.getValue())) + ability.getValue()) * (1 - getNextChampion().getMagicResist() / 100)) * multipilier);
                            getNextChampion().setPoisonMove(totalMovesCount + 11);
                            mess += "Poison applied to " + getNextChampion().getName();
                            break;
                        default:
                            break;
                    }
                } else if (ability.getType().startsWith("add")) {
                    if (ability.getType().equals("addad2")) {
                        getNextChampion().addHP((-((getCurrentChampion().getAttackDamage()) * ability.getValue()) * (1 - getNextChampion().getPhysicalResist() / 70)) * multipilier);
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
                        getCurrentChampion().setSpecialSpellValue(ability.getValue() * multipilier);
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
                        getCurrentChampion().setSpecialSpellValue(ability.getValue() * multipilier);
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
                        mess += "[LUCKY]";
                    }
                }
            }
            Loggers.clearScreen();
            Loggers.logMessage("=================================================", false, true);
            Loggers.logMessage(mess, true, true);
            ability.addUsesLeft(-1);
        }
    }

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
            Loggers.logMessage(mess, true, true);
            Loggers.logMessage("=================================================", false, true);
        }
    }

    //Checking - if: range is okay to start fight - if not: ending tour
    public void rangeCheck() {
        //Decreasing range - if: champion is far away
        if (getCurrentChampion().getDistancePoint() < getNextChampion().getDistancePoint()) {
            getNextChampion().addDistancePoint(-1);
//            effectsManagement();
            String mess = "[" + getTotalMovesCount() + "][" + getTourPoint() + "/3] " + getCurrentChampion().getName() + " is losing tour caused by range difference.";
            Loggers.logMessage(mess + "\n=================================================", false, true);
            mess += getNextPlayer().getName() + " [" + getNextChampion().getName() + "] ITS YOUR TURN!";
            Loggers.logMessage(mess, true, false);
            setTourPoint(3);
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
